package com.humanconsulting.humancore_api.application.usecases.usuario;

import com.humanconsulting.humancore_api.application.usecases.usuario.mappers.UsuarioResponseMapper;
import com.humanconsulting.humancore_api.domain.entities.Usuario;
import com.humanconsulting.humancore_api.domain.exception.EntidadeSemRetornoException;
import com.humanconsulting.humancore_api.domain.repositories.UsuarioRepository;
import com.humanconsulting.humancore_api.domain.utils.PageResult;
import com.humanconsulting.humancore_api.infrastructure.utils.PageResultImpl;
import com.humanconsulting.humancore_api.web.dtos.response.usuario.UsuarioResponseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ListarUsuariosPorEmpresaFiltradoPorNomeUseCaseTest {

    private UsuarioRepository usuarioRepository;
    private UsuarioResponseMapper usuarioResponseMapper;
    private ListarUsuariosPorEmpresaFiltradoPorNomeUseCase useCase;

    @BeforeEach
    void setUp() {
        usuarioRepository = mock(UsuarioRepository.class);
        usuarioResponseMapper = mock(UsuarioResponseMapper.class);
        useCase = new ListarUsuariosPorEmpresaFiltradoPorNomeUseCase(usuarioRepository, usuarioResponseMapper);
    }

    @Test
    void deveRetornarUsuariosQuandoEncontradosSemConsultores() {
        // Arrange
        Usuario usuario = new Usuario();
        usuario.setIdUsuario(1);
        usuario.setNome("Pedro");

        PageResult<Usuario> pageResult = new PageResultImpl<>(
                List.of(usuario), 0, 10, 1, 1
        );

        when(usuarioRepository.findByFkEmpresa_IdEmpresaAndNomeContainingIgnoreCase(1, 0, 10, "Pedro"))
                .thenReturn(pageResult);

        UsuarioResponseDto responseDto = new UsuarioResponseDto();
        responseDto.setIdUsuario(1);
        responseDto.setNome("Pedro");

        when(usuarioResponseMapper.toResponse(usuario)).thenReturn(responseDto);

        // Act
        PageResult<UsuarioResponseDto> result = useCase.execute(1, 0, 10, "Pedro", false);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getContent().size());
        assertEquals("Pedro", result.getContent().get(0).getNome());
        verify(usuarioRepository).findByFkEmpresa_IdEmpresaAndNomeContainingIgnoreCase(1, 0, 10, "Pedro");
        verify(usuarioResponseMapper).toResponse(usuario);
    }

    @Test
    void deveRetornarUsuariosQuandoEncontradosComConsultores() {
        // Arrange
        Usuario usuario = new Usuario();
        usuario.setIdUsuario(2);
        usuario.setNome("Maria");

        PageResult<Usuario> pageResult = new PageResultImpl<>(
                List.of(usuario), 0, 10, 1, 1
        );

        when(usuarioRepository.findByFkEmpresa_IdEmpresaOrUsuarioPermissaoLikeConsultorAndNomeContainingIgnoreCase(1, 0, 10, "Maria"))
                .thenReturn(pageResult);

        UsuarioResponseDto responseDto = new UsuarioResponseDto();
        responseDto.setIdUsuario(2);
        responseDto.setNome("Maria");

        when(usuarioResponseMapper.toResponse(usuario)).thenReturn(responseDto);

        // Act
        PageResult<UsuarioResponseDto> result = useCase.execute(1, 0, 10, "Maria", true);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getContent().size());
        assertEquals("Maria", result.getContent().get(0).getNome());
        verify(usuarioRepository).findByFkEmpresa_IdEmpresaOrUsuarioPermissaoLikeConsultorAndNomeContainingIgnoreCase(1, 0, 10, "Maria");
        verify(usuarioResponseMapper).toResponse(usuario);
    }

    @Test
    void deveLancarExcecaoQuandoNenhumUsuarioEncontrado() {
        // Arrange
        PageResult<Usuario> pageResult = new PageResultImpl<>(
                Collections.emptyList(), 0, 10, 0, 0
        );

        when(usuarioRepository.findByFkEmpresa_IdEmpresaAndNomeContainingIgnoreCase(1, 0, 10, "Inexistente"))
                .thenReturn(pageResult);

        // Act & Assert
        assertThrows(EntidadeSemRetornoException.class,
                () -> useCase.execute(1, 0, 10, "Inexistente", false));

        verify(usuarioRepository).findByFkEmpresa_IdEmpresaAndNomeContainingIgnoreCase(1, 0, 10, "Inexistente");
        verify(usuarioResponseMapper, never()).toResponse(any());
    }
}
