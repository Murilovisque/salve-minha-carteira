package com.salveminhacarteira.usuario;

import java.security.Principal;

import com.salveminhacarteira.excecoes.SalveMinhaCarteiraException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lombok.Getter;

@RestController
@RequestMapping("/usuarios")
class UsuarioResources {

    @Autowired
    private UsuarioManager usuarioManager;

    @PostMapping()
    @ResponseStatus(code = HttpStatus.CREATED)
    public void cadastrarUsuario(@RequestBody UsuarioRequestBody usuarioRequestBody) throws SalveMinhaCarteiraException {
        usuarioManager.cadastrarUsuario(usuarioRequestBody.getNome(), usuarioRequestBody.getEmail(), usuarioRequestBody.getSenha());
    }

    @RequestMapping("/login")
    public Principal login(Principal user) {
        return user;
    }

    @Getter    
    static class UsuarioRequestBody {
        private String nome;
        private String email;
        private String senha;
    }    
}

