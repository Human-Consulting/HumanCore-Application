package com.humanconsulting.humancore_api.application.usecases.sprint;

import com.humanconsulting.humancore_api.application.usecases.sprint.mappers.SprintResponseMapper;
import com.humanconsulting.humancore_api.domain.entities.Sprint;
import com.humanconsulting.humancore_api.domain.exception.EntidadeSemRetornoException;
import com.humanconsulting.humancore_api.domain.repositories.SprintRepository;
import com.humanconsulting.humancore_api.web.dtos.response.sprint.SprintResponseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BuscarSprintsPorProjetoUseCaseTest {

    private SprintRepository sprintRepository;
    private SprintResponseMapper sprintResponseMapper;
    private BuscarSprintsPorProjetoUseCase buscarSprintsPorProjetoUseCase;

    @BeforeEach
    void setUp() {
        sprintRepository = mock(SprintRepository.class);
        sprintResponseMapper = mock(SprintResponseMapper.class);
        buscarSprintsPorProjetoUseCase = new BuscarSprintsPorProjetoUseCase(sprintRepository, sprintResponseMapper);
    }

    @Test
    void execute_ShouldReturnSprintResponseDtos_WhenSprintsExistem() {
        // Arrange
        Integer idProjeto = 1;
        Sprint sprint1 = new Sprint();
        sprint1.setIdSprint(10);
        Sprint sprint2 = new Sprint();
        sprint2.setIdSprint(20);

        SprintResponseDto dto1 = new SprintResponseDto();
        dto1.setIdSprint(10);
        SprintResponseDto dto2 = new SprintResponseDto();
        dto2.setIdSprint(20);

        when(sprintRepository.findByProjeto_IdProjeto(idProjeto)).thenReturn(List.of(sprint1, sprint2));
        when(sprintResponseMapper.toResponse(sprint1)).thenReturn(dto1);
        when(sprintResponseMapper.toResponse(sprint2)).thenReturn(dto2);

        // Act
        List<SprintResponseDto> result = buscarSprintsPorProjetoUseCase.execute(idProjeto);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(10, result.get(0).getIdSprint());
        assertEquals(20, result.get(1).getIdSprint());

        verify(sprintRepository, times(1)).findByProjeto_IdProjeto(idProjeto);
        verify(sprintResponseMapper, times(2)).toResponse(any(Sprint.class));
    }

    @Test
    void execute_ShouldThrowException_WhenNoSprintsExistem() {
        // Arrange
        Integer idProjeto = 99;
        when(sprintRepository.findByProjeto_IdProjeto(idProjeto)).thenReturn(List.of());

        // Act & Assert
        EntidadeSemRetornoException exception = assertThrows(
                EntidadeSemRetornoException.class,
                () -> buscarSprintsPorProjetoUseCase.execute(idProjeto)
        );

        assertEquals("Nenhum projeto encontrado", exception.getMessage());
        verify(sprintRepository, times(1)).findByProjeto_IdProjeto(idProjeto);
        verify(sprintResponseMapper, never()).toResponse(any());
    }
}
