package com.salveminhacarteira.boleta;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.salveminhacarteira.boleta.Boleta.Tipo;
import com.salveminhacarteira.boleta.extensoes.BoletaAgrupadoPeloCodigoNegociacao;
import com.salveminhacarteira.boleta.extensoes.BoletaAgrupadoPeloTipoEhCodigoNegociacao;
import com.salveminhacarteira.usuario.Usuario;

import static com.salveminhacarteira.acao.DadosAcoesTests.*;
import static com.salveminhacarteira.usuario.DadosUsuariosTests.*;

public final class DadosBoletaTests {

    public static Boleta boletaOK(Tipo tipoBoleta) {
        return new Boleta(tipoBoleta, LocalDate.now(), new BigDecimal("10.00"), 2, acaoOK(), usuarioOK());
    }

    public static Boleta boleta2OK(Tipo tipoBoleta, Usuario usuario) {
        return new Boleta(tipoBoleta, LocalDate.now(), new BigDecimal("15.00"), 4, acao2OK(), usuario);
    }

    public static Boleta boleta3OK(Tipo tipoBoleta) {
        return new Boleta(tipoBoleta, LocalDate.now().plusDays(5), new BigDecimal("5.00"), 3, acaoOK(), usuarioOK());
    }

    public static BoletaAgrupadoPeloTipoEhCodigoNegociacao boletaAgrupadaPeloTipoEhCodigoNegociacaoOK(Tipo tipo, String codigoNegociacaoPapel, Integer quantidadeTotals) {
        return BoletaAgrupadoPeloTipoEhCodigoNegociacao.construir(tipo, codigoNegociacaoPapel, quantidadeTotals);
    }

    public static BoletaAgrupadoPeloCodigoNegociacao boletaAgrupadaPeloCodigoNegociacaoOK(String codigoNegociacaoPapel, Integer quantidadeTotals) {
        return new BoletaAgrupadoPeloCodigoNegociacao(codigoNegociacaoPapel, quantidadeTotals);
    }
}