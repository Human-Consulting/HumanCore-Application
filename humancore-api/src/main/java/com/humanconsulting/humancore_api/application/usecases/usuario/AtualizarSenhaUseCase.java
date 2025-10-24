package com.humanconsulting.humancore_api.application.usecases.usuario;

import com.humanconsulting.humancore_api.application.usecases.usuario.mappers.UsuarioResponseMapper;
import com.humanconsulting.humancore_api.domain.entities.Usuario;
import com.humanconsulting.humancore_api.domain.exception.EntidadeNaoEncontradaException;
import com.humanconsulting.humancore_api.domain.exception.EntidadeSemPermissaoException;
import com.humanconsulting.humancore_api.domain.repositories.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.humanconsulting.humancore_api.web.dtos.atualizar.usuario.UsuarioAtualizarSenhaDto;
import com.humanconsulting.humancore_api.web.dtos.response.usuario.UsuarioResponseDto;

public class AtualizarSenhaUseCase {
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final UsuarioResponseMapper usuarioResponseMapper;

    public AtualizarSenhaUseCase(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder, UsuarioResponseMapper usuarioResponseMapper) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.usuarioResponseMapper = usuarioResponseMapper;
    }

    public UsuarioResponseDto execute(Integer idUsuario, UsuarioAtualizarSenhaDto usuarioAtualizar) {
        if (!usuarioAtualizar.getIdEditor().equals(idUsuario)) {
            throw new EntidadeSemPermissaoException("Apenas o dono da conta pode editá-la");
        }
        Usuario usuarioAtual = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Usuário não encontrado"));
        if (!passwordEncoder.matches(usuarioAtualizar.getSenhaAtual(), usuarioAtual.getSenha())) {
            throw new EntidadeSemPermissaoException("Senha atual incorreta");
        }
        if (passwordEncoder.matches(usuarioAtualizar.getSenhaAtualizada(), usuarioAtual.getSenha())) {
            throw new EntidadeSemPermissaoException("Nova senha deve ser diferente da atual");
        }
        usuarioAtual.setSenha(passwordEncoder.encode(usuarioAtualizar.getSenhaAtualizada()));
        Usuario usuarioAtualizado = usuarioRepository.save(usuarioAtual);
        return usuarioResponseMapper.toResponse(usuarioAtualizado);
    }
}

