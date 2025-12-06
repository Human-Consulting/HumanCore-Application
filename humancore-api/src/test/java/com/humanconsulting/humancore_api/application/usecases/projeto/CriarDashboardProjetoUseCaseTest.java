package com.humanconsulting.humancore_api.application.usecases.projeto;

import com.humanconsulting.humancore_api.application.usecases.projeto.mappers.ProjetoResponseMapper;
import com.humanconsulting.humancore_api.domain.entities.Projeto;
import com.humanconsulting.humancore_api.domain.exception.EntidadeSemRetornoException;
import com.humanconsulting.humancore_api.web.dtos.response.projeto.DashboardProjetoResponseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CriarDashboardProjetoUseCaseTest {

    private ProjetoResponseMapper projetoResponseMapper;
    private BuscarProjetoPorIdUseCase buscarProjetoPorIdUseCase;
    private CriarDashboardProjetoUseCase criarDashboardProjetoUseCase;

    @BeforeEach
    void setUp() {
        projetoResponseMapper = mock(ProjetoResponseMapper.class);
        buscarProjetoPorIdUseCase = mock(BuscarProjetoPorIdUseCase.class);
        criarDashboardProjetoUseCase = new CriarDashboardProjetoUseCase(projetoResponseMapper, buscarProjetoPorIdUseCase);
    }

    @Test
    void execute_ShouldReturnDashboard_WhenProjetoExists() {
        // Arrange
        Integer idProjeto = 1;
        Projeto projeto = new Projeto();
        projeto.setIdProjeto(idProjeto);
        projeto.setDescricao("Projeto Teste");

        DashboardProjetoResponseDto dashboardDto = new DashboardProjetoResponseDto();
        dashboardDto.setIdProjeto(idProjeto);

        when(buscarProjetoPorIdUseCase.execute(idProjeto)).thenReturn(projeto);
        when(projetoResponseMapper.toResponseDashboard(projeto)).thenReturn(dashboardDto);

        // Act
        DashboardProjetoResponseDto result = criarDashboardProjetoUseCase.execute(idProjeto);

        // Assert
        assertNotNull(result);
        assertEquals(idProjeto, result.getIdProjeto());
        verify(buscarProjetoPorIdUseCase, times(1)).execute(idProjeto);
        verify(projetoResponseMapper, times(1)).toResponseDashboard(projeto);
    }

    @Test
    void execute_ShouldThrowException_WhenProjetoNotFound() {
        // Arrange
        Integer idProjeto = 99;
        when(buscarProjetoPorIdUseCase.execute(idProjeto))
                .thenThrow(new EntidadeSemRetornoException("Nenhum projeto encontrado."));

        // Act & Assert
        EntidadeSemRetornoException exception = assertThrows(
                EntidadeSemRetornoException.class,
                () -> criarDashboardProjetoUseCase.execute(idProjeto)
        );

        assertEquals("Nenhum projeto encontrado.", exception.getMessage());
        verify(buscarProjetoPorIdUseCase, times(1)).execute(idProjeto);
        verify(projetoResponseMapper, never()).toResponseDashboard(any());
    }
}
