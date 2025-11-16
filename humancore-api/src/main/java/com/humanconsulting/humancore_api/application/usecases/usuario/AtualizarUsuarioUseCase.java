package com.humanconsulting.humancore_api.application.usecases.usuario;

import com.humanconsulting.humancore_api.application.enums.PermissaoEnum;
import com.humanconsulting.humancore_api.application.usecases.usuario.mappers.UsuarioResponseMapper;
import com.humanconsulting.humancore_api.domain.entities.Empresa;
import com.humanconsulting.humancore_api.domain.entities.Usuario;
import com.humanconsulting.humancore_api.domain.exception.EntidadeNaoEncontradaException;
import com.humanconsulting.humancore_api.domain.exception.EntidadeSemPermissaoException;
import com.humanconsulting.humancore_api.domain.repositories.UsuarioRepository;
import com.humanconsulting.humancore_api.web.dtos.atualizar.usuario.UsuarioAtualizarDto;
import com.humanconsulting.humancore_api.web.dtos.response.usuario.UsuarioResponseDto;
import com.humanconsulting.humancore_api.web.mappers.UsuarioMapper;

import java.util.Optional;

public class AtualizarUsuarioUseCase {
    private final UsuarioRepository usuarioRepository;
    private final UsuarioResponseMapper usuarioResponseMapper;

    public AtualizarUsuarioUseCase(UsuarioRepository usuarioRepository, UsuarioResponseMapper usuarioResponseMapper) {
        this.usuarioRepository = usuarioRepository;
        this.usuarioResponseMapper = usuarioResponseMapper;
    }

    public UsuarioResponseDto execute(Integer idUsuario, UsuarioAtualizarDto usuarioAtualizar) {
        Optional<Usuario> optUsuarioAlvo = usuarioRepository.findById(idUsuario);
        Optional<Usuario> optUsuarioEditor = usuarioRepository.findById(usuarioAtualizar.getIdEditor());
        if (optUsuarioAlvo.isEmpty() || optUsuarioEditor.isEmpty())
            throw new EntidadeNaoEncontradaException("Usuário não encontrado.");
        Usuario usuarioAlvo = optUsuarioAlvo.get();
        Usuario usuarioEditor = optUsuarioEditor.get();
        PermissaoEnum permissaoEditor;
        PermissaoEnum permissaoAlvo;
        try {
            permissaoEditor = PermissaoEnum.valueOf(usuarioAtualizar.getPermissaoEditor());
            permissaoAlvo = PermissaoEnum.valueOf(usuarioAlvo.getPermissao());
        } catch (IllegalArgumentException e) {
            throw new EntidadeSemPermissaoException("Permissão inválida informada.");
        }
        if (usuarioEditor.getIdUsuario().equals(usuarioAlvo.getIdUsuario())) {
            if (!permissaoEditor.temPermissao("MODIFICAR_PROPRIO")) {
                throw new EntidadeSemPermissaoException("Você não tem permissão para editar suas próprias informações.");
            }
        } else {
            String chavePermissao = "MODIFICAR_" + permissaoAlvo.name();
            if (!permissaoEditor.temPermissao(chavePermissao)) {
                throw new EntidadeSemPermissaoException("Você não tem permissão para editar este tipo de usuário.");
            }
        }
        if (usuarioEditor.getIdUsuario().equals(usuarioAlvo.getIdUsuario()) &&
                !usuarioAtualizar.getPermissao().equals(usuarioAlvo.getPermissao())) {
            throw new EntidadeSemPermissaoException("Você não pode alterar sua própria permissão.");
        }
        Empresa empresa = usuarioAlvo.getEmpresa();
        Usuario usuarioAtualizado = usuarioRepository.save(UsuarioMapper.toEntity(usuarioAtualizar, idUsuario, empresa, usuarioAlvo.getSenha()));
        return usuarioResponseMapper.toResponse(usuarioAtualizado);
    }
}

