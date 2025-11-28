package com.humanconsulting.humancore_api.application.usecases.projeto;

import com.humanconsulting.humancore_api.application.usecases.sprint.BuscarSprintsPorProjetoUseCase;
import com.humanconsulting.humancore_api.application.usecases.sprint.CriarBurndownSprintUseCase;
import com.humanconsulting.humancore_api.web.dtos.response.projeto.ProjetoBurndownResponseDto;
import com.humanconsulting.humancore_api.web.dtos.response.sprint.SprintBurndownResponseDto;
import com.humanconsulting.humancore_api.web.dtos.response.sprint.SprintResponseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CriarBurndownProjetoUseCaseTest {

    private CriarBurndownSprintUseCase criarBurndownSprintUseCase;
    private BuscarSprintsPorProjetoUseCase buscarSprintsPorProjetoUseCase;
    private CriarBurndownProjetoUseCase criarBurndownProjetoUseCase;

    @BeforeEach
    void setUp() {
        criarBurndownSprintUseCase = mock(CriarBurndownSprintUseCase.class);
        buscarSprintsPorProjetoUseCase = mock(BuscarSprintsPorProjetoUseCase.class);
        criarBurndownProjetoUseCase = new CriarBurndownProjetoUseCase(criarBurndownSprintUseCase, buscarSprintsPorProjetoUseCase);
    }

    @Test
    void execute_ShouldReturnBurndown_WhenProjetoHasSprints() {
        // Arrange
        Integer idProjeto = 1;

        SprintResponseDto sprint1 = new SprintResponseDto();
        sprint1.setIdSprint(100);

        SprintResponseDto sprint2 = new SprintResponseDto();
        sprint2.setIdSprint(200);

        SprintBurndownResponseDto burndown1 = new SprintBurndownResponseDto();
        burndown1.setIdSprint(100);

        SprintBurndownResponseDto burndown2 = new SprintBurndownResponseDto();
        burndown2.setIdSprint(200);

        when(buscarSprintsPorProjetoUseCase.execute(idProjeto)).thenReturn(List.of(sprint1, sprint2));
        when(criarBurndownSprintUseCase.execute(100)).thenReturn(burndown1);
        when(criarBurndownSprintUseCase.execute(200)).thenReturn(burndown2);

        // Act
        ProjetoBurndownResponseDto result = criarBurndownProjetoUseCase.execute(idProjeto);

        // Assert
        assertNotNull(result);
        assertEquals(idProjeto, result.getIdProjeto());
        verify(buscarSprintsPorProjetoUseCase, times(1)).execute(idProjeto);
        verify(criarBurndownSprintUseCase, times(1)).execute(100);
        verify(criarBurndownSprintUseCase, times(1)).execute(200);
    }

    @Test
    void execute_ShouldReturnEmptyBurndown_WhenProjetoHasNoSprints() {
        // Arrange
        Integer idProjeto = 2;
        when(buscarSprintsPorProjetoUseCase.execute(idProjeto)).thenReturn(List.of());

        // Act
        ProjetoBurndownResponseDto result = criarBurndownProjetoUseCase.execute(idProjeto);

        // Assert
        assertNotNull(result);
        assertEquals(idProjeto, result.getIdProjeto());

        verify(buscarSprintsPorProjetoUseCase, times(1)).execute(idProjeto);
        verify(criarBurndownSprintUseCase, never()).execute(anyInt());
    }
}
