package com.humanconsulting.humancore_api.infrastructure.configs.usecases;

import com.humanconsulting.humancore_api.application.usecases.usuario.reset.ConfirmarResetSenhaUseCase;
import com.humanconsulting.humancore_api.application.usecases.usuario.reset.SolicitarResetSenhaUseCase;
import com.humanconsulting.humancore_api.domain.notifiers.ResetSenhaNotifier;
import com.humanconsulting.humancore_api.domain.repositories.UsuarioRepository;
import com.humanconsulting.humancore_api.infrastructure.configs.RabbitTemplateConfiguration;
import com.humanconsulting.humancore_api.infrastructure.notifiers.RabbitResetSenhaNotifier;
import com.humanconsulting.humancore_api.infrastructure.security.JwtTokenResetSenhaService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class ResetSenhaConfig {

    @Bean
    public SolicitarResetSenhaUseCase solicitarResetSenhaUseCase(UsuarioRepository usuarioRepository,
            ResetSenhaNotifier resetSenhaNotifier, JwtTokenResetSenhaService tokenService) {
        return new SolicitarResetSenhaUseCase(usuarioRepository, resetSenhaNotifier, tokenService);
    }

    @Bean
    public ConfirmarResetSenhaUseCase confirmarResetSenhaUseCase(UsuarioRepository usuarioRepository,
            PasswordEncoder passwordEncoder,
            com.humanconsulting.humancore_api.application.usecases.usuario.mappers.UsuarioResponseMapper usuarioResponseMapper,
            JwtTokenResetSenhaService tokenService) {
        return new ConfirmarResetSenhaUseCase(usuarioRepository, passwordEncoder, usuarioResponseMapper, tokenService);
    }

    @Bean
    public ResetSenhaNotifier resetSenhaNotifier(RabbitTemplateConfiguration rabbitTemplateConfiguration) {
        return new RabbitResetSenhaNotifier(rabbitTemplateConfiguration);
    }

    @Bean
    public JwtTokenResetSenhaService jwtTokenResetSenhaService(
            @Value("${jwt.secret}") String secret,
            @Value("${jwt.reset.validity}") long resetValidity) {
        return new JwtTokenResetSenhaService(secret, resetValidity);
    }
}
