package com.humanconsulting.humancore_api.application.usecases.autenticacao;

import com.humanconsulting.humancore_api.domain.entities.Usuario;
import com.humanconsulting.humancore_api.domain.repositories.UsuarioRepository;
import com.humanconsulting.humancore_api.web.dtos.token.UsuarioDetalhesDto;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

public class CarregarUsuarioPorEmailUseCase {
    private final UsuarioRepository usuarioRepository;

    public CarregarUsuarioPorEmailUseCase(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public UsuarioDetalhesDto execute(String email) {
        Optional<Usuario> usuarioOptional = usuarioRepository.findByEmail(email);
        if (usuarioOptional == null || usuarioOptional.isEmpty()) {
            throw new UsernameNotFoundException(String.format("Usuário: %s não encontrado", email));
        }
        return new UsuarioDetalhesDto(usuarioOptional.get());
    }
}

