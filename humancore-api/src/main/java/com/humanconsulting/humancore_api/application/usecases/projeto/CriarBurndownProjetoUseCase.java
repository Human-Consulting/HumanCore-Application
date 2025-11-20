package com.humanconsulting.humancore_api.application.usecases.projeto;

import com.humanconsulting.humancore_api.application.usecases.sprint.BuscarSprintsPorProjetoUseCase;
import com.humanconsulting.humancore_api.application.usecases.sprint.CriarBurndownSprintUseCase;
import com.humanconsulting.humancore_api.web.dtos.response.projeto.ProjetoBurndownResponseDto;
import com.humanconsulting.humancore_api.web.dtos.response.sprint.SprintBurndownResponseDto;
import com.humanconsulting.humancore_api.web.dtos.response.sprint.SprintResponseDto;

import java.util.ArrayList;
import java.util.List;

public class CriarBurndownProjetoUseCase {
    private final CriarBurndownSprintUseCase criarBurndownSprintUseCase;
    private final BuscarSprintsPorProjetoUseCase buscarSprintsPorProjetoUseCase;

    public CriarBurndownProjetoUseCase(CriarBurndownSprintUseCase criarBurndownSprintuseCase, BuscarSprintsPorProjetoUseCase buscarSprintsPorProjetoUseCase) {
        this.criarBurndownSprintUseCase = criarBurndownSprintuseCase;
        this.buscarSprintsPorProjetoUseCase = buscarSprintsPorProjetoUseCase;
    }

    public ProjetoBurndownResponseDto execute(Integer idProjeto) {
        List<SprintResponseDto> sprintResponse = buscarSprintsPorProjetoUseCase.execute(idProjeto);

        List<SprintBurndownResponseDto> burndowns = new ArrayList<>();

        sprintResponse.forEach(sprint -> {
            SprintBurndownResponseDto sprintBurndown =  criarBurndownSprintUseCase.execute(sprint.getIdSprint());
            burndowns.add(sprintBurndown);
        });

        return new ProjetoBurndownResponseDto(idProjeto, burndowns);
    }
}
