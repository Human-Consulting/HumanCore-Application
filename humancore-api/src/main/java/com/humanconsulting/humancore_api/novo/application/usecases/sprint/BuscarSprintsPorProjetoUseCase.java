package com.humanconsulting.humancore_api.novo.application.usecases.sprint;

import com.humanconsulting.humancore_api.novo.application.usecases.sprint.mappers.SprintResponseMapper;
import com.humanconsulting.humancore_api.novo.domain.entities.Sprint;
import com.humanconsulting.humancore_api.novo.domain.exception.EntidadeSemRetornoException;
import com.humanconsulting.humancore_api.novo.domain.repositories.SprintRepository;
import com.humanconsulting.humancore_api.novo.web.dtos.response.sprint.SprintResponseDto;

import java.util.ArrayList;
import java.util.List;

public class BuscarSprintsPorProjetoUseCase {
    private final SprintRepository sprintRepository;
    private final SprintResponseMapper sprintResponseMapper;

    public BuscarSprintsPorProjetoUseCase(SprintRepository sprintRepository, SprintResponseMapper sprintResponseMapper) {
        this.sprintRepository = sprintRepository;
        this.sprintResponseMapper = sprintResponseMapper;
    }

    public List<SprintResponseDto> execute(Integer idProjeto) {
        List<Sprint> all = sprintRepository.findByProjeto_IdProjeto(idProjeto);
        if (all.isEmpty()) throw new EntidadeSemRetornoException("Nenhum projeto encontrado");
        List<SprintResponseDto> allResponse = new ArrayList<>();
        for (Sprint sprint : all) {
            allResponse.add(sprintResponseMapper.toResponse(sprint, sprint.getIdSprint()));
        }
        return allResponse;
    }
}

