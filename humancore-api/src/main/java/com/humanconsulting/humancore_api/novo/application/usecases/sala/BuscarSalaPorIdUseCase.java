package com.humanconsulting.humancore_api.novo.application.usecases.sala;

import com.humanconsulting.humancore_api.novo.domain.entities.Sala;
import com.humanconsulting.humancore_api.novo.domain.exception.EntidadeNaoEncontradaException;
import com.humanconsulting.humancore_api.novo.domain.repositories.SalaRepository;

import java.util.Optional;

public class BuscarSalaPorIdUseCase {
    private final SalaRepository salaRepository;

    public BuscarSalaPorIdUseCase(SalaRepository salaRepository) {
        this.salaRepository = salaRepository;
    }

    public Sala execute(Integer id) {
        Optional<Sala> optSala = salaRepository.findById(id);
        if (optSala.isEmpty()) throw new EntidadeNaoEncontradaException("SalaEntity não encontrada.");
        return optSala.get();
    }
}

