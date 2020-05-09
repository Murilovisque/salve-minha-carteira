package com.salveminhacarteira.boleta.extensoes;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BoletaAgrupadoPeloCodigoNegociacao2 {

    private String codigoNegociacaoPapel;
    private List<Somatoria> somatorias;

}