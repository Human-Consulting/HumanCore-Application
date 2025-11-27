package com.humanconsulting.humancore_api.application.usecases.usuario.reset;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.humanconsulting.humancore_api.application.exception.ApplicationException;
import com.humanconsulting.humancore_api.application.usecases.usuario.mappers.UsuarioResponseMapper;
import com.humanconsulting.humancore_api.domain.entities.Usuario;
import com.humanconsulting.humancore_api.domain.exception.EntidadeNaoEncontradaException;
import com.humanconsulting.humancore_api.domain.repositories.UsuarioRepository;
import com.humanconsulting.humancore_api.web.dtos.request.ResetSenhaRequest;
import com.humanconsulting.humancore_api.web.dtos.response.usuario.UsuarioResponseDto;
import org.springframework.security.crypto.password.PasswordEncoder;

public class ConfirmarResetSenhaUseCase {
    private static final Logger log = LoggerFactory.getLogger(ConfirmarResetSenhaUseCase.class);
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final UsuarioResponseMapper usuarioResponseMapper;
    private final com.humanconsulting.humancore_api.domain.security.TokenResetSenhaService tokenService;

    public ConfirmarResetSenhaUseCase(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder,
            UsuarioResponseMapper usuarioResponseMapper,
            com.humanconsulting.humancore_api.domain.security.TokenResetSenhaService tokenService) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.usuarioResponseMapper = usuarioResponseMapper;
        this.tokenService = tokenService;
    }

    public UsuarioResponseDto execute(ResetSenhaRequest request) {
        String email;
        try {
            email = tokenService.validarTokenERetornarEmail(request.getToken());
            log.info("[RESET] Token validado para email: {}", email);
        } catch (Exception e) {
            log.error("[RESET] Token inválido ou expirado: {}", request.getToken(), e);
            throw new ApplicationException("Token inválido ou expirado");
        }
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Usuário não encontrado"));
        usuario.setSenha(passwordEncoder.encode(request.getSenha()));
        Usuario atualizado = usuarioRepository.save(usuario);
        return usuarioResponseMapper.toResponse(atualizado);
    }
}
