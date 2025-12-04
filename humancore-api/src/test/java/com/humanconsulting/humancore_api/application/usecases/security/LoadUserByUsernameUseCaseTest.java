package com.humanconsulting.humancore_api.application.usecases.security;

import com.humanconsulting.humancore_api.domain.entities.Usuario;
import com.humanconsulting.humancore_api.domain.repositories.UsuarioRepository;
import com.humanconsulting.humancore_api.web.dtos.token.UsuarioDetalhesDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class LoadUserByUsernameUseCaseTest {

    private UsuarioRepository usuarioRepository;
    private LoadUserByUsernameUseCase loadUserByUsernameUseCase;

    @BeforeEach
    void setUp() {
        usuarioRepository = mock(UsuarioRepository.class);
        loadUserByUsernameUseCase = new LoadUserByUsernameUseCase(usuarioRepository);
    }

    @Test
    void execute_ShouldReturnUserDetails_WhenUsuarioExists() {
        // Arrange
        String email = "teste@empresa.com";
        Usuario usuario = new Usuario();
        usuario.setIdUsuario(1);
        usuario.setEmail(email);
        usuario.setSenha("123456");

        when(usuarioRepository.findByEmail(email)).thenReturn(Optional.of(usuario));

        // Act
        UserDetails result = loadUserByUsernameUseCase.execute(email);

        // Assert
        assertNotNull(result);
        assertTrue(result instanceof UsuarioDetalhesDto);
        assertEquals(email, result.getUsername());
        assertEquals("123456", result.getPassword());
        verify(usuarioRepository, times(1)).findByEmail(email);
    }

    @Test
    void execute_ShouldThrowException_WhenUsuarioNotFound() {
        // Arrange
        String email = "naoexiste@empresa.com";
        when(usuarioRepository.findByEmail(email)).thenReturn(Optional.empty());

        // Act & Assert
        UsernameNotFoundException exception = assertThrows(
                UsernameNotFoundException.class,
                () -> loadUserByUsernameUseCase.execute(email)
        );

        assertEquals("Usuário: naoexiste@empresa.com não encontrado", exception.getMessage());
        verify(usuarioRepository, times(1)).findByEmail(email);
    }
}
