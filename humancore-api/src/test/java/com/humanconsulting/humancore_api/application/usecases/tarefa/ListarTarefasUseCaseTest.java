package com.humanconsulting.humancore_api.application.usecases.tarefa;

import com.humanconsulting.humancore_api.application.usecases.tarefa.mappers.TarefaResponseMapper;
import com.humanconsulting.humancore_api.domain.entities.Tarefa;
import com.humanconsulting.humancore_api.domain.exception.EntidadeSemRetornoException;
import com.humanconsulting.humancore_api.domain.repositories.TarefaRepository;
import com.humanconsulting.humancore_api.web.dtos.response.tarefa.TarefaResponseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ListarTarefasUseCaseTest {

    private TarefaRepository tarefaRepository;
    private TarefaResponseMapper tarefaResponseMapper;
    private ListarTarefasUseCase listarTarefasUseCase;

    @BeforeEach
    void setUp() {
        tarefaRepository = mock(TarefaRepository.class);
        tarefaResponseMapper = mock(TarefaResponseMapper.class);
        listarTarefasUseCase = new ListarTarefasUseCase(tarefaRepository, tarefaResponseMapper);
    }

    @Test
    void execute_ShouldReturnTarefaResponseDtos_WhenTarefasExistem() {
        // Arrange
        Tarefa tarefa1 = new Tarefa();
        tarefa1.setIdTarefa(10);
        Tarefa tarefa2 = new Tarefa();
        tarefa2.setIdTarefa(20);

        TarefaResponseDto dto1 = new TarefaResponseDto();
        dto1.setIdTarefa(10);
        TarefaResponseDto dto2 = new TarefaResponseDto();
        dto2.setIdTarefa(20);

        when(tarefaRepository.findAll()).thenReturn(List.of(tarefa1, tarefa2));
        when(tarefaResponseMapper.toResponse(tarefa1)).thenReturn(dto1);
        when(tarefaResponseMapper.toResponse(tarefa2)).thenReturn(dto2);

        // Act
        List<TarefaResponseDto> result = listarTarefasUseCase.execute();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(10, result.get(0).getIdTarefa());
        assertEquals(20, result.get(1).getIdTarefa());

        verify(tarefaRepository, times(1)).findAll();
        verify(tarefaResponseMapper, times(2)).toResponse(any(Tarefa.class));
    }

    @Test
    void execute_ShouldThrowException_WhenNoTarefasExistem() {
        // Arrange
        when(tarefaRepository.findAll()).thenReturn(List.of());

        // Act & Assert
        EntidadeSemRetornoException exception = assertThrows(
                EntidadeSemRetornoException.class,
                () -> listarTarefasUseCase.execute()
        );

        assertEquals("Nenhuma tarefa registrada", exception.getMessage());
        verify(tarefaRepository, times(1)).findAll();
        verify(tarefaResponseMapper, never()).toResponse(any());
    }
}
