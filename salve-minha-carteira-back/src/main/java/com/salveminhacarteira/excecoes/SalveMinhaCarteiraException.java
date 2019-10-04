package com.salveminhacarteira.excecoes;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
public class SalveMinhaCarteiraException extends Exception {

    private static final long serialVersionUID = 1L;

    public SalveMinhaCarteiraException() {
        super("Ocorreu um erro interno. Por favor tente mais tarde");
    }

    public SalveMinhaCarteiraException(String msg) {
        super(msg);
    }

    public int httpStatus() {
        return HttpStatus.INTERNAL_SERVER_ERROR.value();
    }
}