package com.humanconsulting.humancore_api.application.usecases.empresa;

import com.humanconsulting.humancore_api.domain.entities.Area;
import com.humanconsulting.humancore_api.domain.repositories.DashboardEmpresaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ListarTarefasPorAreaUseCaseTest {

    private DashboardEmpresaRepository dashRepository;
    private ListarTarefasPorAreaUseCase useCase;

    @BeforeEach
    void setUp() {
        dashRepository = mock(DashboardEmpresaRepository.class);
        useCase = new ListarTarefasPorAreaUseCase(dashRepository);
    }

    @Test
    void deveRetornarListaDeAreasQuandoEncontradas() {
        // Arrange
        Object[] area1 = {"Financeiro", 5};
        Object[] area2 = {"RH", 3};

        when(dashRepository.buscarTarefasPorArea(1))
                .thenReturn(List.of(area1, area2));

        // Act
        List<Area> result = useCase.execute(1);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Financeiro", result.get(0).getNome());
        assertEquals("RH", result.get(1).getNome());
        verify(dashRepository).buscarTarefasPorArea(1);
    }

    @Test
    void deveRetornarListaVaziaQuandoNenhumaAreaEncontrada() {
        // Arrange
        when(dashRepository.buscarTarefasPorArea(99))
                .thenReturn(List.of());

        // Act
        List<Area> result = useCase.execute(99);

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(dashRepository).buscarTarefasPorArea(99);
    }
}
