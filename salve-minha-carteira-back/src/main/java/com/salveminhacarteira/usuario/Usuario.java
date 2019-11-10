package com.salveminhacarteira.usuario;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import com.salveminhacarteira.utilitarios.Validador;

import org.springframework.security.crypto.password.PasswordEncoder;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Entity
@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Usuario {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;
    @NotBlank(message = MensagensUsuario.NOME_INVALIDO)
    private String nome;
    @Pattern(regexp = Validador.EMAIL_REGEX_PATTERN, message = MensagensUsuario.EMAIL_INVALIDO)
    @Column(unique = true)
    private String email;
    @NotBlank(message = MensagensUsuario.SENHA_INVALIDA)
    @Getter(value = AccessLevel.PACKAGE)
    private String senha;

    Usuario(String nome, String email, String senha) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
    }

    Usuario() {}

    void encriptarSenha(PasswordEncoder encriptadorDeSenha) {
        this.senha = encriptadorDeSenha.encode(senha);
    }
}