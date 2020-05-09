package com.salveminhacarteira.boleta;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import com.salveminhacarteira.boleta.extensoes.BoletaAgrupadoPeloTipoEhCodigoNegociacao;
import com.salveminhacarteira.boleta.extensoes.BoletaAgrupadoPeloTipoEhCodigoNegociacao2;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

interface BoletaRepository extends CrudRepository<Boleta, Long> {

    @Modifying
    @Query(value = "insert into boleta (tipo, data, valor, quantidade, id_acao, id_usuario) values (:tipo, :data, :valor, :quantidade, :id_acao, :id_usuario)", nativeQuery = true)
    @Transactional()
    void salvar(@Param("tipo") String tipo, @Param("data") LocalDate data, @Param("valor") BigDecimal valor, @Param("quantidade") Integer quantidade,
        @Param("id_acao") Long idAcao, @Param("id_usuario") Long idUsuario);

    @Query(value = "select a.id, a.cod_negociacao, b.tipo, sum(b.quantidade) as quantidadetotal from boleta b " 
        + "inner join acao a on b.id_acao = a.id "
        + "where tipo = :tipo and b.id_usuario = :id_usuario group by a.id, a.cod_negociacao, b.tipo", nativeQuery = true)
    List<BoletaAgrupadoPeloTipoEhCodigoNegociacao> obterBoletasAgrupadasPeloTipoEhCodigoNegociacaoBuscandoPeloTipo(@Param("id_usuario") Long idUsuario, @Param("tipo") String tipo);

    @Query(value = "select a.id, a.cod_negociacao, b.tipo, sum(b.quantidade) as quantidadetotal from boleta b "
        + "inner join acao a on b.id_acao = a.id "
        + "where b.id_usuario = :id_usuario group by a.id, a.cod_negociacao, b.tipo", nativeQuery = true)
    List<BoletaAgrupadoPeloTipoEhCodigoNegociacao> obterBoletasAgrupadasPeloTipoEhCodigoNegociacao(@Param("id_usuario") Long idUsuario);

    @Query(value = "select a.id, a.cod_negociacao, b.tipo, sum(b.valor * b.quantidade) as valortotal, sum(b.quantidade) as quantidadetotal from boleta b "
        + "inner join acao a on b.id_acao = a.id "
        + "where b.id_usuario = :id_usuario group by a.id, a.cod_negociacao, b.tipo", nativeQuery = true)
    List<BoletaAgrupadoPeloTipoEhCodigoNegociacao2> obterBoletasAgrupadasPeloTipoEhCodigoNegociacao2(@Param("id_usuario") Long idUsuario);

    @Query(value = "select * from boleta where tipo = :tipo and data = :data and id_usuario = :id_usuario and id_acao = :id_acao and valor = :valor", nativeQuery = true)
    Optional<Boleta> obterBoletaPeloTipoDataAcaoEhValor(@Param("id_usuario") Long idUsuario, @Param("tipo") String tipo, @Param("data") LocalDate data, @Param("id_acao") Long idAcao, @Param("valor") BigDecimal valor);

    @Query(value = "select (select coalesce(sum(quantidade), 0) from boleta where tipo = 'COMPRA' and id_usuario = :id_usuario and id_acao = :id_acao) "
        + " - (select coalesce(sum(quantidade), 0) from boleta where tipo = 'VENDA' and id_usuario = :id_usuario and id_acao = :id_acao) from dual",    
        nativeQuery = true)
    Optional<Integer> obterQuantidadeTotalComSaldoPositivoDaAcao(@Param("id_usuario") Long idUsuario, @Param("id_acao") Long idAcao); 
}