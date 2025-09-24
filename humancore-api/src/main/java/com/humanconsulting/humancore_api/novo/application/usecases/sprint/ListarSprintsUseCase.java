package com.humanconsulting.humancore_api.novo.application.usecases.sprint;

import com.humanconsulting.humancore_api.novo.domain.entities.Sprint;
import com.humanconsulting.humancore_api.novo.domain.exception.EntidadeSemRetornoException;
import com.humanconsulting.humancore_api.novo.domain.repositories.SprintRepository;

import java.util.List;

public class ListarSprintsUseCase {
    private final SprintRepository sprintRepository;

    public ListarSprintsUseCase(SprintRepository sprintRepository) {
        this.sprintRepository = sprintRepository;
    }

    public List<Sprint> execute() {
        List<Sprint> all = sprintRepository.findAll();
        if (all.isEmpty()) throw new EntidadeSemRetornoException("Nenhuma sprint registrada");
        return all;
    }
}

