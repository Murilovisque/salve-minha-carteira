package com.salveminhacarteira.boleta.extensoes;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Value;

public interface BoletaAgrupadoPeloTipoEhCodigoNegociacao2 {

    Long getId();

    String getTipo();

    @Value("#{target.cod_negociacao}")
    String getCodigoNegociacaoPapel();

    @Value("#{target.quantidadetotal}")
    Integer getQuantidadeTotal();

    @Value("#{target.valortotal}")
    BigDecimal getValorTotal();
    
}