package com.humanconsulting.humancore_api.novo.application.usecases.sprint;

import com.humanconsulting.humancore_api.novo.domain.entities.Sprint;
import com.humanconsulting.humancore_api.novo.domain.repositories.SprintRepository;

import java.util.Optional;

public class BuscarSprintPorIdUseCase {
    private final SprintRepository sprintRepository;

    public BuscarSprintPorIdUseCase(SprintRepository sprintRepository) {
        this.sprintRepository = sprintRepository;
    }

    public Sprint execute(Integer id) {
        Optional<Sprint> optSprint = sprintRepository.findById(id);
        if (optSprint.isEmpty()) throw new EntidadeNaoEncontradaException("SprintEntity não encontrada.");
        return optSprint.get();
    }
}

