package com.salveminhacarteira.acao;

import static com.salveminhacarteira.empresa.DadosEmpresasTests.*;

public final class DadosAcoesTests {

    public static Acao acaoOK() {
        return new Acao("ABEV3", empresaOK());
    }
}