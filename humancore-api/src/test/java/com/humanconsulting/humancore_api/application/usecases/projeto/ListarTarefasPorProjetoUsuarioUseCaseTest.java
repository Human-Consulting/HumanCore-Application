package com.humanconsulting.humancore_api.application.usecases.projeto;

import com.humanconsulting.humancore_api.domain.entities.TarefaUsuario;
import com.humanconsulting.humancore_api.domain.repositories.DashboardProjetoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ListarTarefasPorProjetoUsuarioUseCaseTest {

    private DashboardProjetoRepository dashboardProjetoRepository;
    private ListarTarefasPorProjetoUsuarioUseCase listarTarefasPorProjetoUsuarioUseCase;

    @BeforeEach
    void setUp() {
        dashboardProjetoRepository = mock(DashboardProjetoRepository.class);
        listarTarefasPorProjetoUsuarioUseCase = new ListarTarefasPorProjetoUsuarioUseCase(dashboardProjetoRepository);
    }

    @Test
    void execute_ShouldReturnTarefasUsuario_WhenRepositoryReturnsData() {
        // Arrange
        Integer idProjeto = 1;
        Object[] tarefa1 = {"Pedro", 5};
        Object[] tarefa2 = {"Maria", 3};

        when(dashboardProjetoRepository.buscarTarefasPorProjetoUsuario(idProjeto))
                .thenReturn(List.of(tarefa1, tarefa2));

        // Act
        List<TarefaUsuario> result = listarTarefasPorProjetoUsuarioUseCase.execute(idProjeto);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());

        verify(dashboardProjetoRepository, times(1)).buscarTarefasPorProjetoUsuario(idProjeto);
    }

    @Test
    void execute_ShouldReturnEmptyList_WhenRepositoryReturnsEmpty() {
        // Arrange
        Integer idProjeto = 2;
        when(dashboardProjetoRepository.buscarTarefasPorProjetoUsuario(idProjeto)).thenReturn(List.of());

        // Act
        List<TarefaUsuario> result = listarTarefasPorProjetoUsuarioUseCase.execute(idProjeto);

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(dashboardProjetoRepository, times(1)).buscarTarefasPorProjetoUsuario(idProjeto);
    }
}
