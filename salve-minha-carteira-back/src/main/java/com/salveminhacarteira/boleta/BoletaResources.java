package com.salveminhacarteira.boleta;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import com.salveminhacarteira.boleta.Boleta.Tipo;
import com.salveminhacarteira.boleta.extensoes.BoletaAgrupadoPeloCodigoNegociacao;
import com.salveminhacarteira.excecoes.SalveMinhaCarteiraException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;
import org.springframework.format.annotation.NumberFormat.Style;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/boletas")
class BoletaResources {
    
    @Autowired
    private BoletaManager boletaManager;
    
    @PutMapping
    @ResponseStatus(code = HttpStatus.OK)
    public void cadastrar(@RequestParam("tipo") Tipo tipo, @RequestParam("data") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate data,
            @RequestParam("valor") @NumberFormat(style = Style.CURRENCY) Double valor, @RequestParam("quantidade") Integer quantidade,
            @RequestParam("id-acao") Long idAcao) throws SalveMinhaCarteiraException  {
        boletaManager.cadastrarOuAtualizar(tipo, data, BigDecimal.valueOf(valor), quantidade, idAcao);
    }

    @GetMapping
    @ResponseStatus(code = HttpStatus.OK)
    public ResponseEntity<List<BoletaAgrupadoPeloCodigoNegociacao>> obterBoletasAgrupadasPeloCodigoNegociacaoPositivas() {
        return ResponseEntity.ok(boletaManager.obterBoletasAgrupadasPeloCodigoNegociacaoComSaldoPositivo());
    }
}