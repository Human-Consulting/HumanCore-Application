package com.humanconsulting.humancore_api.service;

import com.humanconsulting.humancore_api.velho.controller.dto.response.sprint.SprintResponseDto;
import com.humanconsulting.humancore_api.velho.controller.dto.response.tarefa.TarefaResponseDto;
import com.humanconsulting.humancore_api.velho.model.Checkpoint;
import com.humanconsulting.humancore_api.velho.model.Projeto;
import com.humanconsulting.humancore_api.velho.model.Sprint;
import com.humanconsulting.humancore_api.velho.model.Tarefa;
import com.humanconsulting.humancore_api.velho.repository.*;
import com.humanconsulting.humancore_api.velho.service.SprintService;
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

import com.humanconsulting.humancore_api.velho.mapper.SprintMapper;
import com.humanconsulting.humancore_api.velho.utils.ProgressoCalculator;

@ExtendWith(MockitoExtension.class)
class SprintServiceTest {
    @Mock
    ProjetoRepository projetoRepository;
    @Mock
    SprintRepository sprintRepository;
    @Mock
    CheckpointRepository checkpointRepository;
    @Mock
    TarefaRepository tarefaRepository;
    @Mock
    UsuarioRepository usuarioRepository;
    @Mock
    TarefaService tarefaService;
    @InjectMocks
    SprintService sprintService;

    @Test
    void passarParaResponse_deveRetornarSprintResponseDto_quandoDadosValidos() {
        Sprint sprint = mock(Sprint.class);
        when(sprint.getIdSprint()).thenReturn(1);
        Projeto projeto = mock(Projeto.class);
        when(sprint.getProjeto()).thenReturn(projeto);
        when(projeto.getIdProjeto()).thenReturn(1);
        when(tarefaRepository.existsImpedimentoBySprint(1)).thenReturn(true);
        Tarefa tarefa = mock(Tarefa.class);
        when(tarefaRepository.findByProjetoAndSprint(1, 1)).thenReturn(Collections.singletonList(tarefa));
        when(tarefaService.passarParaResponse(tarefa)).thenReturn(mock(TarefaResponseDto.class));
        Checkpoint checkpoint = mock(Checkpoint.class);
        when(checkpointRepository.findAllByTarefa_Sprint_IdSprint(1)).thenReturn(Collections.singletonList(checkpoint));
        try (MockedStatic<ProgressoCalculator> progressoCalculatorMock = mockStatic(ProgressoCalculator.class)) {
            progressoCalculatorMock.when(() -> ProgressoCalculator.calularProgresso(anyList())).thenReturn(0.5);
            try (MockedStatic<SprintMapper> sprintMapperMock = mockStatic(SprintMapper.class)) {
                SprintResponseDto responseDto = mock(SprintResponseDto.class);
                sprintMapperMock.when(() -> SprintMapper.toDto(any(), anyDouble(), anyBoolean(), anyList())).thenReturn(responseDto);
                SprintResponseDto result = sprintService.passarParaResponse(sprint, 1);
                assertEquals(responseDto, result);
            }
        }
    }

    @Test
    void passarParaResponse_deveLancarExcecao_quandoProjetoForNull() {
        Sprint sprint = mock(Sprint.class);
        when(sprint.getProjeto()).thenReturn(null);

        assertThrows(NullPointerException.class, () -> sprintService.passarParaResponse(sprint, 1));
    }
}

