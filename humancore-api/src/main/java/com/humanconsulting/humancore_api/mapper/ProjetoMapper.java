package com.humanconsulting.humancore_api.mapper;

import com.humanconsulting.humancore_api.controller.dto.atualizar.projeto.ProjetoAtualizarRequestDto;
import com.humanconsulting.humancore_api.controller.dto.request.ProjetoRequestDto;
import com.humanconsulting.humancore_api.controller.dto.response.investimento.InvestimentoResponseDto;
import com.humanconsulting.humancore_api.controller.dto.response.projeto.DashboardProjetoResponseDto;
import com.humanconsulting.humancore_api.controller.dto.response.projeto.ProjetoResponseDto;
import com.humanconsulting.humancore_api.model.Area;
import com.humanconsulting.humancore_api.model.Projeto;

import java.util.List;

public class ProjetoMapper {
    public static Projeto toEntity(ProjetoRequestDto projetoRequestDto) {
        return Projeto.builder()
                .descricao(projetoRequestDto.getDescricao())
                .orcamento(projetoRequestDto.getOrcamento())
                .urlImagem(projetoRequestDto.getUrlImagem())
                .empresa(projetoRequestDto.getEmpresa())
                .responsavel(projetoRequestDto.getResponsavel())
                .build();
    }

    public static Projeto toEntity(ProjetoAtualizarRequestDto projetoAtualizarRequestDto) {
        return Projeto.builder()
                .descricao(projetoAtualizarRequestDto.getDescricao())
                .orcamento(projetoAtualizarRequestDto.getOrcamento())
                .urlImagem(projetoAtualizarRequestDto.getUrlImagem())
                .responsavel(projetoAtualizarRequestDto.getResponsavel())
                .build();
    }

    public static ProjetoResponseDto toDto(Projeto projeto, double progresso, boolean comImpedimento) {
        return ProjetoResponseDto.builder()
                .idProjeto(projeto.getIdProjeto())
                .descricao(projeto.getDescricao())
                .orcamento(projeto.getOrcamento())
                .urlImagem(projeto.getUrlImagem())
                .urlImagemEmpresa(projeto.getEmpresa().getUrlImagem())
                .responsavel(projeto.getResponsavel())
                .progresso(progresso)
                .comImpedimento(comImpedimento)
                .empresa(projeto.getEmpresa())
                .build();
    }

    public static DashboardProjetoResponseDto toDashboard(Projeto projeto, String nomeDiretor, Double progresso, List<Area> areas, Double orcamento, Integer projetos, Boolean comImpedimento, List<InvestimentoResponseDto> financeiroResponseDtos) {
        return DashboardProjetoResponseDto.builder()
                .idProjeto(projeto.getIdProjeto())
                .nomeResponsavel(nomeDiretor)
                .comImpedimento(comImpedimento)
                .progresso(progresso)
                .orcamento(orcamento)
                .areas(areas)
                .financeiroResponseDtos(financeiroResponseDtos)
                .totalItens(projetos)
                .build();
    }
}
