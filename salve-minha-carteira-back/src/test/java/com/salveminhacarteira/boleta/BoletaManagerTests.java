package com.salveminhacarteira.boleta;

import static com.salveminhacarteira.acao.DadosAcoesTests.acao2OK;
import static com.salveminhacarteira.acao.DadosAcoesTests.acaoOK;
import static com.salveminhacarteira.boleta.DadosBoletaTests.boletaAgrupadaPeloTipoEhCodigoNegociacaoOK;
import static com.salveminhacarteira.boleta.DadosBoletaTests.boletaAgrupadaPeloCodigoNegociacaoOK;
import static com.salveminhacarteira.boleta.DadosBoletaTests.boletaOK;
import static com.salveminhacarteira.seguranca.DadosTokensTests.tokenOK;
import static org.junit.Assert.assertFalse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.sql.SQLIntegrityConstraintViolationException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Validation;
import javax.validation.Validator;

import com.salveminhacarteira.boleta.Boleta.Tipo;
import com.salveminhacarteira.boleta.extensoes.BoletaAgrupadoPeloTipoEhCodigoNegociacao;
import com.salveminhacarteira.excecoes.ArgumentosInvalidadosException;
import com.salveminhacarteira.excecoes.SalveMinhaCarteiraException;
import com.salveminhacarteira.seguranca.TokenManager;
import com.salveminhacarteira.utilitarios.Checador;
import com.salveminhacarteira.utilitarios.Erros;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class BoletaManagerTests {

    @Autowired
    private BoletaManager boletaManager;

    @MockBean
    private TokenManager tokenManager;

    @MockBean
    private BoletaRepository boletaRepository;

    private Boleta boletaok = boletaOK(Tipo.COMPRA);

    @Before
    public void configurar() {
        when(tokenManager.obterTokenDaRequisicao()).thenReturn(tokenOK());
    }

    @Test
    public void deveCadastrarBoletaComSucesso() throws SalveMinhaCarteiraException {        
        boletaManager.cadastrarOuAtualizar(boletaok.getTipo(), boletaok.getData(), boletaok.getValor(), boletaok.getQuantidade(), boletaok.getAcao().getId());
        verify(boletaRepository, times(1)).salvar(eq(boletaok.getTipo().name()), eq(boletaok.getData()), eq(boletaok.getValor()),
            eq(boletaok.getQuantidade()), eq(boletaok.getAcao().getId()), eq(tokenOK().getIdUsuario()));
    }

    @Test(expected = ArgumentosInvalidadosException.class)
    public void deveLançarExcecaoAcaoNaoEncontradaExceptionQuandoOcorrerDataIntegrityViolationException() throws SalveMinhaCarteiraException {
        var ex = mock(SQLIntegrityConstraintViolationException.class);
        when(ex.getErrorCode()).thenReturn(Erros.MYSQL_ER_NO_REFERENCED_ROW_2);
        doThrow(new DataIntegrityViolationException("Error constraint", ex)).when(boletaRepository).salvar(eq(boletaok.getTipo().name()), eq(boletaok.getData()), eq(boletaok.getValor()),
            eq(boletaok.getQuantidade()), eq(boletaok.getAcao().getId()), eq(tokenOK().getIdUsuario()));
        boletaManager.cadastrarOuAtualizar(boletaok.getTipo(), boletaok.getData(), boletaok.getValor(), boletaok.getQuantidade(), boletaok.getAcao().getId());
    }

    @Test(expected = ArgumentosInvalidadosException.class)
    public void deveLançarExcecaoQuandoTentaVenderMaisAcoesQueComprou() throws SalveMinhaCarteiraException {
        var boletaVenda = boletaOK(Tipo.VENDA);
        when(boletaRepository.obterQuantidadeTotalComSaldoPositivoDaAcao(anyLong(), anyLong())).thenReturn(Optional.empty());
        boletaManager.cadastrarOuAtualizar(boletaVenda.getTipo(), boletaVenda.getData(), boletaVenda.getValor(), boletaVenda.getQuantidade(), boletaVenda.getAcao().getId());
        verify(boletaRepository, never()).salvar(anyString(), any(LocalDate.class), any(BigDecimal.class), anyInt(), anyLong(), anyLong());
    }

    @Test
    public void obterBoletasAgrupadasPeloCodigoNegociacaoComSaldoPositivo() {
        var listaAgrupada = new ArrayList<BoletaAgrupadoPeloTipoEhCodigoNegociacao>();        
        listaAgrupada.add(boletaAgrupadaPeloTipoEhCodigoNegociacaoOK(Boleta.Tipo.COMPRA, acaoOK().getCodigoNegociacaoPapel(), 5));
        listaAgrupada.add(boletaAgrupadaPeloTipoEhCodigoNegociacaoOK(Boleta.Tipo.VENDA, acaoOK().getCodigoNegociacaoPapel(), 2));
        listaAgrupada.add(boletaAgrupadaPeloTipoEhCodigoNegociacaoOK(Boleta.Tipo.COMPRA, acao2OK().getCodigoNegociacaoPapel(), 2));
        listaAgrupada.add(boletaAgrupadaPeloTipoEhCodigoNegociacaoOK(Boleta.Tipo.VENDA, acao2OK().getCodigoNegociacaoPapel(), 2));

        when(boletaRepository.obterBoletasAgrupadasPeloTipoEhCodigoNegociacao(anyLong())).thenReturn(listaAgrupada);
        var listaParaChecar = boletaManager.obterBoletasAgrupadasPeloCodigoNegociacaoComSaldoPositivo();
        assertFalse(listaParaChecar.isEmpty());
        var listaEsperada = new ArrayList<>(List.of(boletaAgrupadaPeloCodigoNegociacaoOK(acaoOK().getCodigoNegociacaoPapel(), 3)));
        Checador.validarListas(listaEsperada, listaParaChecar, (b1, b2) -> b1.getCodigoNegociacaoPapel().equals(b2.getCodigoNegociacaoPapel())
            && b1.getQuantidadeTotal().equals(b2.getQuantidadeTotal()));
    }

    @TestConfiguration
    static class BoletaManagerTestsContextConfiguration {  
        @Bean
        public BoletaManager boletaManager() {
            return new BoletaManager();
        }

        @Bean
        public Validator validator() {
            return Validation.buildDefaultValidatorFactory().getValidator();
        }

        @Bean
        public TokenManager tokenManager() {
            return mock(TokenManager.class);
        }

        @Bean
        public BoletaRepository boletaRepository() {
            return mock(BoletaRepository.class);
        }
    }
}