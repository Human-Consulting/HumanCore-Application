package com.humanconsulting.humancore_api.application.usecases.sala;

import com.humanconsulting.humancore_api.application.usecases.mensagem.CadastrarMensagemInfoUseCase;
import com.humanconsulting.humancore_api.application.usecases.sala.mappers.SalaResponseMapper;
import com.humanconsulting.humancore_api.domain.entities.Sala;
import com.humanconsulting.humancore_api.domain.entities.Usuario;
import com.humanconsulting.humancore_api.domain.exception.EntidadeNaoEncontradaException;
import com.humanconsulting.humancore_api.domain.repositories.SalaRepository;
import com.humanconsulting.humancore_api.domain.repositories.UsuarioRepository;
import com.humanconsulting.humancore_api.web.dtos.request.SalaRequestDto;
import com.humanconsulting.humancore_api.web.dtos.response.sala.SalaResponseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CadastrarSalaUseCaseTest {

    private SalaRepository salaRepository;
    private UsuarioRepository usuarioRepository;
    private CadastrarMensagemInfoUseCase cadastrarMensagemInfoUseCase;
    private SalaResponseMapper salaResponseMapper;
    private CadastrarSalaUseCase cadastrarSalaUseCase;

    @BeforeEach
    void setUp() {
        salaRepository = mock(SalaRepository.class);
        usuarioRepository = mock(UsuarioRepository.class);
        cadastrarMensagemInfoUseCase = mock(CadastrarMensagemInfoUseCase.class);
        salaResponseMapper = mock(SalaResponseMapper.class);

        cadastrarSalaUseCase = new CadastrarSalaUseCase(
                salaRepository, usuarioRepository, cadastrarMensagemInfoUseCase, salaResponseMapper
        );
    }

    @Test
    void execute_ShouldCadastrarSala_WhenDadosValidos() {
        // Arrange
        SalaRequestDto request = new SalaRequestDto();

        request.setNome("Sala Teste");
        request.setUrlImagem("img.png"); // importante: não deixar null
        request.setFkEmpresa(null);
        request.setFkProjeto(null);
        request.setParticipantes(List.of(10));
        request.setIdEditor(20);

        Usuario participante = new Usuario();
        participante.setIdUsuario(10);

        Usuario editor = new Usuario();
        editor.setIdUsuario(20);

        Sala salaSalva = new Sala();
        salaSalva.setIdSala(1);
        salaSalva.setNome("Sala Teste");

        SalaResponseDto responseDto = new SalaResponseDto();
        responseDto.setIdSala(1);
        responseDto.setNome("Sala Teste");

        when(usuarioRepository.findById(10)).thenReturn(Optional.of(participante));
        when(usuarioRepository.findById(20)).thenReturn(Optional.of(editor));
        when(salaRepository.save(any(Sala.class))).thenReturn(salaSalva);
        when(salaResponseMapper.toResponse(salaSalva)).thenReturn(responseDto);

        // Act
        SalaResponseDto result = cadastrarSalaUseCase.execute(request);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getIdSala());
        assertEquals("Sala Teste", result.getNome());
        verify(salaRepository, times(1)).save(any(Sala.class));
        verify(cadastrarMensagemInfoUseCase, times(1)).execute(any());
        verify(salaResponseMapper, times(1)).toResponse(salaSalva);
    }

    @Test
    void execute_ShouldThrowException_WhenParticipanteNaoEncontrado() {
        // Arrange
        SalaRequestDto request = new SalaRequestDto();

        request.setNome("Sala Teste");
        request.setUrlImagem("img.png"); // importante: não deixar null
        request.setFkEmpresa(null);
        request.setFkProjeto(null);
        request.setParticipantes(List.of(10));
        request.setIdEditor(20);

        when(usuarioRepository.findById(10)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EntidadeNaoEncontradaException.class,
                () -> cadastrarSalaUseCase.execute(request));
        verify(salaRepository, never()).save(any());
    }

    @Test
    void execute_ShouldThrowException_WhenEditorNaoEncontrado() {
        // Arrange
        SalaRequestDto request = new SalaRequestDto();

        request.setNome("Sala Teste");
        request.setUrlImagem("img.png"); // importante: não deixar null
        request.setFkEmpresa(null);
        request.setFkProjeto(null);
        request.setParticipantes(List.of(10));
        request.setIdEditor(20);

        Usuario participante = new Usuario();
        participante.setIdUsuario(10);

        when(usuarioRepository.findById(10)).thenReturn(Optional.of(participante));
        when(usuarioRepository.findById(20)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EntidadeNaoEncontradaException.class,
                () -> cadastrarSalaUseCase.execute(request));
        verify(salaRepository, never()).save(any());
    }
}
