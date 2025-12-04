package com.humanconsulting.humancore_api.application.usecases.usuario;

import com.humanconsulting.humancore_api.application.usecases.usuario.mappers.UsuarioResponseMapper;
import com.humanconsulting.humancore_api.domain.entities.Usuario;
import com.humanconsulting.humancore_api.domain.repositories.UsuarioRepository;
import com.humanconsulting.humancore_api.infrastructure.configs.GerenciadorTokenJwt;
import com.humanconsulting.humancore_api.web.dtos.response.usuario.LoginResponseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AutenticarUsuarioUseCaseTest {

    private UsuarioRepository usuarioRepository;
    private AuthenticationManager authenticationManager;
    private GerenciadorTokenJwt gerenciadorTokenJwt;
    private UsuarioResponseMapper usuarioResponseMapper;
    private AutenticarUsuarioUseCase useCase;

    @BeforeEach
    void setUp() {
        usuarioRepository = mock(UsuarioRepository.class);
        authenticationManager = mock(AuthenticationManager.class);
        gerenciadorTokenJwt = mock(GerenciadorTokenJwt.class);
        usuarioResponseMapper = mock(UsuarioResponseMapper.class);

        useCase = new AutenticarUsuarioUseCase(
                usuarioRepository,
                authenticationManager,
                gerenciadorTokenJwt,
                usuarioResponseMapper
        );
    }

    @Test
    void deveAutenticarUsuarioComSucesso() {
        // Arrange
        Usuario usuario = new Usuario();
        usuario.setEmail("teste@teste.com");
        usuario.setSenha("123456");

        Authentication authentication = new UsernamePasswordAuthenticationToken(usuario.getEmail(), usuario.getSenha());

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authentication);

        when(usuarioRepository.findByEmail(usuario.getEmail()))
                .thenReturn(Optional.of(usuario));

        when(gerenciadorTokenJwt.generateToken(authentication))
                .thenReturn("jwt-token");

        LoginResponseDto responseDto = new LoginResponseDto();
        responseDto.setNome("jwt-token");
        responseDto.setEmail("teste@teste.com");

        when(usuarioResponseMapper.toLoginResponse(usuario, "jwt-token"))
                .thenReturn(responseDto);

        // Act
        LoginResponseDto result = useCase.execute(usuario);

        // Assert
        assertNotNull(result);
        assertEquals("teste@teste.com", result.getEmail());
        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(usuarioRepository).findByEmail(usuario.getEmail());
        verify(gerenciadorTokenJwt).generateToken(authentication);
        verify(usuarioResponseMapper).toLoginResponse(usuario, "jwt-token");
    }

    @Test
    void deveFalharQuandoCredenciaisInvalidas() {
        // Arrange
        Usuario usuario = new Usuario();
        usuario.setEmail("teste@teste.com");
        usuario.setSenha("senhaErrada");

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(new BadCredentialsException("Credenciais inválidas"));

        // Act & Assert
        assertThrows(BadCredentialsException.class, () -> useCase.execute(usuario));
        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(usuarioRepository, never()).findByEmail(anyString());
    }

    @Test
    void deveFalharQuandoUsuarioNaoEncontrado() {
        // Arrange
        Usuario usuario = new Usuario();
        usuario.setEmail("naoexiste@teste.com");
        usuario.setSenha("123456");

        Authentication authentication = new UsernamePasswordAuthenticationToken(usuario.getEmail(), usuario.getSenha());
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authentication);

        when(usuarioRepository.findByEmail(usuario.getEmail()))
                .thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(java.util.NoSuchElementException.class, () -> useCase.execute(usuario));
        verify(usuarioRepository).findByEmail(usuario.getEmail());
    }
}
