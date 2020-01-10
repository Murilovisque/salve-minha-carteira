package com.salveminhacarteira.boleta.extensoes;

import com.salveminhacarteira.boleta.Boleta;

import org.springframework.beans.factory.annotation.Value;

public interface BoletaAgrupadoPeloTipoEhCodigoNegociacao {

    String getTipo();

    @Value("#{target.cod_negociacao}")
    String getCodigoNegociacaoPapel();

    @Value("#{target.quantidadetotal}")
    Integer getQuantidadeTotal();

    static BoletaAgrupadoPeloTipoEhCodigoNegociacao construir(Boleta.Tipo tipo, String codigoNegociacaoPapel, Integer quantidadeTotal) {
        return new BoletaAgrupadoPeloTipoEhCodigoNegociacaoImpl(tipo, codigoNegociacaoPapel, quantidadeTotal);
    }

    default BoletaAgrupadoPeloTipoEhCodigoNegociacao mesclarPelaQuantidade(BoletaAgrupadoPeloTipoEhCodigoNegociacao boletaParaMesclar) {
        if (!getCodigoNegociacaoPapel().equals(boletaParaMesclar.getCodigoNegociacaoPapel()))
            throw new IllegalArgumentException("Boleta agrupada não pode ser mesclada pois não tem o mesmo codigo de negociacao do papel");

        Integer quantidadeTotal = null;
        Boleta.Tipo tipo = Boleta.Tipo.valueOf(getTipo());
        if (getTipo().equals(boletaParaMesclar.getTipo())) {
            quantidadeTotal = getQuantidadeTotal() + boletaParaMesclar.getQuantidadeTotal();
        } else {
            quantidadeTotal = getQuantidadeTotal() - boletaParaMesclar.getQuantidadeTotal();
            if (quantidadeTotal < 1) {
                tipo = tipo.obterOhInverso();    
                quantidadeTotal *= -1;
            }
        }
        return construir(tipo, getCodigoNegociacaoPapel(), quantidadeTotal);
    }

    static class BoletaAgrupadoPeloTipoEhCodigoNegociacaoImpl implements BoletaAgrupadoPeloTipoEhCodigoNegociacao {
        private Boleta.Tipo tipo;
        private String codigoNegociacaoPapel;
        private Integer quantidadeTotal;

        BoletaAgrupadoPeloTipoEhCodigoNegociacaoImpl(Boleta.Tipo tipo, String codigoNegociacaoPapel, Integer quantidadeTotal) {
            this.tipo = tipo;
            this.codigoNegociacaoPapel = codigoNegociacaoPapel;
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
        public Integer getQuantidadeTotal() {
            return quantidadeTotal;
        }
    }
}