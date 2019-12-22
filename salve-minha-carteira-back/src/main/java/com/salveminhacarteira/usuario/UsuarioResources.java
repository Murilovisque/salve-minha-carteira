package com.salveminhacarteira.usuario;


import com.salveminhacarteira.excecoes.SalveMinhaCarteiraException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import lombok.Getter;

@RestController
@RequestMapping("/api/usuarios")
class UsuarioResources {

    @Autowired
    private UsuarioManager usuarioManager;

    @PostMapping()
    @ResponseStatus(code = HttpStatus.CREATED)
    public void cadastrar(@RequestParam("nome") String nome, @RequestParam("email") String email, @RequestParam("senha") String senha)
            throws SalveMinhaCarteiraException {
        usuarioManager.cadastrar(nome, email, senha);
    }

    @RequestMapping("/autenticar")
    @PostMapping
    public ResponseEntity<Token> autenticar(@RequestParam("email") String email, @RequestParam("senha") String senha) throws SalveMinhaCarteiraException {
        return ResponseEntity.ok(new Token(usuarioManager.autenticarEhObterToken(email, senha).getHash()));
    }

    @Getter
    @AllArgsConstructor
    static class Token {
        String hash;
    }
}

