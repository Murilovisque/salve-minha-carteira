package com.salveminhacarteira.seguranca;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class Token {
    private String hash;
    private Long idUsuario;
    private String email;
}