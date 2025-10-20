package com.humanconsulting.humancore_api.application.usecases.usuario;

import com.humanconsulting.humancore_api.application.usecases.usuario.mappers.UsuarioResponseMapper;
import com.humanconsulting.humancore_api.domain.entities.Usuario;
import com.humanconsulting.humancore_api.domain.exception.EntidadeSemRetornoException;
import com.humanconsulting.humancore_api.domain.repositories.UsuarioRepository;
import com.humanconsulting.humancore_api.web.dtos.response.usuario.UsuarioResponseDto;

import java.util.ArrayList;
import java.util.List;

public class ListarUsuariosUseCase {
    private final UsuarioRepository usuarioRepository;
    private final UsuarioResponseMapper usuarioResponseMapper;

    public ListarUsuariosUseCase(UsuarioRepository usuarioRepository, UsuarioResponseMapper usuarioResponseMapper) {
        this.usuarioRepository = usuarioRepository;
        this.usuarioResponseMapper = usuarioResponseMapper;
    }

    public List<UsuarioResponseDto> execute() {
        List<Usuario> all = usuarioRepository.findAll();
        if (all.isEmpty()) throw new EntidadeSemRetornoException("Nenhum usuário registrado");
        List<UsuarioResponseDto> allResponse = new ArrayList<>();
        for (Usuario usuario : all) {
            allResponse.add(usuarioResponseMapper.toResponse(usuario));
        }
        return allResponse;
    }
}

