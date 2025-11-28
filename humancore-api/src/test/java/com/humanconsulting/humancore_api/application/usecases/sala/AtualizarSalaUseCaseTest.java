package com.humanconsulting.humancore_api.application.usecases.sala;

import com.humanconsulting.humancore_api.application.usecases.sala.mappers.SalaResponseMapper;
import com.humanconsulting.humancore_api.domain.entities.Empresa;
import com.humanconsulting.humancore_api.domain.entities.Projeto;
import com.humanconsulting.humancore_api.domain.entities.Sala;
import com.humanconsulting.humancore_api.domain.entities.Usuario;
import com.humanconsulting.humancore_api.domain.exception.EntidadeNaoEncontradaException;
import com.humanconsulting.humancore_api.domain.notifiers.SalaNotifier;
import com.humanconsulting.humancore_api.domain.repositories.EmpresaRepository;
import com.humanconsulting.humancore_api.domain.repositories.ProjetoRepository;
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

class AtualizarSalaUseCaseTest {

    private SalaRepository salaRepository;
    private UsuarioRepository usuarioRepository;
    private ProjetoRepository projetoRepository;
    private EmpresaRepository empresaRepository;
    private SalaNotifier salaNotifier;
    private SalaResponseMapper salaResponseMapper;
    private AtualizarSalaUseCase atualizarSalaUseCase;

    @BeforeEach
    void setUp() {
        salaRepository = mock(SalaRepository.class);
        usuarioRepository = mock(UsuarioRepository.class);
        projetoRepository = mock(ProjetoRepository.class);
        empresaRepository = mock(EmpresaRepository.class);
        salaNotifier = mock(SalaNotifier.class);
        salaResponseMapper = mock(SalaResponseMapper.class);

        atualizarSalaUseCase = new AtualizarSalaUseCase(
                salaRepository, usuarioRepository, projetoRepository, empresaRepository, salaNotifier, salaResponseMapper
        );
    }

    @Test
    void execute_ShouldUpdateSala_WhenDadosValidos() {
        // Arrange
        Integer idSala = 1;
        Sala salaOriginal = new Sala();
        salaOriginal.setIdSala(idSala);
        salaOriginal.setUrlImagem("original.png");

        Usuario participante = new Usuario();
        participante.setIdUsuario(10);

        Usuario editor = new Usuario();
        editor.setIdUsuario(20);

        Projeto projeto = new Projeto();
        projeto.setIdProjeto(30);

        Empresa empresa = new Empresa();
        empresa.setIdEmpresa(40);

        SalaRequestDto request = new SalaRequestDto();

        request.setNome("Nova Sala");
        request.setUrlImagem(""); // importante: não deixar null
        request.setFkEmpresa(40);
        request.setFkProjeto(30);
        request.setParticipantes(List.of(10));
        request.setIdEditor(20);



        Sala salaAtualizada = new Sala();
        salaAtualizada.setIdSala(idSala);
        salaAtualizada.setUrlImagem("original.png");

        SalaResponseDto responseDto = mock(SalaResponseDto.class);
        responseDto.setIdSala(idSala);

        when(salaRepository.buscarComUsuarios(idSala)).thenReturn(Optional.of(salaOriginal));
        when(usuarioRepository.findById(10)).thenReturn(Optional.of(participante));
        when(usuarioRepository.findById(20)).thenReturn(Optional.of(editor));
        when(projetoRepository.findById(30)).thenReturn(Optional.of(projeto));
        when(empresaRepository.findById(40)).thenReturn(Optional.of(empresa));
        when(salaRepository.save(any(Sala.class))).thenReturn(salaAtualizada);
        when(salaResponseMapper.toResponse(salaAtualizada)).thenReturn(responseDto);

        when(salaResponseMapper.toResponse(any(Sala.class))).thenReturn(responseDto);


        // Act
        SalaResponseDto result = atualizarSalaUseCase.execute(idSala, request);

        // Assert
        assertNotNull(result);
        verify(salaRepository, times(1)).save(any(Sala.class));
        verify(salaNotifier, times(1)).notificarAtualizacoesSala(any(), any(), any());
    }

    @Test
    void execute_ShouldThrowException_WhenSalaNaoEncontrada() {
        // Arrange
        when(salaRepository.buscarComUsuarios(99)).thenReturn(Optional.empty());
        SalaRequestDto request = mock(SalaRequestDto.class);

        // Act & Assert
        assertThrows(EntidadeNaoEncontradaException.class,
                () -> atualizarSalaUseCase.execute(99, request));
    }

    @Test
    void execute_ShouldThrowException_WhenParticipanteNaoEncontrado() {
        // Arrange
        Integer idSala = 1;
        Sala salaOriginal = new Sala();
        salaOriginal.setIdSala(idSala);

        SalaRequestDto request = mock(SalaRequestDto.class);

        when(salaRepository.buscarComUsuarios(idSala)).thenReturn(Optional.of(salaOriginal));
        when(usuarioRepository.findById(10)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(NullPointerException.class,
                () -> atualizarSalaUseCase.execute(idSala, request));
    }

    @Test
    void execute_ShouldThrowException_WhenEditorNaoEncontrado() {
        // Arrange
        Integer idSala = 1;
        Sala salaOriginal = new Sala();
        salaOriginal.setIdSala(idSala);

        Usuario participante = new Usuario();
        participante.setIdUsuario(10);

        SalaRequestDto request = mock(SalaRequestDto.class);

        when(salaRepository.buscarComUsuarios(idSala)).thenReturn(Optional.of(salaOriginal));
        when(usuarioRepository.findById(10)).thenReturn(Optional.of(participante));
        when(usuarioRepository.findById(20)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(NullPointerException.class,
                () -> atualizarSalaUseCase.execute(idSala, request));
    }

}