package com.salveminhacarteira.acao;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Getter;

@Entity
@Getter
public class Acao {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)    
    private Long id;

    private String codigo;   
}