package com.salveminhacarteira.boletas;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import com.salveminhacarteira.acoes.Acao;

import lombok.Getter;

@Entity
@Getter
public class Boleta {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Tipo tipo;

    private LocalDateTime data;
    
    private BigDecimal valor;

    private Integer quantidade;

    @OneToOne(fetch = FetchType.EAGER, targetEntity = Boleta.class)
    private Acao acao;

    public static enum Tipo {
        VENDA, COMPRA
    }
}


