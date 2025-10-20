package com.humanconsulting.humancore_api.application.usecases.usuario;

import com.humanconsulting.humancore_api.domain.entities.Usuario;
import com.humanconsulting.humancore_api.domain.exception.EntidadeNaoEncontradaException;
import com.humanconsulting.humancore_api.domain.repositories.UsuarioRepository;
import com.humanconsulting.humancore_api.web.dtos.request.UsuarioPermissaoDto;

import java.util.Optional;

public class DeletarUsuarioUseCase {
    private final UsuarioRepository usuarioRepository;

    public DeletarUsuarioUseCase(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public void execute(Integer id, UsuarioPermissaoDto usuarioPermissaoDto) {
        Optional<Usuario> usuario = usuarioRepository.findById(id);
        if (usuario.isEmpty()) throw new EntidadeNaoEncontradaException("Usuário não encontrado.");
        usuarioRepository.deleteById(id);
    }
}

