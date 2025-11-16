package com.humanconsulting.humancore_api.application.usecases.usuario;

import com.humanconsulting.humancore_api.application.usecases.usuario.mappers.UsuarioResponseMapper;
import com.humanconsulting.humancore_api.domain.entities.Usuario;
import com.humanconsulting.humancore_api.domain.exception.EntidadeSemRetornoException;
import com.humanconsulting.humancore_api.domain.repositories.UsuarioRepository;
import com.humanconsulting.humancore_api.domain.utils.PageResult;
import com.humanconsulting.humancore_api.infrastructure.utils.PageResultImpl;
import com.humanconsulting.humancore_api.web.dtos.response.usuario.UsuarioResponseDto;

import java.util.ArrayList;
import java.util.List;

public class ListarUsuariosPorEmpresaFiltradoPorNomeUseCase {
    private final UsuarioRepository usuarioRepository;
    private final UsuarioResponseMapper usuarioResponseMapper;

    public ListarUsuariosPorEmpresaFiltradoPorNomeUseCase(UsuarioRepository usuarioRepository, UsuarioResponseMapper usuarioResponseMapper) {
        this.usuarioRepository = usuarioRepository;
        this.usuarioResponseMapper = usuarioResponseMapper;
    }

    public PageResult<UsuarioResponseDto> execute(Integer idEmpresa, int page, int size, String nome, boolean comConsultores) {
        PageResult<Usuario> usuarios = null;
        if (comConsultores) usuarios = usuarioRepository.findByFkEmpresa_IdEmpresaOrUsuarioPermissaoLikeConsultorAndNomeContainingIgnoreCase(idEmpresa, page, size, nome);
        else usuarios = usuarioRepository.findByFkEmpresa_IdEmpresaAndNomeContainingIgnoreCase(idEmpresa, page, size, nome);

        List<UsuarioResponseDto> allResponse = new ArrayList<>();
        for (Usuario usuario : usuarios.getContent()) {
            allResponse.add(usuarioResponseMapper.toResponse(usuario));
        }
        if (allResponse.isEmpty()) throw new EntidadeSemRetornoException("Nenhum usuário encontrado");

        return new PageResultImpl<>(
                allResponse,
                usuarios.getPageNumber(),
                usuarios.getPageSize(),
                usuarios.getTotalElements(),
                usuarios.getTotalPages()
        );
    }
}

