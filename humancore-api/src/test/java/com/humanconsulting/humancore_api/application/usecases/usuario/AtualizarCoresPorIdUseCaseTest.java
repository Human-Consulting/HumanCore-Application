package com.humanconsulting.humancore_api.application.usecases.usuario;

import com.humanconsulting.humancore_api.domain.entities.Usuario;
import com.humanconsulting.humancore_api.domain.exception.EntidadeNaoEncontradaException;
import com.humanconsulting.humancore_api.domain.repositories.UsuarioRepository;
import com.humanconsulting.humancore_api.web.dtos.atualizar.usuario.UsuarioAtualizarCoresDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AtualizarCoresPorIdUseCaseTest {

    private UsuarioRepository usuarioRepository;
    private AtualizarCoresPorIdUseCase atualizarCoresPorIdUseCase;

    @BeforeEach
    void setUp() {
        usuarioRepository = mock(UsuarioRepository.class);
        atualizarCoresPorIdUseCase = new AtualizarCoresPorIdUseCase(usuarioRepository);
    }

    @Test
    void execute_ShouldUpdateCores_WhenUsuarioExists() {
        // Arrange
        Integer idUsuario = 1;
        Usuario usuario = new Usuario();
        usuario.setIdUsuario(idUsuario);
        usuario.setCores("azul");

        UsuarioAtualizarCoresDto dto = new UsuarioAtualizarCoresDto();
        dto.setCores("vermelho");

        when(usuarioRepository.findById(idUsuario)).thenReturn(Optional.of(usuario));
        when(usuarioRepository.save(usuario)).thenReturn(usuario);

        // Act
        Boolean result = atualizarCoresPorIdUseCase.execute(idUsuario, dto);

        // Assert
        assertTrue(result);
        assertEquals("vermelho", usuario.getCores());
        verify(usuarioRepository, times(1)).findById(idUsuario);
        verify(usuarioRepository, times(1)).save(usuario);
    }

    @Test
    void execute_ShouldThrowException_WhenUsuarioNotFound() {
        // Arrange
        Integer idUsuario = 99;
        UsuarioAtualizarCoresDto dto = new UsuarioAtualizarCoresDto();
        dto.setCores("verde");

        when(usuarioRepository.findById(idUsuario)).thenReturn(Optional.empty());

        // Act & Assert
        EntidadeNaoEncontradaException exception = assertThrows(
                EntidadeNaoEncontradaException.class,
                () -> atualizarCoresPorIdUseCase.execute(idUsuario, dto)
        );

        assertEquals("Usuário não encontrado", exception.getMessage());
        verify(usuarioRepository, times(1)).findById(idUsuario);
        verify(usuarioRepository, never()).save(any());
    }
}
