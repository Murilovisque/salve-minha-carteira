package com.salveminhacarteira.acao.excecoes;

import com.salveminhacarteira.excecoes.SalveMinhaCarteiraException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class AcaoNaoEncontradaException  extends SalveMinhaCarteiraException {

    private static final long serialVersionUID = 1L;

    public AcaoNaoEncontradaException() {
        super("Ação não encontrada");
    }

    @Override
    public int httpStatus() {
        return HttpStatus.NOT_FOUND.value();
    }

}