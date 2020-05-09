package com.salveminhacarteira.boleta;

import static com.salveminhacarteira.boleta.DadosBoletaTests.boleta2OK;
import static com.salveminhacarteira.boleta.DadosBoletaTests.boletaOK;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.salveminhacarteira.acao.Acao;
import com.salveminhacarteira.boleta.Boleta.Tipo;
import com.salveminhacarteira.boleta.extensoes.BoletaAgrupadoPeloTipoEhCodigoNegociacao;
import com.salveminhacarteira.empresa.Empresa;
import com.salveminhacarteira.usuario.Usuario;
import com.salveminhacarteira.utilitarios.Checador;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.junit4.SpringRunner;

@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
@RunWith(SpringRunner.class)
@DataJpaTest
@EntityScan(basePackageClasses = {Boleta.class, Acao.class, Empresa.class, Usuario.class})
public class BoletaRepositoryTests {

    @Autowired
    private BoletaRepository boletaRepository;

    private Boleta boletaok;
    private Boleta boleta2ok;

    @Before
    public void deveCadastrarBoletaJuntoComAcaoEhUsuario() {
        assertFalse(boletaRepository.findById(1L).isPresent() || boletaRepository.findById(2L).isPresent());
        boletaok = boletaRepository.save(boletaOK(Tipo.COMPRA));
        testarSeAhBoletaEhOK(boletaok, boletaRepository.findById(1L));
        boleta2ok = boleta2OK(Tipo.COMPRA, boletaok.getUsuario());
        boletaRepository.save(boleta2ok);        
        testarSeAhBoletaEhOK(boleta2ok, boletaRepository.findById(2L));
    }

    @Test
    public void deveCadastrarSoBoleta() {        
        var boleta = boletaOK(Tipo.VENDA);
        boletaRepository.salvar(boleta.getTipo().name(), boleta.getData(), boleta.getValor(), boleta.getQuantidade(), 1L, 1L);
        testarSeAhBoletaEhOK(boleta, boletaRepository.findById(3L));
    }

    @Test
    public void devolverBoletosAgrupadosPeloCodigoNegociacaoPeloTipo() {
        var boletaAgrupadook = BoletaAgrupadoPeloTipoEhCodigoNegociacao.construir(boletaok.getTipo(), 
            boletaok.getAcao().getCodigoNegociacaoPapel(), boletaok.getQuantidade());
        var boletaAgrupado2ok = BoletaAgrupadoPeloTipoEhCodigoNegociacao.construir(boleta2ok.getTipo(),
            boleta2ok.getAcao().getCodigoNegociacaoPapel(), boleta2ok.getQuantidade());

        var listEsperada = new ArrayList<>(List.of(boletaAgrupadook, boletaAgrupado2ok));
        var listAgrupado = boletaRepository.obterBoletasAgrupadasPeloTipoEhCodigoNegociacaoBuscandoPeloTipo(boletaok.getUsuario().getId(), boletaok.getTipo().name());
        assertFalse(listAgrupado.isEmpty());

        Checador.validarListas(listEsperada, listAgrupado, (esperado, recebido) -> esperado.getCodigoNegociacaoPapel().equals(recebido.getCodigoNegociacaoPapel())
            && esperado.getQuantidadeTotal().equals(recebido.getQuantidadeTotal())
            && esperado.getTipo().equals(recebido.getTipo()));
    }

    @Test
    public void devolverBoletosAgrupadosPeloTipoEhCodigoNegociacao() {
        var boleta = boletaOK(Tipo.VENDA);
        boletaRepository.salvar(boleta.getTipo().name(), boleta.getData(), boleta.getValor(), boleta.getQuantidade(), 1L, 1L);
        var boletaAgrupadoVendaok = BoletaAgrupadoPeloTipoEhCodigoNegociacao.construir(boleta.getTipo(),
            boleta.getAcao().getCodigoNegociacaoPapel(), boleta.getQuantidade());
        var boletaAgrupadoCompraok = BoletaAgrupadoPeloTipoEhCodigoNegociacao.construir(boletaok.getTipo(), 
            boletaok.getAcao().getCodigoNegociacaoPapel(), boletaok.getQuantidade());
        var boletaAgrupado2Compraok = BoletaAgrupadoPeloTipoEhCodigoNegociacao.construir(boleta2ok.getTipo(), 
            boleta2ok.getAcao().getCodigoNegociacaoPapel(), boleta2ok.getQuantidade());            
     
        var listEsperada = new ArrayList<>(List.of(boletaAgrupadoCompraok, boletaAgrupado2Compraok, boletaAgrupadoVendaok));
        var listAgrupado = boletaRepository.obterBoletasAgrupadasPeloTipoEhCodigoNegociacao(boletaok.getUsuario().getId());
        assertFalse(listAgrupado.isEmpty());

        Checador.validarListas(listEsperada, listAgrupado, (esperado, recebido) -> esperado.getCodigoNegociacaoPapel().equals(recebido.getCodigoNegociacaoPapel())
            && esperado.getQuantidadeTotal().equals(recebido.getQuantidadeTotal())
            && esperado.getTipo().equals(recebido.getTipo()));
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void cadastrarDeveLançarExceçãoQuandoAcaoNaoExiste() {
        boletaRepository.salvar(boletaok.getTipo().name(), boletaok.getData(), boletaok.getValor(), boletaok.getQuantidade(), 50L, 1L);
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void cadastrarDeveLançarExceçãoQuandoUsuarioNaoExiste() {
        boletaRepository.salvar(boletaok.getTipo().name(), boletaok.getData(), boletaok.getValor(), boletaok.getQuantidade(), 1L, 50L);
    }

    @Test()
    public void obterBoletaPeloTipoDataUsuarioAcaoEhValor() {
        var boletaPeloTipoEhData = boletaRepository.obterBoletaPeloTipoDataAcaoEhValor(boletaok.getUsuario().getId(), boletaok.getTipo().name(), boletaok.getData(), boletaok.getAcao().getId(), boletaok.getValor());
        testarSeAhBoletaEhOK(boletaok, boletaPeloTipoEhData);
    }

    // table dual does not work in unit tests
    // @Test()
    // public void obterQuantidadeTotalDeBoletaCompra() {
    //     var boleta = boleta3OK(Tipo.COMPRA);
    //     boletaRepository.salvar(boleta.getTipo().name(), boleta.getData(), boleta.getValor(), boleta.getQuantidade(), boletaok.getAcao().getId(), boletaok.getUsuario().getId());
    //     var quantidadeEsperada = boletaok.getQuantidade() + boleta.getQuantidade();
    //     var quantidadeChecada = boletaRepository.obterQuantidadeTotalComSaldoPositivoDaAcao(boletaok.getUsuario().getId(), boletaok.getAcao().getId());
    //     assertTrue(quantidadeChecada.isPresent());
    //     assertEquals(quantidadeEsperada, quantidadeChecada.get().intValue());
    // }
    
    private void testarSeAhBoletaEhOK(Boleta boletaEsperada, Optional<Boleta> boleta) {
        assertTrue(boleta.isPresent());
        assertEquals(boletaEsperada.getTipo(), boleta.get().getTipo());
        assertEquals(boletaEsperada.getData(), boleta.get().getData());
        assertEquals(boletaEsperada.getQuantidade(), boleta.get().getQuantidade());
        assertEquals(boletaEsperada.getValor(), boleta.get().getValor());
        assertEquals(boletaEsperada.getAcao().getCodigoNegociacaoPapel(), boleta.get().getAcao().getCodigoNegociacaoPapel());
        assertEquals(boletaEsperada.getUsuario().getNome(), boleta.get().getUsuario().getNome());
    }
}