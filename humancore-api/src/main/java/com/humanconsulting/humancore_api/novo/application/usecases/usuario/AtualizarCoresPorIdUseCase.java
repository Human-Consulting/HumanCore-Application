package com.humanconsulting.humancore_api.novo.application.usecases.usuario;

import com.humanconsulting.humancore_api.novo.domain.entities.Usuario;
import com.humanconsulting.humancore_api.novo.domain.repositories.UsuarioRepository;
import com.humanconsulting.humancore_api.novo.web.dtos.atualizar.usuario.UsuarioAtualizarCoresDto;

public class AtualizarCoresPorIdUseCase {
    private final UsuarioRepository usuarioRepository;

    public AtualizarCoresPorIdUseCase(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public Boolean execute(Integer idUsuario, UsuarioAtualizarCoresDto usuarioAtualizarCoresDto) {
        Usuario usuarioAtual = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Usuário não encontrado"));
        usuarioAtual.setCores(usuarioAtualizarCoresDto.getCores());
        usuarioRepository.save(usuarioAtual);
        return true;
    }
}

