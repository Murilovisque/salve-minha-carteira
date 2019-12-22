package com.salveminhacarteira.boleta.extensoes;

import java.math.BigDecimal;

import com.salveminhacarteira.boleta.Boleta.Tipo;

import org.springframework.beans.factory.annotation.Value;

public interface BoletaAgrupadoPeloCodigoNegociacao {

    String getTipo();

    @Value("#{target.cod_negociacao}")
    String getCodigoNegociacaoPapel();

    @Value("#{target.somatotal}")
    BigDecimal getSomaTotal();

    @Value("#{target.quantidadetotal}")
    Integer getQuantidadeTotal();

    static BoletaAgrupadoPeloCodigoNegociacao build(Tipo tipo, String codigoNegociacaoPapel, BigDecimal somaTotal, Integer quantidadeTotal) {
        return new BoletaAgrupadoPeloCodigoNegociacaoImpl(tipo, codigoNegociacaoPapel, somaTotal, quantidadeTotal);
    }

    default BoletaAgrupadoPeloCodigoNegociacao mesclarPelaQuantidadeEhTipo(BoletaAgrupadoPeloCodigoNegociacao boletaAgrupadoPeloCodigoNegociacao) {
        Integer quantidade = 0;
        if (this.getTipo().equals(boletaAgrupadoPeloCodigoNegociacao.getTipo())
            quantidade = this.getQuantidadeTotal()
    }

    static class BoletaAgrupadoPeloCodigoNegociacaoImpl implements BoletaAgrupadoPeloCodigoNegociacao {
        private Tipo tipo;

        private String codigoNegociacaoPapel;

        private BigDecimal somaTotal;

        private Integer quantidadeTotal;

        BoletaAgrupadoPeloCodigoNegociacaoImpl(Tipo tipo, String codigoNegociacaoPapel, BigDecimal somaTotal, Integer quantidadeTotal) {
            this.tipo = tipo;
            this.codigoNegociacaoPapel = codigoNegociacaoPapel;
            this.somaTotal = somaTotal;
            this.quantidadeTotal = quantidadeTotal;
        }

        @Override
        public String getTipo() {
            return tipo.name();
        }

        @Override
        public String getCodigoNegociacaoPapel() {
            return codigoNegociacaoPapel;
        }

        @Override
        public BigDecimal getSomaTotal() {
            return somaTotal;
        }

        @Override
        public Integer getQuantidadeTotal() {
            return quantidadeTotal;
        }
    }
}