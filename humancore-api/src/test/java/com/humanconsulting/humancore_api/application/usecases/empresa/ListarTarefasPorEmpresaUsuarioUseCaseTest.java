package com.humanconsulting.humancore_api.application.usecases.empresa;

import com.humanconsulting.humancore_api.domain.entities.TarefaUsuario;
import com.humanconsulting.humancore_api.domain.repositories.DashboardEmpresaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ListarTarefasPorEmpresaUsuarioUseCaseTest {

    private DashboardEmpresaRepository dashboardEmpresaRepository;
    private ListarTarefasPorEmpresaUsuarioUseCase useCase;

    @BeforeEach
    void setUp() {
        dashboardEmpresaRepository = mock(DashboardEmpresaRepository.class);
        useCase = new ListarTarefasPorEmpresaUsuarioUseCase(dashboardEmpresaRepository);
    }

    @Test
    void deveRetornarListaDeTarefasPorUsuarioQuandoEncontradas() {
        // Arrange
        Object[] tarefa1 = {"Pedro", 7};
        Object[] tarefa2 = {"Maria", 4};

        when(dashboardEmpresaRepository.buscarTarefasPorEmpresaUsuario(1))
                .thenReturn(List.of(tarefa1, tarefa2));

        // Act
        List<TarefaUsuario> result = useCase.execute(1);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(dashboardEmpresaRepository).buscarTarefasPorEmpresaUsuario(1);
    }

    @Test
    void deveRetornarListaVaziaQuandoNenhumaTarefaEncontrada() {
        // Arrange
        when(dashboardEmpresaRepository.buscarTarefasPorEmpresaUsuario(99))
                .thenReturn(List.of());

        // Act
        List<TarefaUsuario> result = useCase.execute(99);

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(dashboardEmpresaRepository).buscarTarefasPorEmpresaUsuario(99);
    }
}
