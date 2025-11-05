package com.humanconsulting.humancore_api.web.mappers;

import com.humanconsulting.humancore_api.domain.entities.Projeto;
import com.humanconsulting.humancore_api.domain.entities.Usuario;
import com.humanconsulting.humancore_api.domain.entities.Empresa;
import com.humanconsulting.humancore_api.domain.entities.Area;
import com.humanconsulting.humancore_api.web.dtos.atualizar.projeto.ProjetoAtualizarRequestDto;
import com.humanconsulting.humancore_api.web.dtos.request.ProjetoRequestDto;
import com.humanconsulting.humancore_api.web.dtos.response.empresa.EmpresaResponseLoginDto;
import com.humanconsulting.humancore_api.web.dtos.response.investimento.InvestimentoResponseDto;
import com.humanconsulting.humancore_api.web.dtos.response.projeto.DashboardProjetoResponseDto;
import com.humanconsulting.humancore_api.web.dtos.response.projeto.KpiProjetoResponseDto;
import com.humanconsulting.humancore_api.web.dtos.response.projeto.ProjetoResponseDto;
import com.humanconsulting.humancore_api.web.dtos.response.projeto.ProjetoResponseLoginDto;
import com.humanconsulting.humancore_api.web.dtos.response.usuario.UsuarioSprintResponseDto;

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

    public static ProjetoResponseDto toDto(Projeto projeto, double progresso, boolean comImpedimento, UsuarioSprintResponseDto usuario) {
        return ProjetoResponseDto.builder()
                .idProjeto(projeto.getIdProjeto())
                .titulo(projeto.getTitulo())
                .descricao(projeto.getDescricao())
                .orcamento(projeto.getOrcamento())
                .urlImagem(projeto.getUrlImagem())
                .urlImagemEmpresa(projeto.getEmpresa().getUrlImagem())
                .responsavel(usuario)
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

    public static KpiProjetoResponseDto toKpiDto(List<ProjetoResponseDto> impedidos, List<ProjetoResponseDto> finalizadas, Integer totalAndamento) {
        return KpiProjetoResponseDto.builder()
                .impedidos(impedidos)
                .finalizadas(finalizadas)
                .totalAndamento(totalAndamento)
                .build();
    }

    public static ProjetoResponseLoginDto toResponseLoginDto(Projeto projeto, EmpresaResponseLoginDto empresa) {
        return ProjetoResponseLoginDto.builder()
                .idProjeto(projeto.getIdProjeto())
                .titulo(projeto.getTitulo())
                .empresa(empresa)
                .build();
    }
}
