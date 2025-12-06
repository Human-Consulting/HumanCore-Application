package com.humanconsulting.humancore_api.application.usecases.sprint;

import com.humanconsulting.humancore_api.application.usecases.sprint.mappers.SprintResponseMapper;
import com.humanconsulting.humancore_api.domain.entities.Sprint;
import com.humanconsulting.humancore_api.domain.exception.EntidadeNaoEncontradaException;
import com.humanconsulting.humancore_api.domain.repositories.CheckpointRepository;
import com.humanconsulting.humancore_api.domain.repositories.SprintRepository;
import com.humanconsulting.humancore_api.domain.repositories.TarefaRepository;
import com.humanconsulting.humancore_api.web.dtos.response.sprint.SprintResponseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BuscarSprintPorIdUseCaseTest {

    private SprintRepository sprintRepository;
    private TarefaRepository tarefaRepository;
    private CheckpointRepository checkpointRepository;
    private SprintResponseMapper sprintResponseMapper;
    private BuscarSprintPorIdUseCase buscarSprintPorIdUseCase;

    @BeforeEach
    void setUp() {
        sprintRepository = mock(SprintRepository.class);
        tarefaRepository = mock(TarefaRepository.class);
        checkpointRepository = mock(CheckpointRepository.class);
        sprintResponseMapper = mock(SprintResponseMapper.class);
        buscarSprintPorIdUseCase = new BuscarSprintPorIdUseCase(
                sprintRepository, tarefaRepository, checkpointRepository, sprintResponseMapper
        );
    }

    @Test
    void execute_ShouldReturnSprintResponseDto_WhenSprintExists() {
        // Arrange
        Integer idSprint = 1;
        Sprint sprint = new Sprint();
        sprint.setIdSprint(idSprint);

        SprintResponseDto responseDto = new SprintResponseDto();
        responseDto.setIdSprint(idSprint);

        when(sprintRepository.findById(idSprint)).thenReturn(Optional.of(sprint));
        when(sprintResponseMapper.toResponse(sprint)).thenReturn(responseDto);

        // Act
        SprintResponseDto result = buscarSprintPorIdUseCase.execute(idSprint);

        // Assert
        assertNotNull(result);
        assertEquals(idSprint, result.getIdSprint());
        verify(sprintRepository, times(1)).findById(idSprint);
        verify(sprintResponseMapper, times(1)).toResponse(sprint);
    }

    @Test
    void execute_ShouldThrowException_WhenSprintNotFound() {
        // Arrange
        Integer idSprint = 99;
        when(sprintRepository.findById(idSprint)).thenReturn(Optional.empty());

        // Act & Assert
        EntidadeNaoEncontradaException exception = assertThrows(
                EntidadeNaoEncontradaException.class,
                () -> buscarSprintPorIdUseCase.execute(idSprint)
        );

        assertEquals("SprintEntity não encontrada.", exception.getMessage());
        verify(sprintRepository, times(1)).findById(idSprint);
        verify(sprintResponseMapper, never()).toResponse(any());
    }
}
