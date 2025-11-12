package com.humanconsulting.humancore_api.application.usecases.sala;

import com.humanconsulting.humancore_api.domain.entities.Sala;
import com.humanconsulting.humancore_api.domain.exception.EntidadeNaoEncontradaException;
import com.humanconsulting.humancore_api.domain.repositories.SalaRepository;
import com.humanconsulting.humancore_api.web.dtos.response.sala.SalaResponseDto;
import com.humanconsulting.humancore_api.web.mappers.SalaMapper;

import java.util.Optional;

public class BuscarSalaPorIdUseCase {
    private final SalaRepository salaRepository;

    public BuscarSalaPorIdUseCase(SalaRepository salaRepository) {
        this.salaRepository = salaRepository;
    }

    public SalaResponseDto execute(Integer id) {
        Optional<Sala> optSala = salaRepository.findById(id);
        if (optSala.isEmpty()) throw new EntidadeNaoEncontradaException("SalaEntity não encontrada.");
        return SalaMapper.toDto(optSala.get());
    }
}

