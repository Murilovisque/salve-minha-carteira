package com.salveminhacarteira.boleta;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.salveminhacarteira.boleta.Boleta.Tipo;
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
}