package com.salveminhacarteira.acao;

import static com.salveminhacarteira.empresa.DadosEmpresasTests.*;

public final class DadosAcoesTests {

    public static Acao acaoOK() {
        return new Acao("ABEV3", empresaOK());
    }

    public static Acao acao2OK() {
        return new Acao("ITSA4", empresa2OK());
    }
}