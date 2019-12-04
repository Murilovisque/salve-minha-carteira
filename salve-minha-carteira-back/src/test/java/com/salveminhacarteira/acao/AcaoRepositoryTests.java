package com.salveminhacarteira.acao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Optional;

import com.salveminhacarteira.boleta.Boleta;
import static com.salveminhacarteira.acao.DadosAcoesTests.*;
import com.salveminhacarteira.empresa.Empresa;
import com.salveminhacarteira.usuario.Usuario;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
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

    private void testarSeAcaoEhOK(Optional<Acao> acao) {
        assertTrue(acao.isPresent());
        assertEquals(acao.get().getCodigoNegociacaoPapel(), acaoOK().getCodigoNegociacaoPapel());
        assertNotNull(acao.get().getEmpresa());
    }
}