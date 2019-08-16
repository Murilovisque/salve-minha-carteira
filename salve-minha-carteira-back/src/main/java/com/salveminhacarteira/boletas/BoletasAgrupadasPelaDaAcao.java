package com.salveminhacarteira.boletas;

import java.math.BigDecimal;

import com.salveminhacarteira.acoes.Acao;

import lombok.Getter;

@Getter
public class BoletasAgrupadasPelaDaAcao {
    
    private Acao acao;
    private BigDecimal valorMedio;
    private BigDecimal valorMáximo;
    private BigDecimal valorMínimo;
}