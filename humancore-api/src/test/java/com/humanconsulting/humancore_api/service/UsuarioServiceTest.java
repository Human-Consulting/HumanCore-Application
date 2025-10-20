package com.humanconsulting.humancore_api.service;

import com.humanconsulting.humancore_api.velho.controller.dto.atualizar.usuario.UsuarioAtualizarCoresDto;
import com.humanconsulting.humancore_api.velho.controller.dto.atualizar.usuario.UsuarioAtualizarSenhaDto;
import com.humanconsulting.humancore_api.velho.controller.dto.response.usuario.UsuarioResponseDto;
import com.humanconsulting.humancore_api.velho.exception.AcessoNegadoException;
import com.humanconsulting.humancore_api.velho.exception.EntidadeNaoEncontradaException;
import com.humanconsulting.humancore_api.velho.model.Empresa;
import com.humanconsulting.humancore_api.velho.model.Usuario;
import com.humanconsulting.humancore_api.velho.repository.UsuarioRepository;
import com.humanconsulting.humancore_api.velho.service.UsuarioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;
    @Mock
    private org.springframework.security.crypto.password.PasswordEncoder passwordEncoder;
    @InjectMocks
    private UsuarioService usuarioService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void cadastrar() {
    }

    @Test
    void buscarPorId() {
    }

    @Test
    void listar() {
    }

    @Test
    void deletar() {
    }

    @Test
    void atualizarPorId() {
    }

    @Test
    void atualizarSenhaPorId_deveAtualizarSenha_quandoDadosValidos() {
        // Arrange
        Integer idUsuario = 1;
        var dto = mock(UsuarioAtualizarSenhaDto.class);
        when(dto.getIdEditor()).thenReturn(idUsuario);
        when(dto.getSenhaAtual()).thenReturn("senhaAntiga");
        when(dto.getSenhaAtualizada()).thenReturn("senhaNova");
        var usuario = mock(Usuario.class);
        when(usuarioRepository.findById(idUsuario)).thenReturn(Optional.of(usuario));
        when(usuario.getSenha()).thenReturn("senhaCriptografada");
        when(passwordEncoder.matches("senhaAntiga", "senhaCriptografada")).thenReturn(true);
        when(passwordEncoder.matches("senhaNova", "senhaCriptografada")).thenReturn(false);
        when(passwordEncoder.encode("senhaNova")).thenReturn("novaSenhaCriptografada");
        when(usuarioRepository.save(usuario)).thenReturn(usuario);
        UsuarioService spyService = spy(usuarioService);
        doReturn(mock(UsuarioResponseDto.class)).when(spyService).passarParaResponse(usuario);

        // Act
        var result = spyService.atualizarSenhaPorId(idUsuario, dto);

        // Assert
        assertNotNull(result);
    }

    @Test
    void atualizarSenhaPorId_deveLancarExcecao_quandoEditorNaoEhDono() {
        // Arrange
        Integer idUsuario = 1;
        var dto = mock(UsuarioAtualizarSenhaDto.class);
        when(dto.getIdEditor()).thenReturn(2);

        // Act & Assert
        assertThrows(AcessoNegadoException.class, () -> usuarioService.atualizarSenhaPorId(idUsuario, dto));
    }

    @Test
    void atualizarCoresPorId_deveAtualizarCores_quandoUsuarioExiste() {
        // Arrange
        Integer idUsuario = 1;
        var dto = mock(UsuarioAtualizarCoresDto.class);
        when(dto.getCores()).thenReturn("#606080|#8d7dca|#4e5e8c|true");
        var usuario = mock(Usuario.class);
        when(usuarioRepository.findById(idUsuario)).thenReturn(Optional.of(usuario));
        when(usuarioRepository.save(usuario)).thenReturn(usuario);

        // Act
        Boolean result = usuarioService.atualizarCoresPorId(idUsuario, dto);

        // Assert
        assertTrue(result);
    }

    @Test
    void atualizarCoresPorId_deveLancarExcecao_quandoUsuarioNaoExiste() {
        // Arrange
        Integer idUsuario = 1;
        var dto = mock(UsuarioAtualizarCoresDto.class);
        when(usuarioRepository.findById(idUsuario)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EntidadeNaoEncontradaException.class, () -> usuarioService.atualizarCoresPorId(idUsuario, dto));
    }

    @Test
    void autenticar() {
    }

    @Test
    void listarPorEmpresa() {
    }

    @Test
    void passarParaResponse() {
    }

    @Test
    void passarParaLoginResponse_deveRetornarLoginResponseDto_quandoDadosValidos() {
        // Arrange
        var usuario = mock(Usuario.class);
        var empresa = mock(Empresa.class);
        when(usuario.getEmpresa()).thenReturn(empresa);
        when(empresa.getNome()).thenReturn("EmpresaEntity X");
        when(usuario.getIdUsuario()).thenReturn(1);
        when(usuarioRepository.hasTarefaComImpedimento(1)).thenReturn(false);
        when(usuarioRepository.findProjetosVinculados(1)).thenReturn(List.of(10));
        when(usuarioRepository.findTarefasVinculadas(1)).thenReturn(List.of());
        UsuarioService spyService = spy(usuarioService);
        doReturn(mock(UsuarioResponseDto.class)).when(spyService).passarParaResponse(any());

        // Act
        var result = usuarioService.passarParaLoginResponse(usuario, "token123");

        // Assert
        assertNotNull(result);
    }

    @Test
    void passarParaLoginResponse_deveLancarExcecao_quandoEmpresaNull() {
        // Arrange
        var usuario = mock(Usuario.class);
        when(usuario.getEmpresa()).thenReturn(null);

        // Act & Assert
        assertThrows(NullPointerException.class, () -> usuarioService.passarParaLoginResponse(usuario, "token123"));
    }
}