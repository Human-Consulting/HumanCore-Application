package com.humanconsulting.humancore_api.application.usecases.usuario;

import com.humanconsulting.humancore_api.application.usecases.usuario.mappers.UsuarioResponseMapper;
import com.humanconsulting.humancore_api.domain.entities.Usuario;
import com.humanconsulting.humancore_api.domain.exception.EntidadeNaoEncontradaException;
import com.humanconsulting.humancore_api.domain.repositories.UsuarioRepository;
import com.humanconsulting.humancore_api.web.dtos.response.usuario.LoginResponseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BuscarUsuarioPorIdUseCaseTest {

    private UsuarioRepository usuarioRepository;
    private UsuarioResponseMapper usuarioResponseMapper;
    private BuscarUsuarioPorIdUseCase useCase;

    @BeforeEach
    void setUp() {
        usuarioRepository = mock(UsuarioRepository.class);
        usuarioResponseMapper = mock(UsuarioResponseMapper.class);
        useCase = new BuscarUsuarioPorIdUseCase(usuarioRepository, usuarioResponseMapper);
    }

    @Test
    void deveRetornarLoginResponseQuandoUsuarioEncontrado() {
        // Arrange
        Usuario usuario = new Usuario();
        usuario.setIdUsuario(1);
        usuario.setEmail("teste@teste.com");

        LoginResponseDto responseDto = new LoginResponseDto();

        responseDto.setNome("token-falso");
        responseDto.setEmail(usuario.getEmail());

        when(usuarioRepository.findById(1))
                .thenReturn(Optional.of(usuario));

        when(usuarioResponseMapper.toLoginResponse(usuario, null))
                .thenReturn(responseDto);

        // Act
        LoginResponseDto result = useCase.execute(1);

        // Assert
        assertNotNull(result);
        assertEquals("teste@teste.com", result.getEmail());
        verify(usuarioRepository).findById(1);
        verify(usuarioResponseMapper).toLoginResponse(usuario, null);
    }

    @Test
    void deveLancarExcecaoQuandoUsuarioNaoEncontrado() {
        // Arrange
        when(usuarioRepository.findById(99))
                .thenReturn(Optional.empty());

        // Act & Assert
        EntidadeNaoEncontradaException exception = assertThrows(
                EntidadeNaoEncontradaException.class,
                () -> useCase.execute(99)
        );

        assertEquals("Usuário não encontrado.", exception.getMessage());
        verify(usuarioRepository).findById(99);
        verify(usuarioResponseMapper, never()).toLoginResponse(any(), any());
    }
}
