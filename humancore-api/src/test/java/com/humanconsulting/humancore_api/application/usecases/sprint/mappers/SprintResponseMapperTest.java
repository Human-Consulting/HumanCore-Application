package com.humanconsulting.humancore_api.application.usecases.sprint.mappers;

import com.humanconsulting.humancore_api.application.usecases.tarefa.mappers.TarefaResponseMapper;
import com.humanconsulting.humancore_api.domain.entities.Checkpoint;
import com.humanconsulting.humancore_api.domain.entities.Projeto;
import com.humanconsulting.humancore_api.domain.entities.Sprint;
import com.humanconsulting.humancore_api.domain.entities.Tarefa;
import com.humanconsulting.humancore_api.domain.repositories.CheckpointRepository;
import com.humanconsulting.humancore_api.domain.repositories.TarefaRepository;
import com.humanconsulting.humancore_api.web.dtos.response.sprint.SprintResponseDto;
import com.humanconsulting.humancore_api.web.dtos.response.tarefa.TarefaResponseDto;
import com.humanconsulting.humancore_api.web.mappers.UsuarioMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SprintResponseMapperTest {

    private TarefaRepository tarefaRepository;
    private CheckpointRepository checkpointRepository;
    private TarefaResponseMapper tarefaResponseMapper;
    private UsuarioMapper usuarioMapper;
    private SprintResponseMapper mapper;

    @BeforeEach
    void setup() {
        tarefaRepository = mock(TarefaRepository.class);
        checkpointRepository = mock(CheckpointRepository.class);
        tarefaResponseMapper = mock(TarefaResponseMapper.class);
        usuarioMapper = mock(UsuarioMapper.class);

        mapper = new SprintResponseMapper(
                tarefaRepository,
                checkpointRepository,
                tarefaResponseMapper,
                usuarioMapper
        );
    }

    @Test
    void testToResponse_Feliz() {
        // Arrange
        Projeto projeto = new Projeto();
        projeto.setIdProjeto(1);

        Sprint sprint = new Sprint();
        sprint.setIdSprint(10);
        sprint.setProjeto(projeto);

        Tarefa tarefa = new Tarefa();
        tarefa.setIdTarefa(100);

        TarefaResponseDto tarefaResponseDto = new TarefaResponseDto();

        tarefaResponseDto.setIdTarefa(100);
        tarefaResponseDto.setTitulo("Tarefa Teste");

        when(tarefaRepository.existsImpedimentoBySprint(10)).thenReturn(false);
        when(tarefaRepository.findByProjetoAndSprint(1, 10)).thenReturn(List.of(tarefa));
        when(tarefaResponseMapper.toResponse(tarefa)).thenReturn(tarefaResponseDto);
        when(checkpointRepository.findAllByTarefa_Sprint_IdSprint(10)).thenReturn(List.of(mock(Checkpoint.class)));

        // Act
        SprintResponseDto response = mapper.toResponse(sprint);

        // Assert
        assertNotNull(response);
        assertEquals(10, response.getIdSprint());
        assertFalse(response.getComImpedimento());
        assertEquals(1, response.getTarefas().size());
        assertEquals(100, response.getTarefas().get(0).getIdTarefa());
    }

    @Test
    void testToResponse_Triste_SemTarefas() {
        Projeto projeto = new Projeto();
        projeto.setIdProjeto(2);

        Sprint sprint = new Sprint();
        sprint.setIdSprint(20);
        sprint.setProjeto(projeto);

        when(tarefaRepository.existsImpedimentoBySprint(20)).thenReturn(true);
        when(tarefaRepository.findByProjetoAndSprint(2, 20)).thenReturn(Collections.emptyList());
        when(checkpointRepository.findAllByTarefa_Sprint_IdSprint(20)).thenReturn(Collections.emptyList());

        SprintResponseDto response = mapper.toResponse(sprint);

        assertNotNull(response);
        assertTrue(response.getComImpedimento());
        assertEquals(0, response.getTarefas().size());
        assertEquals(0.0, response.getProgresso()); // progresso calculado com checkpoints vazios
    }

    @Test
    void testToResponse_Triste_SprintSemProjeto() {
        Sprint sprint = new Sprint();
        sprint.setIdSprint(30);
        sprint.setProjeto(null); // caso sprint não tenha projeto associado

        // Esse cenário deve lançar NullPointerException porque o código acessa sprint.getProjeto().getIdProjeto()
        assertThrows(NullPointerException.class, () -> mapper.toResponse(sprint));
    }
}
