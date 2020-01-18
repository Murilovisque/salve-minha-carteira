package com.salveminhacarteira.acao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

interface AcaoRepository extends CrudRepository<Acao, Long> {

    @Query("from Acao where codigoNegociacaoPapel = :codigoNegociacaoPapel")
    Optional<Acao> obterAcaoPeloCodigoNegociacaoPapel(@Param("codigoNegociacaoPapel") String codigoNegociacaoPapel);

    @Query("select a from Acao a inner join fetch a.empresa e where a.codigoNegociacaoPapel like concat(:codigoNegociacaoPapel, '%') or e.nome like concat(:nomeEmpresa,'%')")
    List<Acao> procurarAcoesPeloCodigoNegociacaoOuNomeEmpresa(@Param("codigoNegociacaoPapel") String codigoNegociacaoPapel, @Param("nomeEmpresa") String nomeEmpresa);
}