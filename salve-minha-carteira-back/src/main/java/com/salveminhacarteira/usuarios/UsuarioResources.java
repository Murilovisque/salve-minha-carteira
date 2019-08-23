package com.salveminhacarteira.usuarios;

import com.fasterxml.jackson.annotation.JsonProperty;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.Getter;

@RestController
@RequestMapping("/usuarios")
public class UsuarioResources {

    @Autowired
    private UsuarioManager usuarioManager;

    @PostMapping()
    public void cadastrarUsuario(@RequestBody UsuarioRequestBody usuarioRequestBody) {
        usuarioManager.cadastrarUsuario(usuarioRequestBody.getNome(), usuarioRequestBody.getEmail(), usuarioRequestBody.getSenha());
    }

    @Getter    
    static class UsuarioRequestBody {
        @JsonProperty(required = true)
        private String nome;
        @JsonProperty(required = true)
        private String email;
        @JsonProperty(required = true)
        private String senha;
    }    
}

