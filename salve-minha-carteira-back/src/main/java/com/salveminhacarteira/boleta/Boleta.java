package com.salveminhacarteira.boleta;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.salveminhacarteira.acao.Acao;

import lombok.EqualsAndHashCode;
import lombok.Getter;

//@Entity
@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Boleta {

    // @Id
    // @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    private Tipo tipo;

    private LocalDateTime data;
    
    private BigDecimal valor;

    private Integer quantidade;

    @ManyToOne(fetch = FetchType.EAGER, targetEntity = Boleta.class)
    private Acao acao;

    public static enum Tipo {
        VENDA, COMPRA
    }

    
}


