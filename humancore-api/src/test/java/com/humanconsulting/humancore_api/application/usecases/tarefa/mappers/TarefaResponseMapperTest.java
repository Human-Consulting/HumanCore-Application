package com.humanconsulting.humancore_api.application.usecases.tarefa.mappers;

import com.humanconsulting.humancore_api.domain.entities.Checkpoint;
import com.humanconsulting.humancore_api.domain.entities.Empresa;
import com.humanconsulting.humancore_api.domain.entities.Projeto;
import com.humanconsulting.humancore_api.domain.entities.Sprint;
import com.humanconsulting.humancore_api.domain.entities.Tarefa;
import com.humanconsulting.humancore_api.domain.repositories.CheckpointRepository;
import com.humanconsulting.humancore_api.web.dtos.response.tarefa.TarefaLoginResponseDto;
import com.humanconsulting.humancore_api.web.dtos.response.tarefa.TarefaResponseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TarefaResponseMapperTest {

    private CheckpointRepository checkpointRepository;
    private TarefaResponseMapper mapper;

    @BeforeEach
    void setup() {
        checkpointRepository = mock(CheckpointRepository.class);
        mapper = new TarefaResponseMapper(checkpointRepository);
    }

    @Test
    void testToResponse_Feliz() {
        // Arrange
        Tarefa tarefa = new Tarefa();
        tarefa.setIdTarefa(1);
        tarefa.setTitulo("Tarefa Teste");

        when(checkpointRepository.findAllByTarefa_IdTarefa(1))
                .thenReturn(List.of(mock(Checkpoint.class)));

        // Act
        TarefaResponseDto response = mapper.toResponse(tarefa);

        // Assert
        assertNotNull(response);
        assertEquals(1, response.getIdTarefa());
        assertEquals("Tarefa Teste", response.getTitulo());
        assertTrue(response.getProgresso() >= 0.0);
    }

    @Test
    void testToResponse_Triste_SemCheckpoints() {
        Tarefa tarefa = new Tarefa();
        tarefa.setIdTarefa(2);

        when(checkpointRepository.findAllByTarefa_IdTarefa(2))
                .thenReturn(Collections.emptyList());

        TarefaResponseDto response = mapper.toResponse(tarefa);

        assertNotNull(response);
        assertEquals(0.0, response.getProgresso()); // progresso calculado com lista vazia
        assertTrue(response.getCheckpoints().isEmpty());
    }

    @Test
    void testToLoginResponse_Feliz() {
        Empresa empresa = new Empresa();
        empresa.setIdEmpresa(1);
        empresa.setNome("Empresa Teste");

        Projeto projeto = new Projeto();
        projeto.setIdProjeto(10);
        projeto.setEmpresa(empresa);

        Sprint sprint = new Sprint();
        sprint.setIdSprint(100);
        sprint.setProjeto(projeto);

        Tarefa tarefa = new Tarefa();
        tarefa.setIdTarefa(200);
        tarefa.setTitulo("Tarefa Login");
        tarefa.setSprint(sprint);

        when(checkpointRepository.findAllByTarefa_IdTarefa(200))
                .thenReturn(List.of(mock(Checkpoint.class)));

        TarefaLoginResponseDto response = mapper.toLoginResponse(tarefa);

        assertNotNull(response);
        assertEquals("Tarefa Login", response.getTitulo());
        assertNotNull(response.getSprint());
        assertEquals(100, response.getSprint().getIdSprint());
        assertEquals(10, response.getSprint().getProjeto().getIdProjeto());
        assertEquals(1, response.getSprint().getProjeto().getEmpresa().getIdEmpresa());
    }

    @Test
    void testToLoginResponse_Triste_SprintNulo() {
        Tarefa tarefa = new Tarefa();
        tarefa.setIdTarefa(300);
        tarefa.setSprint(null); // Sprint ausente

        when(checkpointRepository.findAllByTarefa_IdTarefa(300))
                .thenReturn(Collections.emptyList());

        // Esperado: NullPointerException porque o código acessa tarefa.getSprint().getProjeto()
        assertThrows(NullPointerException.class, () -> mapper.toLoginResponse(tarefa));
    }
}
