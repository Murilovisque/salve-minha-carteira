package com.salveminhacarteira.usuario;

import org.springframework.data.repository.CrudRepository;

interface UsuarioRepository extends CrudRepository<Usuario, Long> {
}