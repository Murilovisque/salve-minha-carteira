package com.salveminhacarteira.seguranca;

import static com.salveminhacarteira.seguranca.DadosTokensTests.*;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.JWTVerifier.BaseVerification;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Clock;
import com.salveminhacarteira.excecoes.SalveMinhaCarteiraException;
import com.salveminhacarteira.seguranca.excecoes.TokenException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class TokenManagerTests {

    @Autowired
    private TokenManager tokenManager;

    @MockBean
    private TokenConfig tokenConfig;

    @MockBean
    private HttpServletRequest httpRequest;

    private CustomClock customClock;

    @Before
    public void configurar() {
        customClock = new CustomClock();
        when(tokenConfig.getJwtIssuer()).thenReturn(JWT_ISSUER);
        when(tokenConfig.getJwtSegredo()).thenReturn(JWT_SECRET);
        when(tokenConfig.getDiasDeVida()).thenReturn((long) TOKEN_DIAS_DE_VIDA);
        BaseVerification verification = (BaseVerification) JWT.require(Algorithm.HMAC256(tokenConfig.getJwtSegredo()));
        verification.withIssuer(tokenConfig.getJwtIssuer());
        ((TokenManagerForTests) tokenManager).setVerificador(verification.build(customClock));
        tokenManager.configurar();
    }

    @Test(expected = TokenException.class)
    public void tokenDeveSerInvalidoSeExpirado() throws SalveMinhaCarteiraException {        
        var token = tokenManager.gerarToken(usuarioOK());
        customClock.adicionarDias(TOKEN_DIAS_DE_VIDA + 1);
        tokenManager.decodificarToken(token.getHash());
    }

    @Test
    public void tokenDeveSerValidoSeDentroDoTempoDeVida() throws SalveMinhaCarteiraException {        
        var token = tokenManager.gerarToken(usuarioOK());
        customClock.adicionarDias(1);
        token = tokenManager.decodificarToken(token.getHash());
        assertEquals(USUARIO_EMAIL, token.getEmail());
        assertEquals(USUARIO_ID, token.getIdUsuario());
    }    

    @Test(expected = TokenException.class)
    public void tokenDeveSerInvalidoSeTokenInvalido() throws SalveMinhaCarteiraException {        
        tokenManager.decodificarToken("bananaIspliti");
    } 

    static class TokenManagerForTests extends TokenManager {
        private JWTVerifier verificador;

        void setVerificador(JWTVerifier verificador) {
            this.verificador = verificador;
        }

        @Override
        protected void configurar() {
            verificadorDeTokens = verificador;
        }
    }

    static class CustomClock implements Clock {
        private Date data = new Date();

		@Override
		public Date getToday() {
			return data;
        }
        
        public void adicionarDias(int dias) {
            var d = LocalDateTime.ofInstant(data.toInstant(), ZoneId.systemDefault());
            data = Date.from(d.plusDays(dias).atZone(ZoneId.systemDefault()).toInstant());
        }
    }

    @TestConfiguration
    static class TokenManagerTestsContextConfiguration {  
        @Bean
        public TokenManager tokenManager() {
            return new TokenManagerForTests();
        }
    }
}