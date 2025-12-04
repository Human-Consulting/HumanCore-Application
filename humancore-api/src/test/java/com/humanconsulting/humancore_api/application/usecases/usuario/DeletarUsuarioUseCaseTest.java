package com.humanconsulting.humancore_api.application.usecases.usuario;

import com.humanconsulting.humancore_api.domain.entities.Usuario;
import com.humanconsulting.humancore_api.domain.exception.EntidadeNaoEncontradaException;
import com.humanconsulting.humancore_api.domain.repositories.UsuarioRepository;
import com.humanconsulting.humancore_api.web.dtos.request.UsuarioPermissaoDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class DeletarUsuarioUseCaseTest {

    private UsuarioRepository usuarioRepository;
    private DeletarUsuarioUseCase useCase;

    @BeforeEach
    void setUp() {
        usuarioRepository = mock(UsuarioRepository.class);
        useCase = new DeletarUsuarioUseCase(usuarioRepository);
    }

    @Test
    void deveDeletarUsuarioQuandoEncontrado() {
        // Arrange
        Usuario usuario = new Usuario();
        usuario.setIdUsuario(1);

        when(usuarioRepository.findById(1)).thenReturn(Optional.of(usuario));

        UsuarioPermissaoDto permissaoDto = new UsuarioPermissaoDto(); // pode ser expandido se necessário

        // Act
        useCase.execute(1, permissaoDto);

        // Assert
        verify(usuarioRepository).findById(1);
        verify(usuarioRepository).deleteById(1);
    }

    @Test
    void deveLancarExcecaoQuandoUsuarioNaoEncontrado() {
        // Arrange
        when(usuarioRepository.findById(99)).thenReturn(Optional.empty());

        UsuarioPermissaoDto permissaoDto = new UsuarioPermissaoDto();

        // Act & Assert
        assertThrows(EntidadeNaoEncontradaException.class, () -> useCase.execute(99, permissaoDto));

        verify(usuarioRepository).findById(99);
        verify(usuarioRepository, never()).deleteById(anyInt());
    }
}
