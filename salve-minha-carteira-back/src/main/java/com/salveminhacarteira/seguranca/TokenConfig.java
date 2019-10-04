package com.salveminhacarteira.seguranca;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@ConfigurationProperties("token")
@Data
class TokenConfig {

    private String jwtSegredo;

    private long diasDeVida;

    private String jwtIssuer;
}