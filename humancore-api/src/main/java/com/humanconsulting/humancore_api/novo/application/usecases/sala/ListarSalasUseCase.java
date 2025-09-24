package com.humanconsulting.humancore_api.novo.application.usecases.sala;

import com.humanconsulting.humancore_api.novo.application.usecases.sala.mappers.SalaResponseMapper;
import com.humanconsulting.humancore_api.novo.domain.entities.Sala;
import com.humanconsulting.humancore_api.novo.domain.exception.EntidadeSemRetornoException;
import com.humanconsulting.humancore_api.novo.domain.repositories.SalaRepository;
import com.humanconsulting.humancore_api.novo.web.dtos.response.sala.SalaResponseDto;

import java.util.ArrayList;
import java.util.List;

public class ListarSalasUseCase {
    private final SalaRepository salaRepository;
    private final SalaResponseMapper salaResponseMapper;

    public ListarSalasUseCase(SalaRepository salaRepository, SalaResponseMapper salaResponseMapper) {
        this.salaRepository = salaRepository;
        this.salaResponseMapper = salaResponseMapper;
    }

    public List<SalaResponseDto> execute() {
        List<Sala> all = salaRepository.findAll();
        if (all.isEmpty()) throw new EntidadeSemRetornoException("Nenhuma sala registrada");
        List<SalaResponseDto> allResponses = new ArrayList<>();
        for (Sala sala : all) {
            allResponses.add(salaResponseMapper.toResponse(sala));
        }
        return allResponses;
    }
}

