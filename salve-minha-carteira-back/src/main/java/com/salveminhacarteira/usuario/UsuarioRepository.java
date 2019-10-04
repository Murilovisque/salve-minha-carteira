package com.salveminhacarteira.usuario;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

interface UsuarioRepository extends CrudRepository<Usuario, Long> {

    @Query("from Usuario where email = :email")
    Optional<Usuario> obterUsuarioPeloEmail(@Param("email") String email);
}