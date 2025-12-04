package com.humanconsulting.humancore_api.application.usecases.sprint;

import com.humanconsulting.humancore_api.web.dtos.response.sprint.SprintBurndownResponseDto;
import com.humanconsulting.humancore_api.web.dtos.response.sprint.SprintResponseDto;
import com.humanconsulting.humancore_api.web.dtos.response.tarefa.TarefaResponseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CriarBurndownSprintUseCaseTest {

    private BuscarSprintPorIdUseCase buscarSprintPorIdUseCase;
    private CriarBurndownSprintUseCase criarBurndownSprintUseCase;

    @BeforeEach
    void setUp() {
        buscarSprintPorIdUseCase = mock(BuscarSprintPorIdUseCase.class);
        criarBurndownSprintUseCase = new CriarBurndownSprintUseCase(buscarSprintPorIdUseCase);
    }

    @Test
    void execute_ShouldGenerateBurndown_WhenTarefasConcluidasEmDiasDiferentes() {
        // Arrange
        Integer idSprint = 1;
        LocalDate inicio = LocalDate.of(2025, 1, 1);
        LocalDate fim = LocalDate.of(2025, 1, 3);

        TarefaResponseDto tarefa1 = new TarefaResponseDto();
        tarefa1.setProgresso(100.0);
        tarefa1.setDtFim(LocalDate.of(2025, 1, 1));

        TarefaResponseDto tarefa2 = new TarefaResponseDto();
        tarefa2.setProgresso(100.0);
        tarefa2.setDtFim(LocalDate.of(2025, 1, 2));

        SprintResponseDto sprint = new SprintResponseDto();
        sprint.setIdSprint(idSprint);
        sprint.setTitulo("Sprint Teste");
        sprint.setDtInicio(inicio);
        sprint.setDtFim(fim);
        sprint.setTarefas(List.of(tarefa1, tarefa2));

        when(buscarSprintPorIdUseCase.execute(idSprint)).thenReturn(sprint);

        // Act
        SprintBurndownResponseDto result = criarBurndownSprintUseCase.execute(idSprint);

        // Assert
        assertNotNull(result);
        assertEquals(3, result.getBurndown().size()); // dias 1, 2, 3
    }

    @Test
    void execute_ShouldHandleSprintSemTarefas() {
        Integer idSprint = 2;
        LocalDate inicio = LocalDate.of(2025, 1, 1);
        LocalDate fim = LocalDate.of(2025, 1, 2);

        SprintResponseDto sprint = new SprintResponseDto();
        sprint.setIdSprint(idSprint);
        sprint.setTitulo("Sprint Vazia");
        sprint.setDtInicio(inicio);
        sprint.setDtFim(fim);
        sprint.setTarefas(List.of());

        when(buscarSprintPorIdUseCase.execute(idSprint)).thenReturn(sprint);

        SprintBurndownResponseDto result = criarBurndownSprintUseCase.execute(idSprint);

        assertEquals(2, result.getBurndown().size());
    }

    @Test
    void execute_ShouldIgnoreTarefasConcluidasAposFimSprint() {
        Integer idSprint = 3;
        LocalDate inicio = LocalDate.of(2025, 1, 1);
        LocalDate fim = LocalDate.of(2025, 1, 2);

        TarefaResponseDto tarefa = new TarefaResponseDto();
        tarefa.setProgresso(100.0);
        tarefa.setDtFim(LocalDate.of(2025, 1, 5)); // fora do intervalo

        SprintResponseDto sprint = new SprintResponseDto();
        sprint.setIdSprint(idSprint);
        sprint.setTitulo("Sprint Fora");
        sprint.setDtInicio(inicio);
        sprint.setDtFim(fim);
        sprint.setTarefas(List.of(tarefa));

        when(buscarSprintPorIdUseCase.execute(idSprint)).thenReturn(sprint);

        SprintBurndownResponseDto result = criarBurndownSprintUseCase.execute(idSprint);

        assertEquals(2, result.getBurndown().size());
    }

    @Test
    void execute_ShouldIgnoreTarefasNaoConcluidasOuSemDataFim() {
        Integer idSprint = 4;
        LocalDate inicio = LocalDate.of(2025, 1, 1);
        LocalDate fim = LocalDate.of(2025, 1, 1);

        TarefaResponseDto tarefa1 = new TarefaResponseDto();
        tarefa1.setProgresso(50.0); // não concluída

        TarefaResponseDto tarefa2 = new TarefaResponseDto();
        tarefa2.setProgresso(100.0);
        tarefa2.setDtFim(null); // sem data fim

        SprintResponseDto sprint = new SprintResponseDto();
        sprint.setIdSprint(idSprint);
        sprint.setTitulo("Sprint Incompleta");
        sprint.setDtInicio(inicio);
        sprint.setDtFim(fim);
        sprint.setTarefas(List.of(tarefa1, tarefa2));

        when(buscarSprintPorIdUseCase.execute(idSprint)).thenReturn(sprint);

        SprintBurndownResponseDto result = criarBurndownSprintUseCase.execute(idSprint);

        assertEquals(1, result.getBurndown().size());
    }
}
