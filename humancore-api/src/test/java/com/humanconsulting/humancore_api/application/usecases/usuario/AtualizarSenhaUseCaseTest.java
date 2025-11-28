package com.humanconsulting.humancore_api.application.usecases.usuario;

import com.humanconsulting.humancore_api.application.usecases.usuario.mappers.UsuarioResponseMapper;
import com.humanconsulting.humancore_api.domain.entities.Usuario;
import com.humanconsulting.humancore_api.domain.exception.EntidadeNaoEncontradaException;
import com.humanconsulting.humancore_api.domain.exception.EntidadeSemPermissaoException;
import com.humanconsulting.humancore_api.domain.repositories.UsuarioRepository;
import com.humanconsulting.humancore_api.web.dtos.atualizar.usuario.UsuarioAtualizarSenhaDto;
import com.humanconsulting.humancore_api.web.dtos.response.usuario.UsuarioResponseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AtualizarSenhaUseCaseTest {

    private UsuarioRepository usuarioRepository;
    private PasswordEncoder passwordEncoder;
    private UsuarioResponseMapper usuarioResponseMapper;
    private AtualizarSenhaUseCase useCase;

    @BeforeEach
    void setUp() {
        usuarioRepository = mock(UsuarioRepository.class);
        passwordEncoder = mock(PasswordEncoder.class);
        usuarioResponseMapper = mock(UsuarioResponseMapper.class);
        useCase = new AtualizarSenhaUseCase(usuarioRepository, passwordEncoder, usuarioResponseMapper);
    }

    @Test
    void execute_ShouldUpdateSenha_WhenDadosValidos() {
        Integer idUsuario = 1;
        Usuario usuario = new Usuario();
        usuario.setIdUsuario(idUsuario);
        usuario.setSenha("encodedOld");

        UsuarioAtualizarSenhaDto dto = new UsuarioAtualizarSenhaDto();
        dto.setIdEditor(1);
        dto.setSenhaAtual("old");
        dto.setSenhaAtualizada("new");

        when(usuarioRepository.findById(idUsuario)).thenReturn(Optional.of(usuario));
        when(passwordEncoder.matches("old", "encodedOld")).thenReturn(true);
        when(passwordEncoder.matches("new", "encodedOld")).thenReturn(false);
        when(passwordEncoder.encode("new")).thenReturn("encodedNew");
        when(usuarioRepository.save(usuario)).thenReturn(usuario);

        UsuarioResponseDto responseDto = new UsuarioResponseDto();
        responseDto.setIdUsuario(idUsuario);
        when(usuarioResponseMapper.toResponse(usuario)).thenReturn(responseDto);

        UsuarioResponseDto result = useCase.execute(idUsuario, dto);

        assertNotNull(result);
        assertEquals(idUsuario, result.getIdUsuario());
        assertEquals("encodedNew", usuario.getSenha());
        verify(usuarioRepository, times(1)).save(usuario);
    }

    @Test
    void execute_ShouldThrowException_WhenEditorNotOwner() {
        UsuarioAtualizarSenhaDto dto = new UsuarioAtualizarSenhaDto();
        dto.setIdEditor(2); // diferente
        dto.setSenhaAtual("old");
        dto.setSenhaAtualizada("new");

        assertThrows(EntidadeSemPermissaoException.class,
                () -> useCase.execute(1, dto));
    }

    @Test
    void execute_ShouldThrowException_WhenUsuarioNotFound() {
        Integer idUsuario = 1;
        UsuarioAtualizarSenhaDto dto = new UsuarioAtualizarSenhaDto();
        dto.setIdEditor(1);
        dto.setSenhaAtual("old");
        dto.setSenhaAtualizada("new");

        when(usuarioRepository.findById(idUsuario)).thenReturn(Optional.empty());

        assertThrows(EntidadeNaoEncontradaException.class,
                () -> useCase.execute(idUsuario, dto));
    }

    @Test
    void execute_ShouldThrowException_WhenSenhaAtualIncorreta() {
        Integer idUsuario = 1;
        Usuario usuario = new Usuario();
        usuario.setIdUsuario(idUsuario);
        usuario.setSenha("encodedOld");

        UsuarioAtualizarSenhaDto dto = new UsuarioAtualizarSenhaDto();
        dto.setIdEditor(1);
        dto.setSenhaAtual("wrong");
        dto.setSenhaAtualizada("new");

        when(usuarioRepository.findById(idUsuario)).thenReturn(Optional.of(usuario));
        when(passwordEncoder.matches("wrong", "encodedOld")).thenReturn(false);

        assertThrows(EntidadeSemPermissaoException.class,
                () -> useCase.execute(idUsuario, dto));
    }

    @Test
    void execute_ShouldThrowException_WhenNovaSenhaIgualAtual() {
        Integer idUsuario = 1;
        Usuario usuario = new Usuario();
        usuario.setIdUsuario(idUsuario);
        usuario.setSenha("encodedOld");

        UsuarioAtualizarSenhaDto dto = new UsuarioAtualizarSenhaDto();
        dto.setIdEditor(1);
        dto.setSenhaAtual("old");
        dto.setSenhaAtualizada("old");

        when(usuarioRepository.findById(idUsuario)).thenReturn(Optional.of(usuario));
        when(passwordEncoder.matches("old", "encodedOld")).thenReturn(true);
        when(passwordEncoder.matches("old", "encodedOld")).thenReturn(true); // nova senha igual

        assertThrows(EntidadeSemPermissaoException.class,
                () -> useCase.execute(idUsuario, dto));
    }
}
