package com.humanconsulting.humancore_api.domain.security;

public interface TokenResetSenhaService {
    String gerarTokenReset(String email);
    String validarTokenERetornarEmail(String token);
}

