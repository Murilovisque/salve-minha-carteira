package com.salveminhacarteira.boleta;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

interface BoletaRepository extends CrudRepository<Boleta, Long> {

    @Modifying
    @Query(value = "insert into boleta (tipo, data, valor, quantidade, id_acao, id_usuario) values (:tipo, :data, :valor, :quantidade, :id_acao, :id_usuario)", nativeQuery = true)
    void salvar(@Param("tipo") String tipo, @Param("data") LocalDate data, @Param("valor") BigDecimal valor, @Param("quantidade") Integer quantidade,
        @Param("id_acao") Long idAcao, @Param("id_usuario") Long idUsuario);

}