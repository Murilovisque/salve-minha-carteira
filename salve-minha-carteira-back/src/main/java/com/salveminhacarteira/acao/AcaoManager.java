package com.salveminhacarteira.acao;

import java.util.Optional;

import com.salveminhacarteira.excecoes.ArgumentosInvalidadosException;
import com.salveminhacarteira.excecoes.SalveMinhaCarteiraException;
import com.salveminhacarteira.utilitarios.Validador;

import org.springframework.beans.factory.annotation.Autowired;

import static com.salveminhacarteira.acao.MensagensAcao.CODIGO_NEGOCIACAO_PAPEL_INVALIDO;

public class AcaoManager {

    @Autowired
    private AcaoRepository acaoRepository;

    public Optional<Acao> obterAcaoPeloCodigoNegociacaoPapel(String codigoNegociacaoPapel) throws SalveMinhaCarteiraException {
        if (!Validador.formatoCodigoNegociacaoPapelEhValido(codigoNegociacaoPapel))
            throw new ArgumentosInvalidadosException(CODIGO_NEGOCIACAO_PAPEL_INVALIDO);
        return acaoRepository.obterAcaoPeloCodigoNegociacaoPapel(codigoNegociacaoPapel);
    }
}