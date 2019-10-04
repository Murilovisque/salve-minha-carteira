package com.salveminhacarteira.usuario;

import com.salveminhacarteira.seguranca.TokenManager;
import com.salveminhacarteira.excecoes.SalveMinhaCarteiraException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import lombok.Getter;

@RestController
@RequestMapping("/usuarios")
class UsuarioResources {

    private static final Logger logger = LoggerFactory.getLogger(UsuarioResources.class);

    @Autowired
    private UsuarioManager usuarioManager;

    @PostMapping()
    @ResponseStatus(code = HttpStatus.CREATED)
    public void cadastrarUsuario(@RequestBody UsuarioRequestBody usuarioRequestBody) throws SalveMinhaCarteiraException {
        usuarioManager.cadastrarUsuario(usuarioRequestBody.getNome(), usuarioRequestBody.getEmail(), usuarioRequestBody.getSenha());
    }

    @RequestMapping("/autenticar")
    @PostMapping
    public ResponseEntity<?> autenticar(@RequestBody UsuarioAutenticadorRequestBody usuarioRequestBody) throws SalveMinhaCarteiraException {
        String token = usuarioManager.autenticarEhObterToken(usuarioRequestBody.getEmail(), usuarioRequestBody.getSenha());
        return ResponseEntity.ok(token);
    }

    @RequestMapping("/recurso-protegido")
    @GetMapping
    public String recursoProtegido() {
        logger.info("recurso protegido");
        return "";
    } 

    @Getter    
    static class UsuarioRequestBody {
        private String nome;
        private String email;
        private String senha;
    }   
    
    @Getter
    static class UsuarioAutenticadorRequestBody {
        private String email;
        private String senha;
    }

    @Getter
    @AllArgsConstructor
    static class Token {
        String token;
    }
}

