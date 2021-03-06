package com.salveminhacarteira.boleta.extensoes;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Somatoria {
    
    String tipo;
    BigDecimal valorTotal;
    Integer quantidadeTotal;
}