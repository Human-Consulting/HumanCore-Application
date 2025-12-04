package com.humanconsulting.humancore_api.application.usecases.tarefa;

import com.humanconsulting.humancore_api.domain.entities.Tarefa;
import com.humanconsulting.humancore_api.domain.exception.EntidadeNaoEncontradaException;
import com.humanconsulting.humancore_api.domain.repositories.TarefaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BuscarTarefaPorIdUseCaseTest {

    private TarefaRepository tarefaRepository;
    private BuscarTarefaPorIdUseCase buscarTarefaPorIdUseCase;

    @BeforeEach
    void setUp() {
        tarefaRepository = mock(TarefaRepository.class);
        buscarTarefaPorIdUseCase = new BuscarTarefaPorIdUseCase(tarefaRepository);
    }

    @Test
    void execute_ShouldReturnTarefa_WhenTarefaExists() {
        // Arrange
        Integer idTarefa = 1;
        Tarefa tarefa = new Tarefa();
        tarefa.setIdTarefa(idTarefa);

        when(tarefaRepository.findById(idTarefa)).thenReturn(Optional.of(tarefa));

        // Act
        Tarefa result = buscarTarefaPorIdUseCase.execute(idTarefa);

        // Assert
        assertNotNull(result);
        assertEquals(idTarefa, result.getIdTarefa());
        verify(tarefaRepository, times(1)).findById(idTarefa);
    }

    @Test
    void execute_ShouldThrowException_WhenTarefaNotFound() {
        // Arrange
        Integer idTarefa = 99;
        when(tarefaRepository.findById(idTarefa)).thenReturn(Optional.empty());

        // Act & Assert
        EntidadeNaoEncontradaException exception = assertThrows(
                EntidadeNaoEncontradaException.class,
                () -> buscarTarefaPorIdUseCase.execute(idTarefa)
        );

        assertEquals("TarefaEntity não encontrada", exception.getMessage());
        verify(tarefaRepository, times(1)).findById(idTarefa);
    }
}
