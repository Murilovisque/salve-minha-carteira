package com.salveminhacarteira.acao;

import java.util.List;
import java.util.Optional;

import com.salveminhacarteira.excecoes.ArgumentosInvalidadosException;
import com.salveminhacarteira.excecoes.SalveMinhaCarteiraException;
import com.salveminhacarteira.utilitarios.Validador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.salveminhacarteira.acao.MensagensAcao.CODIGO_NEGOCIACAO_PAPEL_INVALIDO;
import static com.salveminhacarteira.acao.MensagensAcao.ARGUMENTO_PESQUISA_ACOES;

@Service
public class AcaoManager {

    @Autowired
    private AcaoRepository acaoRepository;

    public Optional<Acao> obterAcaoPeloCodigoNegociacaoPapel(String codigoNegociacaoPapel) throws SalveMinhaCarteiraException {
        if (!Validador.CODIGO_NEGOCIACAO_PAPEL_REGEX.matcher(codigoNegociacaoPapel).matches())
            throw new ArgumentosInvalidadosException(CODIGO_NEGOCIACAO_PAPEL_INVALIDO);
        return acaoRepository.obterAcaoPeloCodigoNegociacaoPapel(codigoNegociacaoPapel);
    }

    public List<Acao> procurarAcoesPeloCodigoNegociacaoOuNomeEmpresa(String argumentoPesquisa) throws SalveMinhaCarteiraException {
        if (!Validador.PESQUISA_ACAO_EMPRESA_REGEX.matcher(argumentoPesquisa).matches())
            throw new ArgumentosInvalidadosException(ARGUMENTO_PESQUISA_ACOES);
        argumentoPesquisa = argumentoPesquisa.toUpperCase();
        return acaoRepository.procurarAcoesPeloCodigoNegociacaoOuNomeEmpresa(argumentoPesquisa, argumentoPesquisa);
    }
}