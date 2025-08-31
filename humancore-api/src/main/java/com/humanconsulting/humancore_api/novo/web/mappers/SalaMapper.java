package com.humanconsulting.humancore_api.novo.web.mappers;

import com.humanconsulting.humancore_api.novo.domain.entities.Sala;
import com.humanconsulting.humancore_api.novo.domain.entities.Usuario;
import com.humanconsulting.humancore_api.novo.domain.entities.Empresa;
import com.humanconsulting.humancore_api.novo.domain.entities.Projeto;
import com.humanconsulting.humancore_api.novo.web.dtos.request.SalaRequestDto;
import com.humanconsulting.humancore_api.novo.web.dtos.response.sala.SalaResponseDto;

import java.util.Set;

public class SalaMapper {
    public static Sala toEntity(SalaRequestDto salaRequestDto, Set<Usuario> usuarios) {
        Sala sala = new Sala();
        sala.setNome(salaRequestDto.getNome());
        sala.setUrlImagem(salaRequestDto.getUrlImagem());
        sala.setUsuarios(usuarios);
        return sala;
    }
    public static Sala toEntity(SalaRequestDto salaRequestDto, Integer idSala, Set<Usuario> usuarios, Empresa empresa, Projeto projeto) {
        Sala sala = new Sala();
        sala.setIdSala(idSala);
        sala.setNome(salaRequestDto.getNome());
        sala.setUrlImagem(salaRequestDto.getUrlImagem());
        sala.setUsuarios(usuarios);
        sala.setProjeto(projeto);
        return sala;
    }

    public static SalaResponseDto toDto(Sala sala) {
        return SalaResponseDto.builder()
                .idSala(sala.getIdSala())
                .nome(sala.getNome())
                .build();
    }
}
