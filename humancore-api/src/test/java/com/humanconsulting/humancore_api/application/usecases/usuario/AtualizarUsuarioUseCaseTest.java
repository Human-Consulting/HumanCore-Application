package com.humanconsulting.humancore_api.application.usecases.usuario;

import com.humanconsulting.humancore_api.application.enums.PermissaoEnum;
import com.humanconsulting.humancore_api.application.usecases.usuario.mappers.UsuarioResponseMapper;
import com.humanconsulting.humancore_api.domain.entities.Usuario;
import com.humanconsulting.humancore_api.domain.exception.EntidadeNaoEncontradaException;
import com.humanconsulting.humancore_api.domain.exception.EntidadeSemPermissaoException;
import com.humanconsulting.humancore_api.domain.repositories.UsuarioRepository;
import com.humanconsulting.humancore_api.web.dtos.atualizar.usuario.UsuarioAtualizarDto;
import com.humanconsulting.humancore_api.web.dtos.response.usuario.UsuarioResponseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AtualizarUsuarioUseCaseTest {

    private UsuarioRepository usuarioRepository;
    private UsuarioResponseMapper usuarioResponseMapper;
    private AtualizarUsuarioUseCase useCase;

    @BeforeEach
    void setUp() {
        usuarioRepository = mock(UsuarioRepository.class);
        usuarioResponseMapper = mock(UsuarioResponseMapper.class);
        useCase = new AtualizarUsuarioUseCase(usuarioRepository, usuarioResponseMapper);
    }

    @Test
    void deveAtualizarUsuarioComSucesso() {
        // Arrange
        Usuario alvo = new Usuario();
        alvo.setIdUsuario(1);
        alvo.setPermissao(PermissaoEnum.FUNC.name()); // alvo é FUNC

        Usuario editor = new Usuario();
        editor.setIdUsuario(2);
        editor.setPermissao(PermissaoEnum.GESTOR.name()); // editor é GESTOR

        UsuarioAtualizarDto dto = new UsuarioAtualizarDto();
        dto.setIdEditor(2);
        dto.setPermissaoEditor(PermissaoEnum.GESTOR.name()); // editor tem permissão GESTOR
        dto.setPermissao(PermissaoEnum.FUNC.name()); // alvo continua FUNC

        when(usuarioRepository.findById(1)).thenReturn(Optional.of(alvo));
        when(usuarioRepository.findById(2)).thenReturn(Optional.of(editor));
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(alvo);

        UsuarioResponseDto responseDto = new UsuarioResponseDto();
        responseDto.setIdUsuario(1);
        when(usuarioResponseMapper.toResponse(alvo)).thenReturn(responseDto);

        // Act
        UsuarioResponseDto result = useCase.execute(1, dto);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getIdUsuario());
        verify(usuarioRepository, times(1)).save(any(Usuario.class));
    }


    @Test
    void deveLancarExcecaoQuandoUsuarioNaoEncontrado() {
        UsuarioAtualizarDto dto = new UsuarioAtualizarDto();
        dto.setIdEditor(2);
        dto.setPermissaoEditor("ADMIN");

        when(usuarioRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(EntidadeNaoEncontradaException.class,
                () -> useCase.execute(1, dto));
    }

    @Test
    void deveLancarExcecaoQuandoPermissaoInvalida() {
        Usuario alvo = new Usuario();
        alvo.setIdUsuario(1);
        alvo.setPermissao("EDITOR");

        Usuario editor = new Usuario();
        editor.setIdUsuario(2);
        editor.setPermissao("ADMIN");

        UsuarioAtualizarDto dto = new UsuarioAtualizarDto();
        dto.setIdEditor(2);
        dto.setPermissaoEditor("INVALID"); // inválido

        when(usuarioRepository.findById(1)).thenReturn(Optional.of(alvo));
        when(usuarioRepository.findById(2)).thenReturn(Optional.of(editor));

        assertThrows(EntidadeSemPermissaoException.class,
                () -> useCase.execute(1, dto));
    }

    @Test
    void deveLancarExcecaoQuandoUsuarioNaoTemPermissaoDeModificarProprio() {
        Usuario alvo = new Usuario();
        alvo.setIdUsuario(1);
        alvo.setPermissao("EDITOR");

        UsuarioAtualizarDto dto = new UsuarioAtualizarDto();
        dto.setIdEditor(1);
        dto.setPermissaoEditor("EDITOR"); // não tem MODIFICAR_PROPRIO
        dto.setPermissao("EDITOR");

        when(usuarioRepository.findById(1)).thenReturn(Optional.of(alvo));

        assertThrows(EntidadeSemPermissaoException.class,
                () -> useCase.execute(1, dto));
    }

    @Test
    void deveLancarExcecaoQuandoUsuarioTentaMudarAPropriaPermissao() {
        Usuario alvo = new Usuario();
        alvo.setIdUsuario(1);
        alvo.setPermissao("EDITOR");

        UsuarioAtualizarDto dto = new UsuarioAtualizarDto();
        dto.setIdEditor(1);
        dto.setPermissaoEditor("EDITOR");
        dto.setPermissao("ADMIN"); // tentando mudar a própria permissão

        when(usuarioRepository.findById(1)).thenReturn(Optional.of(alvo));

        assertThrows(EntidadeSemPermissaoException.class,
                () -> useCase.execute(1, dto));
    }
}
