package com.humanconsulting.humancore_api.application.usecases.usuario;

import com.humanconsulting.humancore_api.application.usecases.usuario.mappers.UsuarioLoginResponseMapper;
import com.humanconsulting.humancore_api.domain.entities.Usuario;
import com.humanconsulting.humancore_api.domain.exception.EntidadeNaoEncontradaException;
import com.humanconsulting.humancore_api.domain.repositories.UsuarioRepository;
import com.humanconsulting.humancore_api.web.dtos.response.usuario.LoginResponseDto;

import java.util.Optional;

public class BuscarUsuarioPorIdUseCase {
    private final UsuarioRepository usuarioRepository;
    private final UsuarioLoginResponseMapper usuarioLoginResponseMapper;

    public BuscarUsuarioPorIdUseCase(UsuarioRepository usuarioRepository, UsuarioLoginResponseMapper usuarioLoginResponseMapper) {
        this.usuarioRepository = usuarioRepository;
        this.usuarioLoginResponseMapper = usuarioLoginResponseMapper;
    }

    public LoginResponseDto execute(Integer id) {
        Optional<Usuario> optUsuario = usuarioRepository.findById(id);
        if (optUsuario.isEmpty()) throw new EntidadeNaoEncontradaException("Usuário não encontrado.");
        return usuarioLoginResponseMapper.toLoginResponse(optUsuario.get(), null);
    }
}

