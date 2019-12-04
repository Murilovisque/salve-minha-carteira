package com.salveminhacarteira.usuario;

import static com.salveminhacarteira.usuario.DadosUsuariosTests.usuarioOK;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Arrays;

import javax.validation.Validation;
import javax.validation.Validator;

import com.salveminhacarteira.excecoes.SalveMinhaCarteiraException;
import com.salveminhacarteira.seguranca.TokenManager;
import com.salveminhacarteira.usuario.excecoes.UsuarioJaCadastradoException;
import com.salveminhacarteira.utilitarios.Erros;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class UsuarioManagerTests {

    @Autowired
    private UsuarioManager usuarioManager;

    @MockBean
    private UsuarioRepository usuarioRepository;

    @MockBean
    private BCryptPasswordEncoder encriptadorDeSenha;

    @MockBean
    private AuthenticationManager authenticationManager;

    @MockBean
    private TokenManager tokenManager;

    @Before
    public void setup() {
        when(encriptadorDeSenha.encode(any())).then((i) -> i.getArgument(0));
    }

    @Test
    public void deveCadastrarQuandoUsuarioValido() throws SalveMinhaCarteiraException {
        usuarioManager.cadastrar(usuarioOK().getNome(), usuarioOK().getEmail(), usuarioOK().getSenha());
        verify(encriptadorDeSenha, times(1)).encode(any());
        verify(usuarioRepository, times(1)).save(any(Usuario.class));
    }

    @Test
    public void naoDeveCadastrarUsuarioQuandoNomeOuEmailOuSenhaSaoInvalidos() {
        verificarSeCadastrarUsuarioFalha("", usuarioOK().getEmail(), usuarioOK().getSenha(), MensagensUsuario.NOME_INVALIDO);
        verificarSeCadastrarUsuarioFalha(usuarioOK().getNome(), "", usuarioOK().getSenha(), MensagensUsuario.EMAIL_INVALIDO);
        verificarSeCadastrarUsuarioFalha(usuarioOK().getNome(), "email", usuarioOK().getSenha(), MensagensUsuario.EMAIL_INVALIDO);
        verificarSeCadastrarUsuarioFalha(usuarioOK().getNome(), "email@", usuarioOK().getSenha(), MensagensUsuario.EMAIL_INVALIDO);
        verificarSeCadastrarUsuarioFalha(usuarioOK().getNome(), "email@domain", usuarioOK().getSenha(), MensagensUsuario.EMAIL_INVALIDO);
        verificarSeCadastrarUsuarioFalha(usuarioOK().getNome(), usuarioOK().getEmail(), "", MensagensUsuario.SENHA_INVALIDA);
        verificarSeCadastrarUsuarioFalha("", "", usuarioOK().getSenha(), MensagensUsuario.NOME_INVALIDO, MensagensUsuario.EMAIL_INVALIDO);
        verificarSeCadastrarUsuarioFalha("", "", "", MensagensUsuario.NOME_INVALIDO, MensagensUsuario.EMAIL_INVALIDO, MensagensUsuario.SENHA_INVALIDA);
        verificarSeCadastrarUsuarioFalha(null, usuarioOK().getEmail(), usuarioOK().getSenha(), MensagensUsuario.NOME_INVALIDO);
    }

    @Test(expected = UsuarioJaCadastradoException.class)
    public void deveLançarUsuarioJaCadastradoExceptionQuandoOcorrerDataIntegrityViolationException() throws SalveMinhaCarteiraException {
        var ex = mock(SQLIntegrityConstraintViolationException.class);
        when(ex.getErrorCode()).thenReturn(Erros.MYSQL_DUPLICATE_ENTRY_CODE);        
        when(usuarioRepository.save(any())).thenThrow(new DataIntegrityViolationException("Error constraint", ex));
        usuarioManager.cadastrar(usuarioOK().getNome(), usuarioOK().getEmail(), usuarioOK().getSenha());
    }

    private void verificarSeCadastrarUsuarioFalha(String nome, String email, String senha, String... erros) {
        try {
            usuarioManager.cadastrar(nome, email, senha);
            fail("Era esperada uma exceção, mas não foi lançada");
        } catch(SalveMinhaCarteiraException ex) {
            assertTrue(Arrays.stream(erros).allMatch(e -> ex.getMessage().contains(e)));
        }        
        verify(usuarioRepository, never()).save(any(Usuario.class));
    }

    @TestConfiguration
    static class UsuarioManagerTestsContextConfiguration {  
        @Bean
        public UsuarioManager usuarioManager() {
            return new UsuarioManager();
        }

        @Bean
        public Validator validator() {
            return Validation.buildDefaultValidatorFactory().getValidator();
        }

        @Bean
        public AuthenticationManager authenticationManager() {
            return mock(AuthenticationManager.class);
        }

        @Bean
        public TokenManager tokenManager() {
            return mock(TokenManager.class);
        }
    }
}