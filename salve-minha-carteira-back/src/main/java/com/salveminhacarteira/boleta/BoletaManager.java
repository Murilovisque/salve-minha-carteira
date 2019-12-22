package com.salveminhacarteira.boleta;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Validator;

import com.salveminhacarteira.acao.Acao;
import com.salveminhacarteira.boleta.extensoes.BoletaAgrupadoPeloCodigoNegociacao;
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

    public void cadastrarOuAtualizar(Boleta.Tipo tipo, LocalDate data, BigDecimal valor, Integer quantidade, Long idAcao) throws SalveMinhaCarteiraException {
        var boleta = new Boleta(tipo, data, valor, quantidade, new Acao(), new Usuario());
        var erros = validator.validate(boleta);
        if (!erros.isEmpty())
            throw new ArgumentosInvalidadosException(erros);
        var idUsuario = tokenManager.obterTokenDaRequisicao().getIdUsuario();
        try {
            boletaRepository.salvar(tipo.name(), data, valor, quantidade, idAcao, idUsuario);
            logger.info("Boleta de {} e data {} foi cadastrado", tipo, data);
        } catch (DataIntegrityViolationException ex) {
            if (Erros.ehAssociacaoDeEntidadeNaoExistente(ex)) {
                logger.info("Acao com id {} não existe", idAcao);
                throw new ArgumentosInvalidadosException("Ação inválida");
            }
            if (Erros.ehRegistroDuplicado(ex)) {
                logger.info("Boleta nessa data, tipo, acao e valor já existe. Será incrementado a quantidade e atualizado");
                var boletaExistente = boletaRepository.obterBoletaPeloTipoDataUsuarioAcaoEhValor(tipo.name(), data, idUsuario, idAcao, valor).get();
                boletaExistente.incrementarQuantidade(boleta);
                boletaRepository.save(boletaExistente);                
                logger.info("Boleta de {} e data {} foi atualizado", tipo, data);
            } else {
                logger.error(ex.toString());
                throw new SalveMinhaCarteiraException();
            }
        }
    }

    public List<?> obterBoletasAgrupadasPeloCodigoNegociacaoComSaldoPositivo() {
        var idUsuario = tokenManager.obterTokenDaRequisicao().getIdUsuario();
        var listaAgrupada = boletaRepository.obterBoletasAgrupadasPeloCodigoNegociacao(idUsuario);
        listaAgrupada.stream().collect(Collectors.groupingBy(BoletaAgrupadoPeloCodigoNegociacao::getCodigoNegociacaoPapel, Collectors.reducing((b1, b2) -> b1.)));
    }
}