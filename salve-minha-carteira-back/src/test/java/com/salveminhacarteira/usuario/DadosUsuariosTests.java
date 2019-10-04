package com.salveminhacarteira.usuario;

interface DadosUsuariosTests {

    public static Usuario usuarioOK() {
        return new Usuario("Murilo", "murilo@domain.com", "senha");
    }

}