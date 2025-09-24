package com.humanconsulting.humancore_api.novo.application.usecases.usuario;

import com.humanconsulting.humancore_api.novo.domain.entities.Usuario;
import com.humanconsulting.humancore_api.novo.domain.exception.EntidadeNaoEncontradaException;
import com.humanconsulting.humancore_api.novo.domain.repositories.UsuarioRepository;

import java.util.Optional;

public class BuscarUsuarioPorEmailUseCase {
    private final UsuarioRepository usuarioRepository;

    public BuscarUsuarioPorEmailUseCase(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public Integer execute(String email) {
        Optional<Usuario> optUsuario = usuarioRepository.findByEmail(email);
        if (optUsuario.isEmpty()) throw new EntidadeNaoEncontradaException("Usuário não encontrado.");
        return optUsuario.get().getIdUsuario();
    }
}

