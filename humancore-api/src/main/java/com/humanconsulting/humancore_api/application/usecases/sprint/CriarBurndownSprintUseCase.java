        package com.humanconsulting.humancore_api.application.usecases.sprint;

import com.humanconsulting.humancore_api.web.dtos.response.global.BurndownDTO;
import com.humanconsulting.humancore_api.web.dtos.response.sprint.SprintBurndownResponseDto;
import com.humanconsulting.humancore_api.web.dtos.response.sprint.SprintResponseDto;
import com.humanconsulting.humancore_api.web.dtos.response.tarefa.TarefaResponseDto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CriarBurndownSprintUseCase {
    private final BuscarSprintPorIdUseCase buscarSprintPorIdUseCase;

    public CriarBurndownSprintUseCase(BuscarSprintPorIdUseCase buscarSprintPorIdUseCase) {
        this.buscarSprintPorIdUseCase = buscarSprintPorIdUseCase;
    }

    public SprintBurndownResponseDto execute(Integer idSprint) {
        SprintResponseDto sprint = buscarSprintPorIdUseCase.execute(idSprint);
        List<TarefaResponseDto> tarefas = sprint.getTarefas();

        long totalTarefas = tarefas.size();
        LocalDate inicio = sprint.getDtInicio();
        LocalDate fim = sprint.getDtFim();

        List<BurndownDTO> burndown = new ArrayList<>();
        for (LocalDate dia = inicio; !dia.isAfter(fim); dia = dia.plusDays(1)) {
            LocalDate finalDia = dia;
            long concluidas = tarefas.stream()
                    .filter(t -> t.getProgresso() == 100 && t.getDtFim() != null && !t.getDtFim().isAfter(finalDia))
                    .count();
            burndown.add(new BurndownDTO(dia, totalTarefas, concluidas));
        }

        return new SprintBurndownResponseDto(sprint.getIdSprint(), sprint.getTitulo(), burndown);
    }
}
