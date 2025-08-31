package com.humanconsulting.humancore_api.novo.application.usecases.sala.mappers;

import com.humanconsulting.humancore_api.novo.domain.entities.Sala;
import com.humanconsulting.humancore_api.novo.web.dtos.response.sala.SalaResponseDto;
import com.humanconsulting.humancore_api.novo.web.mappers.SalaMapper;

public class SalaResponseMapper {
    public SalaResponseDto toResponse(Sala sala) {
        return SalaMapper.toDto(sala);
    }
}

