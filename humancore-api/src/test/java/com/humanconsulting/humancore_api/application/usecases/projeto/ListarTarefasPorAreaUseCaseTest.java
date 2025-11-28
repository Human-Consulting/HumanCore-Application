package com.humanconsulting.humancore_api.application.usecases.projeto;

import com.humanconsulting.humancore_api.domain.entities.Area;
import com.humanconsulting.humancore_api.domain.repositories.DashboardProjetoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ListarTarefasPorAreaUseCaseTest {

    private DashboardProjetoRepository dashboardProjetoRepository;
    private ListarTarefasPorAreaUseCase listarTarefasPorAreaUseCase;

    @BeforeEach
    void setUp() {
        dashboardProjetoRepository = mock(DashboardProjetoRepository.class);
        listarTarefasPorAreaUseCase = new ListarTarefasPorAreaUseCase(dashboardProjetoRepository);
    }

    @Test
    void execute_ShouldReturnAreas_WhenRepositoryReturnsData() {
        // Arrange
        Integer idProjeto = 1;
        Object[] area1 = {"Desenvolvimento", 5};
        Object[] area2 = {"Design", 3};

        when(dashboardProjetoRepository.buscarTarefasPorArea(idProjeto))
                .thenReturn(List.of(area1, area2));

        // Act
        List<Area> result = listarTarefasPorAreaUseCase.execute(idProjeto);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Desenvolvimento", result.get(0).getNome());
        assertEquals("Design", result.get(1).getNome());

        verify(dashboardProjetoRepository, times(1)).buscarTarefasPorArea(idProjeto);
    }

    @Test
    void execute_ShouldReturnEmptyList_WhenRepositoryReturnsEmpty() {
        // Arrange
        Integer idProjeto = 2;
        when(dashboardProjetoRepository.buscarTarefasPorArea(idProjeto)).thenReturn(List.of());

        // Act
        List<Area> result = listarTarefasPorAreaUseCase.execute(idProjeto);

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(dashboardProjetoRepository, times(1)).buscarTarefasPorArea(idProjeto);
    }
}
