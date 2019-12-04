package com.salveminhacarteira.acao;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

interface AcaoRepository extends CrudRepository<Acao, Long> {

    @Query("from Acao where codigoNegociacaoPapel = :codigoNegociacaoPapel")
    Optional<Acao> obterAcaoPeloCodigoNegociacaoPapel(@Param("codigoNegociacaoPapel") String codigoNegociacaoPapel);
}