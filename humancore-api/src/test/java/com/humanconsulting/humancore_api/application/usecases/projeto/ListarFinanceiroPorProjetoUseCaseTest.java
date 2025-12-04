package com.humanconsulting.humancore_api.application.usecases.projeto;

import com.humanconsulting.humancore_api.domain.entities.Investimento;
import com.humanconsulting.humancore_api.domain.repositories.DashboardProjetoRepository;
import com.humanconsulting.humancore_api.web.dtos.response.investimento.InvestimentoResponseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ListarFinanceiroPorProjetoUseCaseTest {

    private DashboardProjetoRepository dashboardProjetoRepository;
    private ListarFinanceiroPorProjetoUseCase listarFinanceiroPorProjetoUseCase;

    @BeforeEach
    void setUp() {
        dashboardProjetoRepository = mock(DashboardProjetoRepository.class);
        listarFinanceiroPorProjetoUseCase = new ListarFinanceiroPorProjetoUseCase(dashboardProjetoRepository);
    }

    @Test
    void execute_ShouldReturnInvestimentos_WhenProjetoHasFinanceiro() {
        // Arrange
        Integer idProjeto = 1;

        Investimento investimento1 = new Investimento();
        investimento1.setIdInvestimento(100);
        investimento1.setDescricao("Investimento A");
        investimento1.setValor(Double.valueOf(5000));

        Investimento investimento2 = new Investimento();
        investimento2.setIdInvestimento(200);
        investimento2.setDescricao("Investimento B");
        investimento2.setValor(Double.valueOf(10000));

        when(dashboardProjetoRepository.listarFinanceiroPorProjeto(idProjeto))
                .thenReturn(List.of(investimento1, investimento2));

        // Act
        List<InvestimentoResponseDto> result = listarFinanceiroPorProjetoUseCase.execute(idProjeto);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Investimento A", result.get(0).getDescricao());
        assertEquals(Double.valueOf(5000), result.get(0).getValor());
        assertEquals("Investimento B", result.get(1).getDescricao());
        assertEquals(Double.valueOf(10000), result.get(1).getValor());

        verify(dashboardProjetoRepository, times(1)).listarFinanceiroPorProjeto(idProjeto);
    }

    @Test
    void execute_ShouldReturnEmptyList_WhenProjetoHasNoFinanceiro() {
        // Arrange
        Integer idProjeto = 2;
        when(dashboardProjetoRepository.listarFinanceiroPorProjeto(idProjeto)).thenReturn(List.of());

        // Act
        List<InvestimentoResponseDto> result = listarFinanceiroPorProjetoUseCase.execute(idProjeto);

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(dashboardProjetoRepository, times(1)).listarFinanceiroPorProjeto(idProjeto);
    }
}
