package com.humanconsulting.humancore_api.application.usecases.sala.mappers;

import com.humanconsulting.humancore_api.domain.entities.Sala;
import com.humanconsulting.humancore_api.web.dtos.response.sala.SalaResponseDto;
import com.humanconsulting.humancore_api.web.mappers.SalaMapper;

public class SalaResponseMapper {
    public SalaResponseDto toResponse(Sala sala) {
        return SalaMapper.toDto(sala);
    }
}

