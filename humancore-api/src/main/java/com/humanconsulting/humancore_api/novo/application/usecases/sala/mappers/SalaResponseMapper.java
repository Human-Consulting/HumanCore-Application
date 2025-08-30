package com.humanconsulting.humancore_api.novo.application.usecases.sala.mappers;

import com.humanconsulting.humancore_api.novo.domain.entities.Sala;
import com.humanconsulting.humancore_api.novo.web.dtos.response.sala.SalaResponseDto;

public class SalaResponseMapper {
    public SalaResponseDto toResponse(Sala sala) {
        return com.humanconsulting.humancore_api.velho.mapper.SalaMapper.toDto(sala);
    }
}

