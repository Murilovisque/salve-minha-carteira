package com.salveminhacarteira.boleta;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import static java.util.stream.Collectors.*;

import javax.validation.Validator;

import com.salveminhacarteira.acao.Acao;
import com.salveminhacarteira.boleta.extensoes.BoletaAgrupadoPeloCodigoNegociacao;
import com.salveminhacarteira.boleta.extensoes.BoletaAgrupadoPeloCodigoNegociacao2;
import com.salveminhacarteira.boleta.extensoes.BoletaAgrupadoPeloTipoEhCodigoNegociacao;
import com.salveminhacarteira.boleta.extensoes.BoletaAgrupadoPeloTipoEhCodigoNegociacao2;
import com.salveminhacarteira.boleta.extensoes.Somatoria;
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
        if (tipo.equals(Boleta.Tipo.VENDA)) {
            var quantidadeJaComprada = boletaRepository.obterQuantidadeTotalComSaldoPositivoDaAcao(idUsuario, idAcao);
            if (!quantidadeJaComprada.isPresent() || quantidade > quantidadeJaComprada.get())
                throw new ArgumentosInvalidadosException("Não é possível cadastrar " + quantidade + " venda(s) dessa ação pois você tem " + quantidadeJaComprada.get() + " comprada(s)");
        }
        try {
            boletaRepository.salvar(tipo.name(), data, valor, quantidade, idAcao, idUsuario);
            logger.info("Boleta de {} e data {} foi cadastrado", tipo, data);
        } catch (DataIntegrityViolationException ex) {
            if (Erros.ehAssociacaoDeEntidadeNaoExistente(ex)) {
                logger.info("Acao com id {} não existe", idAcao);
                throw new ArgumentosInvalidadosException("Ação inválida");
            }
            if (Erros.ehRegistroDuplicado(ex)) {
                logger.info("Boleta nessa data, tipo, acao e valor já existe. A quantidade será incrementada e atualizada");
                var boletaExistente = boletaRepository.obterBoletaPeloTipoDataAcaoEhValor(idUsuario, tipo.name(), data, idAcao, valor).get();
                boletaExistente.incrementarQuantidade(boleta);
                boletaRepository.save(boletaExistente);                
                logger.info("Boleta de {} e data {} foi atualizado", tipo, data);
            } else {
                logger.error(ex.toString());
                throw new SalveMinhaCarteiraException();
            }
        }
    }

    public List<BoletaAgrupadoPeloCodigoNegociacao2> obterBoletasAgrupadasPeloCodigoNegociacaoComSaldoPositivo() {
        var idUsuario = tokenManager.obterTokenDaRequisicao().getIdUsuario();
        // var listaAgrupada = boletaRepository.obterBoletasAgrupadasPeloTipoEhCodigoNegociacao(idUsuario);
        // var resultado = listaAgrupada.stream().collect(groupingBy(BoletaAgrupadoPeloTipoEhCodigoNegociacao::getCodigoNegociacaoPapel,
        //             reducing(BoletaAgrupadoPeloTipoEhCodigoNegociacao::mesclarSomandoQuantidade)))
        //         .entrySet().stream()
        //             .filter(e -> e.getValue().isPresent() && e.getValue().get().getTipo().equals(Boleta.Tipo.COMPRA.name())
        //                 && e.getValue().get().getQuantidadeTotal() > 0)
        //             .map(e -> new BoletaAgrupadoPeloCodigoNegociacao(e.getValue().get().getId(), e.getKey(), e.getValue().get().getQuantidadeTotal()))
        //             .collect(toList());
        var listaAgrupada = boletaRepository.obterBoletasAgrupadasPeloTipoEhCodigoNegociacao2(idUsuario);
        var resultado = listaAgrupada.stream().collect(groupingBy(BoletaAgrupadoPeloTipoEhCodigoNegociacao2::getCodigoNegociacaoPapel,
                mapping(b -> new Somatoria(b.getTipo(), b.getValorTotal(), b.getQuantidadeTotal()), toList())))
            .entrySet().stream().map(e -> new BoletaAgrupadoPeloCodigoNegociacao2(e.getKey(), e.getValue())).collect(toList());

        logger.info("Listando boletas agrupadas. Quantidade: {}", resultado.size());
        return resultado;
    }
}