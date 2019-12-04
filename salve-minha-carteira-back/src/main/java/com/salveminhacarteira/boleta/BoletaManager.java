package com.salveminhacarteira.boleta;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.transaction.Transactional;
import javax.validation.Validator;

import com.salveminhacarteira.acao.Acao;
import com.salveminhacarteira.excecoes.ArgumentosInvalidadosException;
import com.salveminhacarteira.excecoes.SalveMinhaCarteiraException;
import com.salveminhacarteira.seguranca.TokenManager;
import com.salveminhacarteira.usuario.Usuario;
import com.salveminhacarteira.utilitarios.Erros;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
public class BoletaManager {

    private static final Logger logger = LoggerFactory.getLogger(BoletaManager.class);

    @Autowired
    private BoletaRepository boletaRepository;

    @Autowired
    private Validator validator;

    @Autowired
    private TokenManager tokenManager;

    @Transactional(rollbackOn = Exception.class)
    public void cadastrar(Boleta.Tipo tipo, LocalDate data, BigDecimal valor, Integer quantidade, Long idAcao) throws SalveMinhaCarteiraException {
        var boleta = new Boleta(tipo, data, valor, quantidade, new Acao(), new Usuario());
        var erros = validator.validate(boleta);
        if (!erros.isEmpty())
            throw new ArgumentosInvalidadosException(erros);
        try {
            boletaRepository.salvar(tipo.name(), data, valor, quantidade, idAcao, tokenManager.obterTokenDaRequisicao().getIdUsuario());
        } catch (DataIntegrityViolationException ex) {
            if (Erros.ehAssociacaoDeEntidadeNaoExistente(ex)) {
                logger.info("Acao com id {} não existe", idAcao);
                throw new ArgumentosInvalidadosException("Ação inválida");
            }
            logger.error(ex.toString());
            throw new SalveMinhaCarteiraException();
        }
    }
}