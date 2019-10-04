package com.salveminhacarteira.validadores;

import java.util.regex.Pattern;

public final class Validador {
    private static final Pattern EMAIL_REGEX = Pattern.compile("^[^@.]+@[^@.]+\\.[^@]+$");

    public static boolean formatoEmailEhValido(String email) {
        return (email == null || EMAIL_REGEX.matcher(email).matches());            
    }
}