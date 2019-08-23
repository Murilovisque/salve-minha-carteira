package com.salveminhacarteira.usuarios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuarioManager {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public void cadastrarUsuario(String nome, String email, String senha) {
        usuarioRepository.save(new Usuario(nome, email, senha));
    }
}