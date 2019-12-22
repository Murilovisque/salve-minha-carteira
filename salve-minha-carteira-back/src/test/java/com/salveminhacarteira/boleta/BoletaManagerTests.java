package com.salveminhacarteira.boleta;

import static com.salveminhacarteira.boleta.DadosBoletaTests.boletaOK;
import static com.salveminhacarteira.seguranca.DadosTokensTests.tokenOK;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.SQLIntegrityConstraintViolationException;

import javax.validation.Validation;
import javax.validation.Validator;

import com.salveminhacarteira.boleta.Boleta.Tipo;
import com.salveminhacarteira.excecoes.ArgumentosInvalidadosException;
import com.salveminhacarteira.excecoes.SalveMinhaCarteiraException;
import com.salveminhacarteira.seguranca.TokenManager;
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