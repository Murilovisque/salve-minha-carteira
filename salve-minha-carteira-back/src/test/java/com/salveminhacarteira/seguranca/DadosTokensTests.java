package com.salveminhacarteira.seguranca;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.salveminhacarteira.usuario.Usuario;

interface DadosTokensTests {

    Long USUARIO_ID = 1l;
    String USUARIO_EMAIL = "email-token@domain.com";
    String JWT_ISSUER = "salve-minha-carteira";
    String JWT_SECRET = "secredo";
    int TOKEN_DIAS_DE_VIDA = 7;

    public static Usuario usuarioOK() {
        var u = mock(Usuario.class);
        when(u.getId()).thenReturn(USUARIO_ID);
        when(u.getEmail()).thenReturn(USUARIO_EMAIL);
        return u;
    }
}