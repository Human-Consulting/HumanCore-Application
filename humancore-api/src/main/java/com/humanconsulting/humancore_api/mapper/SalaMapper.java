package com.humanconsulting.humancore_api.mapper;

import com.humanconsulting.humancore_api.controller.dto.atualizar.sala.SalaAtualizarRequestDto;
import com.humanconsulting.humancore_api.controller.dto.request.SalaRequestDto;
import com.humanconsulting.humancore_api.controller.dto.response.sala.SalaResponseDto;
import com.humanconsulting.humancore_api.model.Sala;
import com.humanconsulting.humancore_api.model.Usuario;

public class SalaMapper {
    public static Sala toEntity(SalaRequestDto salaRequestDto) {
        return Sala.builder()
                .nome(salaRequestDto.getNome())
                .build();
    }
    public static Sala toEntity(SalaAtualizarRequestDto salaAtualizarRequestDto, Integer idSala) {
        return Sala.builder()
                .idSala(idSala)
                .nome(salaAtualizarRequestDto.getNome())
                .build();
    }

    public static SalaResponseDto toDto(Sala sala) {
        return SalaResponseDto.builder()
                .idSala(sala.getIdSala())
                .nome(sala.getNome())
                .build();
    }
}
