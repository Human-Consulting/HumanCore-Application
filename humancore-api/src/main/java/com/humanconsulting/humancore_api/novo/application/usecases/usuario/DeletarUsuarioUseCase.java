package com.humanconsulting.humancore_api.novo.application.usecases.usuario;

import com.humanconsulting.humancore_api.novo.domain.entities.Usuario;
import com.humanconsulting.humancore_api.novo.domain.repositories.UsuarioRepository;

import java.util.Optional;

public class DeletarUsuarioUseCase {
    private final UsuarioRepository usuarioRepository;

    public DeletarUsuarioUseCase(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public void execute(Integer id) {
        Optional<Usuario> usuario = usuarioRepository.findById(id);
        if (usuario.isEmpty()) throw new EntidadeNaoEncontradaException("Usuário não encontrado.");
        usuarioRepository.deleteById(id);
    }
}

