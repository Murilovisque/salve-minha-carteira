package com.salveminhacarteira.seguranca;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.salveminhacarteira.usuario.Usuario;

public final class DadosTokensTests {

    public static final Long USUARIO_ID = 1l;
    public static final String USUARIO_EMAIL = "email-token@domain.com";
    public static final String JWT_ISSUER = "salve-minha-carteira";
    public static final String JWT_SECRET = "secreto";
    public static final int TOKEN_DIAS_DE_VIDA = 7;

    public static Usuario usuarioOK() {
        var u = mock(Usuario.class);
        when(u.getId()).thenReturn(USUARIO_ID);
        when(u.getEmail()).thenReturn(USUARIO_EMAIL);
        return u;
    }

    public static Token tokenOK() {
        return new Token("xvwewf1312", USUARIO_ID, USUARIO_EMAIL);
    }
}