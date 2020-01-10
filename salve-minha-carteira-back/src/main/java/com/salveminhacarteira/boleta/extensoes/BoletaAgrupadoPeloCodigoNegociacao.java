package com.salveminhacarteira.boleta.extensoes;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BoletaAgrupadoPeloCodigoNegociacao {

    private String codigoNegociacaoPapel;
    private Integer quantidadeTotal;
}