package com.humanconsulting.humancore_api.infrastructure.configs.usecases;

import com.humanconsulting.humancore_api.application.usecases.autenticacao.CarregarUsuarioPorEmailUseCase;
import com.humanconsulting.humancore_api.application.usecases.security.LoadUserByUsernameUseCase;
import com.humanconsulting.humancore_api.domain.repositories.UsuarioRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AutenticacaoConfig {
    @Bean
    public CarregarUsuarioPorEmailUseCase carregarUsuarioPorEmailUseCase(UsuarioRepository usuarioRepository) {
        return new CarregarUsuarioPorEmailUseCase(usuarioRepository);
    }
}
