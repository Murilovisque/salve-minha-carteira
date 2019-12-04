package com.salveminhacarteira.seguranca;

import java.io.IOException;
import java.util.Collections;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.salveminhacarteira.seguranca.excecoes.TokenException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class ValidadorTokenRequisicaoFilter extends OncePerRequestFilter {

    private static final String BEARER_PREFIX = "Bearer ";
    private static final String AUTHORIZATION_HEADER = "Authorization";

    @Autowired
    private TokenManager tokenManager;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            var tokenHeader = request.getHeader(AUTHORIZATION_HEADER);
            if (tokenHeader == null || !tokenHeader.startsWith(BEARER_PREFIX))
                throw new TokenException();
            var token = tokenManager.decodificarToken(tokenHeader.replace(BEARER_PREFIX, ""));
            request.setAttribute(TokenManager.REQUEST_ATTRIBUTE_NAME, token);
            SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(token.getEmail(), null, Collections.emptyList()));
        } catch (TokenException ex) {
        } finally {
            filterChain.doFilter(request, response);
        }
    }

}