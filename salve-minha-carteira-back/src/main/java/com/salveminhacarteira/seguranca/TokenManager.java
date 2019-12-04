package com.salveminhacarteira.seguranca;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.salveminhacarteira.excecoes.SalveMinhaCarteiraException;
import com.salveminhacarteira.seguranca.excecoes.TokenException;
import com.salveminhacarteira.usuario.Usuario;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TokenManager {

    private static final Logger logger = LoggerFactory.getLogger(TokenManager.class);
    private static final String EMAIL_CLAIM = "email";
    static final String REQUEST_ATTRIBUTE_NAME = "token";

    @Autowired
    private TokenConfig tokenConfig;

    @Autowired
    private HttpServletRequest httpRequest;

    protected JWTVerifier verificadorDeTokens;

    @PostConstruct
    protected void configurar() {
        verificadorDeTokens = JWT.require(algoritmo())
            .withIssuer(tokenConfig.getJwtIssuer())
            .build();
    }

    public Token gerarToken(Usuario usuario) throws SalveMinhaCarteiraException {
        try {
            logger.info("Gerando um token para o identificador do usuário: " + usuario.getEmail());
            String hash = JWT.create()
                .withIssuer(tokenConfig.getJwtIssuer())
                .withSubject(usuario.getId().toString())
                .withExpiresAt(dataDeExpiracao())
                .withClaim(EMAIL_CLAIM, usuario.getEmail())
                .sign(algoritmo());
            return new Token(hash, usuario.getId(), usuario.getEmail());
        } catch (JWTCreationException e) {
            logger.error("Erro ao gerar um token JWT", e);
            throw new SalveMinhaCarteiraException();
        }
    }

    public Token obterTokenDaRequisicao() {
        return (Token) httpRequest.getAttribute(REQUEST_ATTRIBUTE_NAME);
    }

    Token decodificarToken(String token) throws TokenException {
        try {
            var tokenDecodificado = verificadorDeTokens.verify(token);
            return new Token(token, Long.valueOf(tokenDecodificado.getSubject()), tokenDecodificado.getClaim(EMAIL_CLAIM).asString());
        } catch (JWTVerificationException exception){
            throw new TokenException();
        }
    }

    private Algorithm algoritmo() {
        return Algorithm.HMAC256(tokenConfig.getJwtSegredo());
    }

    private Date dataDeExpiracao() {
        return Date.from(LocalDateTime.now().plusDays(tokenConfig.getDiasDeVida()).atZone(ZoneId.systemDefault()).toInstant());
    }
}