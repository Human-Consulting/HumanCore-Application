package com.humanconsulting.humancore_api.application.usecases.usuario;

import com.humanconsulting.humancore_api.domain.entities.Usuario;
import com.humanconsulting.humancore_api.domain.exception.EntidadeNaoEncontradaException;
import com.humanconsulting.humancore_api.domain.repositories.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BuscarUsuarioPorEmailUseCaseTest {

    private UsuarioRepository usuarioRepository;
    private BuscarUsuarioPorEmailUseCase useCase;

    @BeforeEach
    void setUp() {
        usuarioRepository = mock(UsuarioRepository.class);
        useCase = new BuscarUsuarioPorEmailUseCase(usuarioRepository);
    }

    @Test
    void deveRetornarIdUsuarioQuandoEncontrado() {
        // Arrange
        Usuario usuario = new Usuario();
        usuario.setIdUsuario(42);
        usuario.setEmail("teste@teste.com");

        when(usuarioRepository.findByEmail("teste@teste.com"))
                .thenReturn(Optional.of(usuario));

        // Act
        Integer idUsuario = useCase.execute("teste@teste.com");

        // Assert
        assertEquals(42, idUsuario);
        verify(usuarioRepository).findByEmail("teste@teste.com");
    }

    @Test
    void deveLancarExcecaoQuandoUsuarioNaoEncontrado() {
        // Arrange
        when(usuarioRepository.findByEmail("naoexiste@teste.com"))
                .thenReturn(Optional.empty());

        // Act & Assert
        EntidadeNaoEncontradaException exception = assertThrows(
                EntidadeNaoEncontradaException.class,
                () -> useCase.execute("naoexiste@teste.com")
        );

        assertEquals("Usuário não encontrado.", exception.getMessage());
        verify(usuarioRepository).findByEmail("naoexiste@teste.com");
    }
}
