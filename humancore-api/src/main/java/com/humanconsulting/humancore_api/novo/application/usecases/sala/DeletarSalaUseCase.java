package com.humanconsulting.humancore_api.novo.application.usecases.sala;

import com.humanconsulting.humancore_api.novo.domain.entities.Sala;
import com.humanconsulting.humancore_api.novo.domain.exception.EntidadeNaoEncontradaException;
import com.humanconsulting.humancore_api.novo.domain.repositories.SalaRepository;

import java.util.Optional;

public class DeletarSalaUseCase {
    private final SalaRepository salaRepository;

    public DeletarSalaUseCase(SalaRepository salaRepository) {
        this.salaRepository = salaRepository;
    }

    public void execute(Integer id) {
        Optional<Sala> optSala = salaRepository.findById(id);
        if (optSala.isEmpty()) throw new EntidadeNaoEncontradaException("SalaEntity não encontrada.");
        salaRepository.deleteById(id);
    }
}

