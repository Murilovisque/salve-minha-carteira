package com.salveminhacarteira.boleta;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import com.salveminhacarteira.acao.Acao;
import com.salveminhacarteira.usuario.Usuario;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Entity
@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(uniqueConstraints = {@UniqueConstraint(columnNames={"id_acao", "tipo", "data"})})
public class Boleta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @NotNull(message = MensagensBoleta.TIPO_INVALIDO)
    @Enumerated(EnumType.STRING)
    private Tipo tipo;

    @NotNull(message = MensagensBoleta.DATA_INVALIDA)
    private LocalDate data;
    
    @Positive(message = MensagensBoleta.VALOR_INVALIDO)
    @DecimalMax(value = "9999999999.99", message = MensagensBoleta.VALOR_INVALIDO)
    private BigDecimal valor;

    @Positive(message = MensagensBoleta.QUANTIDADE_INVALIDA)
    private Integer quantidade;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "id_acao")
    @NotNull(message = MensagensBoleta.ACAO_INVALIDA)
    private Acao acao;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "id_usuario")
    @NotNull(message = MensagensBoleta.USUARIO_INVALIDO)
    private Usuario usuario;

    Boleta() {}

    Boleta(Tipo tipo, LocalDate data, BigDecimal valor, Integer quantidade, Acao acao, Usuario usuario) {
        this.tipo = tipo;
        this.data = data;
        this.valor = valor;
        this.quantidade = quantidade;
        this.acao = acao;
        this.usuario = usuario;
    }

    public static enum Tipo {
        VENDA, COMPRA;

        public Tipo obterOhInverso() {
            return this.equals(COMPRA)? VENDA : COMPRA;
        }
    }

    public void incrementarQuantidade(Boleta boleta) {
        this.quantidade += boleta.quantidade;
    }
}


