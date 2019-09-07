package com.salveminhacarteira.boleta;

import java.math.BigDecimal;

import com.salveminhacarteira.acao.Acao;

import lombok.Getter;

@Getter
public class BoletasAgrupadasPelaDaAcao {
    
    private Acao acao;
    private BigDecimal valorMedio;
    private BigDecimal valorMáximo;
    private BigDecimal valorMínimo;
}