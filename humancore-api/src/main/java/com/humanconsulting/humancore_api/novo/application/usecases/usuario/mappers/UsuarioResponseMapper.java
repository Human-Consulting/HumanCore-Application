package com.humanconsulting.humancore_api.novo.application.usecases.usuario.mappers;

import com.humanconsulting.humancore_api.novo.domain.entities.Usuario;
import com.humanconsulting.humancore_api.novo.domain.repositories.UsuarioRepository;
import com.humanconsulting.humancore_api.novo.web.dtos.response.usuario.UsuarioResponseDto;
import com.humanconsulting.humancore_api.novo.web.mappers.UsuarioMapper;

public class UsuarioResponseMapper {
    private final UsuarioRepository usuarioRepository;

    public UsuarioResponseMapper(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public UsuarioResponseDto toResponse(Usuario usuario) {
        Integer qtdTarefas = usuarioRepository.countTarefasByUsuario(usuario.getIdUsuario());
        Boolean comImpedimento = usuarioRepository.hasTarefasComImpedimento(usuario.getIdUsuario());
        return UsuarioMapper.toUsuarioDto(usuario, qtdTarefas, comImpedimento);
    }
}

