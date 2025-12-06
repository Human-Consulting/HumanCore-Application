package com.humanconsulting.humancore_api.application.usecases.usuario;

import com.humanconsulting.humancore_api.application.usecases.usuario.mappers.UsuarioResponseMapper;
import com.humanconsulting.humancore_api.domain.entities.Usuario;
import com.humanconsulting.humancore_api.domain.exception.EntidadeSemRetornoException;
import com.humanconsulting.humancore_api.domain.repositories.UsuarioRepository;
import com.humanconsulting.humancore_api.web.dtos.response.usuario.UsuarioResponseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ListarUsuariosUseCaseTest {

    private UsuarioRepository usuarioRepository;
    private UsuarioResponseMapper usuarioResponseMapper;
    private ListarUsuariosUseCase useCase;

    @BeforeEach
    void setUp() {
        usuarioRepository = mock(UsuarioRepository.class);
        usuarioResponseMapper = mock(UsuarioResponseMapper.class);
        useCase = new ListarUsuariosUseCase(usuarioRepository, usuarioResponseMapper);
    }

    @Test
    void deveRetornarListaDeUsuariosQuandoEncontrados() {
        // Arrange
        Usuario usuario = new Usuario();
        usuario.setIdUsuario(1);
        usuario.setNome("Pedro");

        UsuarioResponseDto responseDto = new UsuarioResponseDto();
        responseDto.setIdUsuario(1);
        responseDto.setNome("Pedro");

        when(usuarioRepository.findAll()).thenReturn(List.of(usuario));
        when(usuarioResponseMapper.toResponse(usuario)).thenReturn(responseDto);

        // Act
        List<UsuarioResponseDto> result = useCase.execute();

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Pedro", result.get(0).getNome());
        verify(usuarioRepository).findAll();
        verify(usuarioResponseMapper).toResponse(usuario);
    }

    @Test
    void deveLancarExcecaoQuandoNenhumUsuarioEncontrado() {
        // Arrange
        when(usuarioRepository.findAll()).thenReturn(Collections.emptyList());

        // Act & Assert
        assertThrows(EntidadeSemRetornoException.class, () -> useCase.execute());

        verify(usuarioRepository).findAll();
        verify(usuarioResponseMapper, never()).toResponse(any());
    }
}
