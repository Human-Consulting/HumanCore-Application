package com.humanconsulting.humancore_api.infrastructure.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.humanconsulting.humancore_api.application.exception.ApplicationException;
import com.humanconsulting.humancore_api.domain.security.TokenResetSenhaService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

import org.springframework.stereotype.Component;

@Component
public class JwtTokenResetSenhaService implements TokenResetSenhaService {
    private static final Logger log = LoggerFactory.getLogger(JwtTokenResetSenhaService.class);

    private final String secret;
    private final long resetValidity;

    public JwtTokenResetSenhaService(
            @org.springframework.beans.factory.annotation.Value("${jwt.secret}") String secret,
            @org.springframework.beans.factory.annotation.Value("${jwt.reset.validity}") long resetValidity) {
        this.secret = secret;
        this.resetValidity = resetValidity;
    }

    @Override
    public String gerarTokenReset(String email) {
        long now = System.currentTimeMillis();
        Date issuedAt = new Date(now);
        Date expiration = new Date(now + resetValidity);
        String token = Jwts.builder()
                .setSubject(email)
                .setIssuedAt(issuedAt)
                .setExpiration(expiration)
                .signWith(parseSecret())
                .compact();
        log.info("[JWT] Token reset gerado para {} expira em {}", email, expiration);
        return token;
    }

    @Override
    public String validarTokenERetornarEmail(String token) {
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(parseSecret())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            log.info("[JWT] Token validado com sucesso para {} expira em {}", claims.getSubject(),
                    claims.getExpiration());
            return claims.getSubject();
        } catch (Exception e) {
            log.error("[JWT] Token inválido ou expirado: {}", token, e);
            throw new ApplicationException("Token inválido ou expirado");
        }
    }

    private SecretKey parseSecret() {
        return Keys.hmacShaKeyFor(this.secret.getBytes(StandardCharsets.UTF_8));
    }
}
