package com.salveminhacarteira.boleta;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Optional;

import com.salveminhacarteira.acao.Acao;
import com.salveminhacarteira.boleta.Boleta.Tipo;
import com.salveminhacarteira.empresa.Empresa;
import com.salveminhacarteira.usuario.Usuario;

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

import static com.salveminhacarteira.boleta.DadosBoletaTests.*;

@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
@RunWith(SpringRunner.class)
@DataJpaTest
@EntityScan(basePackageClasses = {Boleta.class, Acao.class, Empresa.class, Usuario.class})
public class BoletaRepositoryTests {

    @Autowired
    private BoletaRepository boletaRepository;

    private Boleta boletaok = boletaOK(Tipo.COMPRA);

    @Before
    public void configurar() {
        assertFalse(boletaRepository.findById(1L).isPresent());
        boletaRepository.save(boletaok);
        testarSeAhBoletaEhOK(boletaRepository.findById(1L));
        assertFalse(boletaRepository.findById(2L).isPresent());
    }

    @Test
    public void cadastrarBoletaDeveFuncionar() {        
        boletaRepository.salvar(boletaok.getTipo().name(), boletaok.getData(), boletaok.getValor(), boletaok.getQuantidade(), 1L, 1L);
        testarSeAhBoletaEhOK(boletaRepository.findById(2L));
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void cadastrarDeveLançarExceçãoQuandoAcaoNaoExiste() {
        boletaRepository.salvar(boletaok.getTipo().name(), boletaok.getData(), boletaok.getValor(), boletaok.getQuantidade(), 2L, 1L);
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void cadastrarDeveLançarExceçãoQuandoUsuarioNaoExiste() {
        boletaRepository.salvar(boletaok.getTipo().name(), boletaok.getData(), boletaok.getValor(), boletaok.getQuantidade(), 1L, 2L);
    }

    private void testarSeAhBoletaEhOK(Optional<Boleta> boleta) {
        assertTrue(boleta.isPresent());
        assertEquals(boletaok.getTipo(), boleta.get().getTipo());
        assertEquals(boletaok.getData(), boleta.get().getData());
        assertEquals(boletaok.getQuantidade(), boleta.get().getQuantidade());
        assertEquals(boletaok.getValor(), boleta.get().getValor());
        assertEquals(boletaok.getAcao().getCodigoNegociacaoPapel(), boleta.get().getAcao().getCodigoNegociacaoPapel());
        assertEquals(boletaok.getUsuario().getNome(), boleta.get().getUsuario().getNome());
    }
}