package com.salveminhacarteira.utilitarios;

import java.util.regex.Pattern;

public final class Validador {
    public static final String EMAIL_REGEX_PATTERN = "^[^@.]+@[^@.]+\\.[^@]+$";
    private static final Pattern EMAIL_REGEX = Pattern.compile(EMAIL_REGEX_PATTERN);

    public static boolean formatoEmailEhValido(String email) {
        return (email == null || EMAIL_REGEX.matcher(email).matches());            
    }
}