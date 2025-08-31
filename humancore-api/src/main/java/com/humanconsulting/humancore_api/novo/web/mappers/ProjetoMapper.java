package com.humanconsulting.humancore_api.novo.web.mappers;

import com.humanconsulting.humancore_api.novo.domain.entities.Projeto;
import com.humanconsulting.humancore_api.novo.domain.entities.Usuario;
import com.humanconsulting.humancore_api.novo.domain.entities.Empresa;
import com.humanconsulting.humancore_api.novo.domain.entities.Area;
import com.humanconsulting.humancore_api.novo.web.dtos.atualizar.projeto.ProjetoAtualizarRequestDto;
import com.humanconsulting.humancore_api.novo.web.dtos.request.ProjetoRequestDto;
import com.humanconsulting.humancore_api.novo.web.dtos.response.investimento.InvestimentoResponseDto;
import com.humanconsulting.humancore_api.novo.web.dtos.response.projeto.DashboardProjetoResponseDto;
import com.humanconsulting.humancore_api.novo.web.dtos.response.projeto.ProjetoResponseDto;

import java.util.List;

public class ProjetoMapper {
    public static Projeto toEntity(ProjetoRequestDto projetoRequestDto, Empresa empresa, Usuario usuario) {
        Projeto projeto = new Projeto();
        projeto.setTitulo(projetoRequestDto.getTitulo());
        projeto.setDescricao(projetoRequestDto.getDescricao());
        projeto.setOrcamento(projetoRequestDto.getOrcamento());
        projeto.setUrlImagem(projetoRequestDto.getUrlImagem());
        projeto.setEmpresa(empresa);
        projeto.setResponsavel(usuario);
        return projeto;
    }

    public static Projeto toEntity(ProjetoAtualizarRequestDto projetoAtualizarRequestDto, Integer idProjeto, Usuario usuario, Empresa empresa) {
        Projeto projeto = new Projeto();
        projeto.setIdProjeto(idProjeto);
        projeto.setTitulo(projetoAtualizarRequestDto.getTitulo());
        projeto.setDescricao(projetoAtualizarRequestDto.getDescricao());
        projeto.setOrcamento(projetoAtualizarRequestDto.getOrcamento());
        projeto.setUrlImagem(projetoAtualizarRequestDto.getUrlImagem());
        projeto.setEmpresa(empresa);
        projeto.setResponsavel(usuario);
        return projeto;
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
