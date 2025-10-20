package com.humanconsulting.humancore_api.service;

import com.humanconsulting.humancore_api.velho.exception.AcessoNegadoException;
import com.humanconsulting.humancore_api.velho.exception.EntidadeSemRetornoException;
import com.humanconsulting.humancore_api.velho.model.Checkpoint;
import com.humanconsulting.humancore_api.velho.model.Tarefa;
import com.humanconsulting.humancore_api.velho.model.Usuario;
import com.humanconsulting.humancore_api.velho.observer.EmailNotifier;
import com.humanconsulting.humancore_api.velho.repository.CheckpointRepository;
import com.humanconsulting.humancore_api.velho.repository.SprintRepository;
import com.humanconsulting.humancore_api.velho.repository.TarefaRepository;
import com.humanconsulting.humancore_api.velho.repository.UsuarioRepository;
import com.humanconsulting.humancore_api.velho.service.CheckpointService;
import com.humanconsulting.humancore_api.velho.service.TarefaService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Collections;
import java.util.Optional;
import java.util.List;

import com.humanconsulting.humancore_api.velho.controller.dto.atualizar.tarefa.AtualizarStatusRequestDto;
import com.humanconsulting.humancore_api.velho.controller.dto.response.tarefa.TarefaResponseDto;
import com.humanconsulting.humancore_api.velho.controller.dto.response.checkpoint.CheckpointResponseDto;
import com.humanconsulting.humancore_api.velho.mapper.TarefaMapper;
import com.humanconsulting.humancore_api.velho.mapper.CheckpointMapper;
import com.humanconsulting.humancore_api.velho.utils.ProgressoCalculator;

@ExtendWith(MockitoExtension.class)
class TarefaServiceTest {
    @Mock
    CheckpointService checkpointService;
    @Mock
    TarefaRepository tarefaRepository;
    @Mock
    SprintRepository sprintRepository;
    @Mock
    UsuarioRepository usuarioRepository;
    @Mock
    CheckpointRepository checkpointRepository;
    @Mock EmailNotifier emailNotifier;
    @InjectMocks
    TarefaService tarefaService;

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
    void atualizar() {
        }

    @Test
    void listarPorSprint_deveRetornarLista_quandoExistemTarefas() {
        // Arrange
        Tarefa tarefa = mock(Tarefa.class);
        when(tarefaRepository.findBySprint_IdSprint(1)).thenReturn(Collections.singletonList(tarefa));
        TarefaService spyService = spy(tarefaService);
        doReturn(mock(TarefaResponseDto.class)).when(spyService).passarParaResponse(any());

        // Act
        List<TarefaResponseDto> result = spyService.listarPorSprint(1);

        // Assert
        assertFalse(result.isEmpty());
    }

    @Test
    void listarPorSprint_deveLancarExcecao_quandoNaoExistemTarefas() {
        // Arrange
        when(tarefaRepository.findBySprint_IdSprint(2)).thenReturn(Collections.emptyList());

        // Act & Assert
        assertThrows(EntidadeSemRetornoException.class, () -> tarefaService.listarPorSprint(2));
    }

    @Test
    void atualizarImpedimento_deveAtualizar_quandoEditorEhResponsavel() {
        // Arrange
        AtualizarStatusRequestDto request = mock(AtualizarStatusRequestDto.class);
        when(request.getIdEditor()).thenReturn(1);
        Tarefa tarefa = mock(Tarefa.class);
        Usuario usuario = mock(Usuario.class);
        when(tarefaRepository.findById(1)).thenReturn(Optional.of(tarefa));
        when(tarefa.getResponsavel()).thenReturn(usuario);
        when(usuario.getIdUsuario()).thenReturn(1);
        when(tarefa.getComImpedimento()).thenReturn(true);
        TarefaService spyService = spy(tarefaService);
        doReturn(mock(TarefaResponseDto.class)).when(spyService).passarParaResponse(any());

        // Act & Assert
        assertDoesNotThrow(() -> spyService.atualizarImpedimento(1, request));
    }

    @Test
    void atualizarImpedimento_deveLancarExcecao_quandoEditorNaoEhResponsavel() {
        // Arrange
        AtualizarStatusRequestDto request = mock(AtualizarStatusRequestDto.class);
        when(request.getIdEditor()).thenReturn(2);
        Tarefa tarefa = mock(Tarefa.class);
        Usuario usuario = mock(Usuario.class);
        when(tarefaRepository.findById(1)).thenReturn(Optional.of(tarefa));
        when(tarefa.getResponsavel()).thenReturn(usuario);
        when(usuario.getIdUsuario()).thenReturn(1);
        TarefaService spyService = spy(tarefaService);

        // Act & Assert
        assertThrows(AcessoNegadoException.class, () -> spyService.atualizarImpedimento(1, request));
    }

    @Test
    void passarParaResponse_deveRetornarDto_quandoDadosValidos() {
        // Arrange
        Tarefa tarefa = mock(Tarefa.class);
        when(tarefa.getIdTarefa()).thenReturn(1);
        List<Checkpoint> checkpoints = Collections.singletonList(mock(Checkpoint.class));
        when(checkpointRepository.findAllByTarefa_IdTarefa(1)).thenReturn(checkpoints);

        try (MockedStatic<CheckpointMapper> checkpointMapperMock = mockStatic(CheckpointMapper.class)) {
            checkpointMapperMock.when(() -> CheckpointMapper.toDto(any())).thenReturn(mock(CheckpointResponseDto.class));
            try (MockedStatic<ProgressoCalculator> progressoCalculatorMock = mockStatic(ProgressoCalculator.class)) {
                progressoCalculatorMock.when(() -> ProgressoCalculator.calularProgresso(anyList())).thenReturn(100.0);
                try (MockedStatic<TarefaMapper> tarefaMapperMock = mockStatic(TarefaMapper.class)) {
                    TarefaResponseDto responseDto = mock(TarefaResponseDto.class);
                    tarefaMapperMock.when(() -> TarefaMapper.toDto(any(), anyList(), anyDouble())).thenReturn(responseDto);

                    // Act
                    TarefaResponseDto result = tarefaService.passarParaResponse(tarefa);

                    // Assert
                    assertEquals(responseDto, result);
                }
            }
        }
    }

    @Test
    void passarParaResponse_deveLancarExcecao_quandoCheckpointRepositoryFalha() {
        // Arrange
        Tarefa tarefa = mock(Tarefa.class);
        when(tarefa.getIdTarefa()).thenReturn(1);
        when(checkpointRepository.findAllByTarefa_IdTarefa(1)).thenThrow(new RuntimeException("Erro ao buscar checkpoints"));

        // Act & Assert
        assertThrows(RuntimeException.class, () -> tarefaService.passarParaResponse(tarefa));
    }
}

