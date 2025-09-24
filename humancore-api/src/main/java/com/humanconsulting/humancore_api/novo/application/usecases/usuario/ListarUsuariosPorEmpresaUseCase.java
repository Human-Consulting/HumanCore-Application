package com.humanconsulting.humancore_api.novo.application.usecases.usuario;

import com.humanconsulting.humancore_api.novo.application.usecases.usuario.mappers.UsuarioResponseMapper;
import com.humanconsulting.humancore_api.novo.domain.entities.Usuario;
import com.humanconsulting.humancore_api.novo.domain.exception.EntidadeSemRetornoException;
import com.humanconsulting.humancore_api.novo.domain.repositories.UsuarioRepository;
import com.humanconsulting.humancore_api.novo.web.dtos.response.usuario.UsuarioResponseDto;

import java.util.ArrayList;
import java.util.List;

public class ListarUsuariosPorEmpresaUseCase {
    private final UsuarioRepository usuarioRepository;
    private final UsuarioResponseMapper usuarioResponseMapper;

    public ListarUsuariosPorEmpresaUseCase(UsuarioRepository usuarioRepository, UsuarioResponseMapper usuarioResponseMapper) {
        this.usuarioRepository = usuarioRepository;
        this.usuarioResponseMapper = usuarioResponseMapper;
    }

    public List<UsuarioResponseDto> execute(Integer idEmpresa) {
        List<Usuario> all = usuarioRepository.findByFkEmpresa(idEmpresa);
        if (all.isEmpty()) throw new EntidadeSemRetornoException("Nenhuma empresa registrada");
        List<UsuarioResponseDto> allResponse = new ArrayList<>();
        for (Usuario usuario : all) {
            allResponse.add(usuarioResponseMapper.toResponse(usuario));
        }
        return allResponse;
    }
}

