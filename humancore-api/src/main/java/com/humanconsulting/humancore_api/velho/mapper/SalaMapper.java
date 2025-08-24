package com.humanconsulting.humancore_api.velho.mapper;

import com.humanconsulting.humancore_api.velho.controller.dto.request.SalaRequestDto;
import com.humanconsulting.humancore_api.velho.controller.dto.response.sala.SalaResponseDto;
import com.humanconsulting.humancore_api.velho.model.Empresa;
import com.humanconsulting.humancore_api.velho.model.Projeto;
import com.humanconsulting.humancore_api.velho.model.Sala;
import com.humanconsulting.humancore_api.velho.model.Usuario;

import java.util.Set;

public class SalaMapper {
    public static Sala toEntity(SalaRequestDto salaRequestDto, Set<Usuario> usuarios) {
        return Sala.builder()
                .nome(salaRequestDto.getNome())
                .urlImagem(salaRequestDto.getUrlImagem())
                .usuarios(usuarios)
                .build();
    }
    public static Sala toEntity(SalaRequestDto salaRequestDto, Integer idSala, Set<Usuario> usuarios, Empresa empresa, Projeto projeto) {
        return Sala.builder()
                .idSala(idSala)
                .nome(salaRequestDto.getNome())
                .urlImagem(salaRequestDto.getUrlImagem())
                .usuarios(usuarios)
                .projeto(projeto)
                .build();
    }

    public static SalaResponseDto toDto(Sala sala) {
        return SalaResponseDto.builder()
                .idSala(sala.getIdSala())
                .nome(sala.getNome())
                .build();
    }
}
