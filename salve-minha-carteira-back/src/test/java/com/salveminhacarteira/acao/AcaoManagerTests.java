package com.salveminhacarteira.acao;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.ArgumentMatchers.anyString;

import com.salveminhacarteira.excecoes.ArgumentosInvalidadosException;
import com.salveminhacarteira.excecoes.SalveMinhaCarteiraException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class AcaoManagerTests {

    @Autowired
    private AcaoManager acaoManager;

    @MockBean
    private AcaoRepository acaoRepository;

    @Test(expected = ArgumentosInvalidadosException.class)
    public void procurarAcoesPeloCodigoNegociacaoOuNomeEmpresaDeveRetornarArgumentoInvalidoQuandoHaEspacoNoComeco() throws SalveMinhaCarteiraException {
        acaoManager.procurarAcoesPeloCodigoNegociacaoOuNomeEmpresa(" D");
    }

    @Test
    public void procurarAcoesPeloCodigoNegociacaoOuNomeEmpresa() throws SalveMinhaCarteiraException {
        acaoManager.procurarAcoesPeloCodigoNegociacaoOuNomeEmpresa("D");
        verify(acaoRepository, times(1)).procurarAcoesPeloCodigoNegociacaoOuNomeEmpresa(anyString(), anyString());
    }

    @TestConfiguration
    static class BoletaManagerTestsContextConfiguration {  
        @Bean
        public AcaoRepository acaoRepository() {
            return mock(AcaoRepository.class);
        }

        @Bean
        public AcaoManager acaoManager() {
            return new AcaoManager();
        }
    }

}