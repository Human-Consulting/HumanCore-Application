package com.humanconsulting.humancore_api.mapper;

import com.humanconsulting.humancore_api.controller.dto.request.ProjetoRequestDto;
import com.humanconsulting.humancore_api.controller.dto.response.ProjetoResponseDto;
import com.humanconsulting.humancore_api.model.Projeto;

public class ProjetoMapper {
    public static Projeto toEntity(ProjetoRequestDto projetoRequestDto) {
        return new Projeto(null, projetoRequestDto.getDescricao(), projetoRequestDto.getOrcamento(), projetoRequestDto.getUrlImagem(), projetoRequestDto.getFkEmpresa(), projetoRequestDto.getFkResponsavel());
    }

    public static ProjetoResponseDto toDto(Projeto projeto, Integer idResponsavel, String nomeResponsavel, double progresso, boolean comImpedimento) {
        return new ProjetoResponseDto(projeto.getIdProjeto(), projeto.getDescricao(), projeto.getOrcamento(), projeto.getUrlImagem(), idResponsavel, nomeResponsavel, progresso, comImpedimento);
    }
}
