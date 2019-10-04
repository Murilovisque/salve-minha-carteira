package com.salveminhacarteira.usuario;

import java.util.Collections;

import javax.validation.Validator;

import com.salveminhacarteira.excecoes.ArgumentosInvalidadosException;
import com.salveminhacarteira.excecoes.SalveMinhaCarteiraException;
import com.salveminhacarteira.seguranca.TokenManager;
import com.salveminhacarteira.usuario.excecoes.UsuarioJaCadastradoException;
import com.salveminhacarteira.validadores.Validador;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioManager implements UserDetailsService {

    private static final Logger logger = LoggerFactory.getLogger(UsuarioManager.class);

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private Validator validator;

    @Autowired
    private BCryptPasswordEncoder encriptadorDeSenha;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenManager tokenManager;

    public void cadastrarUsuario(String nome, String email, String senha) throws SalveMinhaCarteiraException {
        var u = new Usuario(nome, email, senha);
        var erros = validator.validate(u);
        if (!erros.isEmpty())
            throw new ArgumentosInvalidadosException(erros);
        u.encriptarSenha(encriptadorDeSenha);
        try {
            usuarioRepository.save(u);
        } catch (DataIntegrityViolationException ex) {
            logger.info("Usuário {} tentou se cadastrar com o email {}, porém já existe", nome, email);
            throw new UsuarioJaCadastradoException();
        }
        logger.info("Usuário {} cadastrado com email {}", nome, email);
    }

    public String autenticarEhObterToken(String email, String senha) throws SalveMinhaCarteiraException {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, senha));
        logger.info("Usuário {} autenticado", email);
        var u = usuarioRepository.obterUsuarioPeloEmail(email);
        return tokenManager.gerarToken(u.get()).getHash();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {        
        if (Validador.formatoEmailEhValido(username)) {
            var u = usuarioRepository.obterUsuarioPeloEmail(username);
            if (u.isPresent()) {
                var uAuth = u.get();                
                return new User(uAuth.getEmail(), uAuth.getSenha(), Collections.emptyList());
            }
        }
        logger.info("Falha ao tentar autenticar o usuário " + username);
        throw new UsernameNotFoundException(username);
    }

}