package com.salveminhacarteira.usuario;

import javax.validation.Validator;

import com.salveminhacarteira.excecoes.ArgumentosInvalidadosException;
import com.salveminhacarteira.excecoes.SalveMinhaCarteiraException;
import com.salveminhacarteira.usuario.excecoes.UsuarioJaCadastradoException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioManager {

    private static final Logger logger = LoggerFactory.getLogger(UsuarioManager.class);

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private Validator validator;

    @Autowired
    private BCryptPasswordEncoder encriptadorDeSenha;

    public void cadastrarUsuario(String nome, String email, String senha) throws SalveMinhaCarteiraException {
        var u = new Usuario(nome, email, senha);
        var erros = validator.validate(u);
        if (!erros.isEmpty())
            throw new ArgumentosInvalidadosException(erros);
        u.encryptPassword(encriptadorDeSenha);
        try {
            usuarioRepository.save(u);
        } catch (DataIntegrityViolationException ex) {
            logger.info("Usuário {} tentou se cadastrar com o email {}, porém já existe", nome, email);
            throw new UsuarioJaCadastradoException();
        }
        logger.info("Usuário {} cadastrado com email {}", nome, email);
    }

}