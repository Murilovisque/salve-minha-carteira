package com.salveminhacarteira.excecoes;

import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolation;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class ArgumentosInvalidadosException extends SalveMinhaCarteiraException {

    private static final long serialVersionUID = 1L;

    public <T> ArgumentosInvalidadosException(Set<ConstraintViolation<T>> erros) {
        super(erros.stream().map(ConstraintViolation::getMessage).collect(Collectors.joining(",")));
    }
}