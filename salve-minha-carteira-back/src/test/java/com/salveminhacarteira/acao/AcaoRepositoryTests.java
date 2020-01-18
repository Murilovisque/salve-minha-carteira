package com.salveminhacarteira.acao;

import static com.salveminhacarteira.acao.DadosAcoesTests.acao2OK;
import static com.salveminhacarteira.acao.DadosAcoesTests.acaoOK;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Optional;

import com.salveminhacarteira.boleta.Boleta;
import com.salveminhacarteira.empresa.Empresa;
import com.salveminhacarteira.usuario.Usuario;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
@DataJpaTest
@EntityScan(basePackageClasses = {Boleta.class, Acao.class, Empresa.class, Usuario.class})
public class AcaoRepositoryTests {

    @Autowired
    private AcaoRepository acaoRepository;

    @Test
    public void cadastrarAcaoComEmpresaEhObterPeloIdOuCodigoNegociacao() {
        assertFalse(acaoRepository.findById(1L).isPresent());
        acaoRepository.save(acaoOK());
        var acao = acaoRepository.findById(1L);
        testarSeAcaoEhOK(acao);
        acao = acaoRepository.obterAcaoPeloCodigoNegociacaoPapel(acaoOK().getCodigoNegociacaoPapel());
        testarSeAcaoEhOK(acao);
        acao = acaoRepository.obterAcaoPeloCodigoNegociacaoPapel("AlgumCodigoQueNaoExiste");
        assertFalse(acao.isPresent());
    }

    @Test
    public void procurarAcoesPeloCodigoNegociacao() {
        var acao = acaoRepository.save(acaoOK());
        acaoRepository.save(acao2OK());
        var lista = acaoRepository
            .procurarAcoesPeloCodigoNegociacaoOuNomeEmpresa(acaoOK().getCodigoNegociacaoPapel().substring(0, 2), "ValorQueNaoIraEncontrar");
        assertEquals(1, lista.size());
        testarSeAcaoEhOK(acao);
    }

    @Test
    public void procurarAcoesPeloNomeDaEmpresa() {
        var acao = acaoRepository.save(acaoOK());
        acaoRepository.save(acao2OK());
        var lista = acaoRepository
            .procurarAcoesPeloCodigoNegociacaoOuNomeEmpresa("ValorQueNaoIraEncontrar", acaoOK().getEmpresa().getNome().substring(0, 2));
        assertEquals(1, lista.size());
        testarSeAcaoEhOK(acao);
    }

    private void testarSeAcaoEhOK(Optional<Acao> acao) {
        assertTrue(acao.isPresent());
        testarSeAcaoEhOK(acao.get());
    }

    private void testarSeAcaoEhOK(Acao acao) {
        assertEquals(acao.getCodigoNegociacaoPapel(), acaoOK().getCodigoNegociacaoPapel());
        assertNotNull(acao.getEmpresa());
    }
}