package com.salveminhacarteira.seguranca.excecoes;

import com.salveminhacarteira.excecoes.SalveMinhaCarteiraException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.UNAUTHORIZED)
public class TokenException extends SalveMinhaCarteiraException {

    private static final long serialVersionUID = 1L;

    public TokenException() {
        super("Não autorizado");
    }

    @Override
    public int httpStatus() {
        return HttpStatus.UNAUTHORIZED.value();
    }
}