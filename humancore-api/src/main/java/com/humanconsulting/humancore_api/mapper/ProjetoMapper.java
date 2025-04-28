package com.humanconsulting.humancore_api.mapper;

import com.humanconsulting.humancore_api.controller.dto.request.ProjetoRequestDto;
import com.humanconsulting.humancore_api.controller.dto.response.investimento.InvestimentoResponseDto;
import com.humanconsulting.humancore_api.controller.dto.response.projeto.DashboardProjetoResponseDto;
import com.humanconsulting.humancore_api.controller.dto.response.projeto.ProjetoResponseDto;
import com.humanconsulting.humancore_api.model.Area;
import com.humanconsulting.humancore_api.model.Projeto;

import java.util.List;

public class ProjetoMapper {
    public static Projeto toEntity(ProjetoRequestDto projetoRequestDto) {
        return new Projeto(null, projetoRequestDto.getDescricao(), projetoRequestDto.getOrcamento(), projetoRequestDto.getUrlImagem(), projetoRequestDto.getFkEmpresa(), projetoRequestDto.getFkResponsavel());
    }

    public static ProjetoResponseDto toDto(Projeto projeto, Integer idResponsavel, String nomeResponsavel, double progresso, boolean comImpedimento, String urlImagemEmpresa) {
        return new ProjetoResponseDto(projeto.getIdProjeto(), projeto.getDescricao(), projeto.getOrcamento(), projeto.getUrlImagem(), urlImagemEmpresa, idResponsavel, nomeResponsavel, progresso, comImpedimento, projeto.getFkEmpresa());
    }

    public static DashboardProjetoResponseDto toDashboard(Projeto projeto, String nomeDiretor, Double progresso, List<Area> areas, Double orcamento, Integer projetos, Boolean comImpedimento, List<InvestimentoResponseDto> financeiroResponseDtos) {
        return new DashboardProjetoResponseDto(projeto.getIdProjeto(), nomeDiretor, comImpedimento, progresso, orcamento, areas, projetos, financeiroResponseDtos);
    }
}
