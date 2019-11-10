package com.salveminhacarteira.acao;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.salveminhacarteira.empresa.Empresa;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Entity
@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Acao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)    
    @EqualsAndHashCode.Include
    private Long id;

    private String codigoNegociacaoPapel;

    @ManyToOne(fetch = FetchType.LAZY)
    private Empresa empresa;
}