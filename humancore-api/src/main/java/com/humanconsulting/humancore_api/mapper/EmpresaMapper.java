package com.humanconsulting.humancore_api.mapper;

import com.humanconsulting.humancore_api.controller.dto.atualizar.empresa.EmpresaAtualizarRequestDto;
import com.humanconsulting.humancore_api.controller.dto.request.EmpresaRequestDto;
import com.humanconsulting.humancore_api.controller.dto.response.investimento.InvestimentoResponseDto;
import com.humanconsulting.humancore_api.controller.dto.response.empresa.DashboardEmpresaResponseDto;
import com.humanconsulting.humancore_api.controller.dto.response.empresa.EmpresaResponseDto;
import com.humanconsulting.humancore_api.model.Area;
import com.humanconsulting.humancore_api.model.Empresa;

import java.util.List;

public class EmpresaMapper {
    public static Empresa toEntity(EmpresaRequestDto empresaRequestDto) {
        return Empresa.builder()
                .cnpj(empresaRequestDto.getCnpj())
                .nome(empresaRequestDto.getNome())
                .urlImagem(empresaRequestDto.getUrlImagem())
                .build();
    }

    public static Empresa toEntity(EmpresaAtualizarRequestDto empresaRequestDto) {
        return Empresa.builder()
                .cnpj(empresaRequestDto.getCnpj())
                .nome(empresaRequestDto.getNome())
                .urlImagem(empresaRequestDto.getUrlImagem())
                .build();
    }

    public static EmpresaResponseDto toDto(Empresa empresa, String nomeDiretor, Boolean comImpedimento, Double progresso, Double orcamento) {
        return EmpresaResponseDto.builder()
                .idEmpresa(empresa.getIdEmpresa())
                .nome(empresa.getNome())
                .cnpj(empresa.getCnpj())
                .nomeDiretor(nomeDiretor)
                .comImpedimento(comImpedimento)
                .progresso(progresso)
                .urlImagemEmpresa(empresa.getUrlImagem())
                .orcamento(orcamento)
                .build();
    }

    public static DashboardEmpresaResponseDto toDashboard(Empresa empresa, String nomeDiretor, Double progresso, List<Area> areas, Double orcamento, Integer projetos, Boolean comImpedimento, List<InvestimentoResponseDto> financeiroResponseDtos) {

        return DashboardEmpresaResponseDto.builder()
                .idEmpresa(empresa.getIdEmpresa())
                .nomeResponsavel(nomeDiretor)
                .comImpedimento(comImpedimento)
                .progresso(progresso)
                .orcamento(orcamento)
                .totalItens(projetos)
                .areas(areas)
                .financeiroResponseDtos(financeiroResponseDtos)
                .build();
    }
}
