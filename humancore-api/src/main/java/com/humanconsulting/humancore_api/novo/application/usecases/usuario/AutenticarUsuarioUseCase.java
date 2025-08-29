package com.humanconsulting.humancore_api.novo.application.usecases.usuario;

import com.humanconsulting.humancore_api.novo.application.usecases.usuario.mappers.UsuarioLoginResponseMapper;
import com.humanconsulting.humancore_api.novo.domain.entities.Usuario;
import com.humanconsulting.humancore_api.novo.domain.repositories.UsuarioRepository;
import com.humanconsulting.humancore_api.novo.infrastructure.configs.GerenciadorTokenJwt;
import com.humanconsulting.humancore_api.novo.web.dtos.response.usuario.LoginResponseDto;
import com.humanconsulting.humancore_api.novo.web.dtos.token.UsuarioTokenMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

public class AutenticarUsuarioUseCase {
    private final UsuarioRepository usuarioRepository;
    private final AuthenticationManager authenticationManager;
    private final GerenciadorTokenJwt gerenciadorTokenJwt;
    private final UsuarioLoginResponseMapper usuarioLoginResponseMapper;

    public AutenticarUsuarioUseCase(
            UsuarioRepository usuarioRepository,
            AuthenticationManager authenticationManager,
            GerenciadorTokenJwt gerenciadorTokenJwt,
            UsuarioLoginResponseMapper usuarioLoginResponseMapper
    ) {
        this.usuarioRepository = usuarioRepository;
        this.authenticationManager = authenticationManager;
        this.gerenciadorTokenJwt = gerenciadorTokenJwt;
        this.usuarioLoginResponseMapper = usuarioLoginResponseMapper;
    }

    public LoginResponseDto execute(Usuario usuario) {
        final UsernamePasswordAuthenticationToken credentials = new UsernamePasswordAuthenticationToken(usuario.getEmail(), usuario.getSenha());
        final Authentication authentication = this.authenticationManager.authenticate(credentials);
        Optional<Usuario> optUsuarioAutenticado = usuarioRepository.findByEmail(usuario.getEmail());
        Usuario usuarioAutenticado = optUsuarioAutenticado.get();
        SecurityContextHolder.getContext().setAuthentication(authentication);
        final String token = gerenciadorTokenJwt.generateToken(authentication);
        String tokenUsuario = UsuarioTokenMapper.of(usuarioAutenticado, token).getToken();
        return usuarioLoginResponseMapper.toLoginResponse(usuarioAutenticado, tokenUsuario);
    }
}

