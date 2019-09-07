package com.salveminhacarteira.usuario;

import static com.salveminhacarteira.usuario.DadosUsuariosTests.usuarioOK;
import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
@EntityScan
public class UsuarioRepositoryTests {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Test
    public void cadastrarUsuarioNoRepo() {
        usuarioRepository.save(usuarioOK());
        var u = usuarioRepository.findAll().iterator().next();
        assertEquals(usuarioOK().getNome(), u.getNome());
        assertEquals(usuarioOK().getEmail(), u.getEmail());
        assertEquals(usuarioOK().getSenha(), u.getSenha());
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void deveLançarExcecaoQuandoTentarSalvarComEmailJáExistente() {
        usuarioRepository.save(usuarioOK());
        usuarioRepository.save(usuarioOK());
        usuarioRepository.findAll();
    }

}