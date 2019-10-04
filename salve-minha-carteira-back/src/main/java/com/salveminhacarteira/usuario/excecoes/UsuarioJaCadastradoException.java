package com.salveminhacarteira.usuario.excecoes;

import com.salveminhacarteira.excecoes.SalveMinhaCarteiraException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT)
public class UsuarioJaCadastradoException extends SalveMinhaCarteiraException {

    private static final long serialVersionUID = 1L;

    public UsuarioJaCadastradoException() {
        super("Usuario já cadastrado");
    }

    @Override
    public int httpStatus() {
        return HttpStatus.CONFLICT.value();
    }
}