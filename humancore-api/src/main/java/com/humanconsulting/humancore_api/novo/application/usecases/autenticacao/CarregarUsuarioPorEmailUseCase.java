package com.humanconsulting.humancore_api.novo.application.usecases.autenticacao;

import com.humanconsulting.humancore_api.novo.domain.entities.Usuario;
import com.humanconsulting.humancore_api.novo.domain.repositories.UsuarioRepository;
import com.humanconsulting.humancore_api.novo.web.dtos.token.UsuarioDetalhesDto;
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

