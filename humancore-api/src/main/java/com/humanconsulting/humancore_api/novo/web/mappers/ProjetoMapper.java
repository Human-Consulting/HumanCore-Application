package com.humanconsulting.humancore_api.novo.web.mappers;

import com.humanconsulting.humancore_api.velho.controller.dto.atualizar.projeto.ProjetoAtualizarRequestDto;
import com.humanconsulting.humancore_api.velho.controller.dto.request.ProjetoRequestDto;
import com.humanconsulting.humancore_api.velho.controller.dto.response.investimento.InvestimentoResponseDto;
import com.humanconsulting.humancore_api.velho.controller.dto.response.projeto.DashboardProjetoResponseDto;
import com.humanconsulting.humancore_api.velho.controller.dto.response.projeto.ProjetoResponseDto;
import com.humanconsulting.humancore_api.velho.model.Area;
import com.humanconsulting.humancore_api.velho.model.Empresa;
import com.humanconsulting.humancore_api.velho.model.Projeto;
import com.humanconsulting.humancore_api.velho.model.Usuario;

import java.util.List;

public class ProjetoMapper {
    public static Projeto toEntity(ProjetoRequestDto projetoRequestDto, Empresa empresa, Usuario usuario) {
        return Projeto.builder()
                .titulo(projetoRequestDto.getTitulo())
                .descricao(projetoRequestDto.getDescricao())
                .orcamento(projetoRequestDto.getOrcamento())
                .urlImagem(projetoRequestDto.getUrlImagem())
                .empresa(empresa)
                .responsavel(usuario)
                .build();
    }

    public static Projeto toEntity(ProjetoAtualizarRequestDto projetoAtualizarRequestDto, Integer idProjeto, Usuario usuario, Empresa empresa) {
        return Projeto.builder()
                .idProjeto(idProjeto)
                .titulo(projetoAtualizarRequestDto.getTitulo())
                .descricao(projetoAtualizarRequestDto.getDescricao())
                .orcamento(projetoAtualizarRequestDto.getOrcamento())
                .urlImagem(projetoAtualizarRequestDto.getUrlImagem())
                .empresa(empresa)
                .responsavel(usuario)
                .build();
    }

    public static ProjetoResponseDto toDto(Projeto projeto, double progresso, boolean comImpedimento) {
        return ProjetoResponseDto.builder()
                .idProjeto(projeto.getIdProjeto())
                .titulo(projeto.getTitulo())
                .descricao(projeto.getDescricao())
                .orcamento(projeto.getOrcamento())
                .urlImagem(projeto.getUrlImagem())
                .urlImagemEmpresa(projeto.getEmpresa().getUrlImagem())
                .nomeResponsavel(projeto.getResponsavel().getNome())
                .fkResponsavel(projeto.getResponsavel().getIdUsuario())
                .progresso(progresso)
                .comImpedimento(comImpedimento)
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
