package com.salveminhacarteira.usuario;

import static com.salveminhacarteira.usuario.DadosUsuariosTests.usuarioOK;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.salveminhacarteira.acao.Acao;
import com.salveminhacarteira.boleta.Boleta;
import com.salveminhacarteira.empresa.Empresa;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
@EntityScan(basePackageClasses = {Boleta.class, Acao.class, Empresa.class, Usuario.class})
public class UsuarioRepositoryTests {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Test
    public void cadastrarUsuarioNoRepo() {
        assertFalse(usuarioRepository.findById(1L).isPresent());
        usuarioRepository.save(usuarioOK());
        var u = usuarioRepository.findById(1L);
        assertTrue(u.isPresent());
        assertEquals(usuarioOK().getNome(), u.get().getNome());
        assertEquals(usuarioOK().getEmail(), u.get().getEmail());
        assertEquals(usuarioOK().getSenha(), u.get().getSenha());
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void deveLançarExcecaoQuandoTentarSalvarComEmailJáExistente() {
        usuarioRepository.save(usuarioOK());
        usuarioRepository.save(usuarioOK());
        usuarioRepository.findAll();
    }

}