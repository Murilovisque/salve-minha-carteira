package com.salveminhacarteira.acao;

import java.util.List;

import com.salveminhacarteira.excecoes.SalveMinhaCarteiraException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/acoes")
class AcaoResources {

    @Autowired
    private AcaoManager acaoManager;

    @GetMapping("/procurar")
    @ResponseStatus(code = HttpStatus.OK)
    public ResponseEntity<List<Acao>> procurarAcoesPeloCodigoNegociacaoOuNomeEmpresa(@RequestParam("argumento-pesquisa") String argumentoPesquisa)
            throws SalveMinhaCarteiraException {
        return ResponseEntity.ok(acaoManager.procurarAcoesPeloCodigoNegociacaoOuNomeEmpresa(argumentoPesquisa));
    }
}