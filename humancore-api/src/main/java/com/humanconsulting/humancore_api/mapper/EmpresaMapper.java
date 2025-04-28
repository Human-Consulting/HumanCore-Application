package com.humanconsulting.humancore_api.mapper;

import com.humanconsulting.humancore_api.controller.dto.request.EmpresaRequestDto;
import com.humanconsulting.humancore_api.controller.dto.response.investimento.InvestimentoResponseDto;
import com.humanconsulting.humancore_api.controller.dto.response.empresa.DashboardEmpresaResponseDto;
import com.humanconsulting.humancore_api.controller.dto.response.empresa.EmpresaResponseDto;
import com.humanconsulting.humancore_api.model.Area;
import com.humanconsulting.humancore_api.model.Empresa;

import java.util.List;

public class EmpresaMapper {
    public static Empresa toEntity(EmpresaRequestDto empresaRequestDto) {
        return new Empresa(empresaRequestDto.getCnpj(), empresaRequestDto.getNome(), empresaRequestDto.getUrlImagem());
    }

    public static EmpresaResponseDto toDto(Empresa empresa, String nomeDiretor, Boolean comImpedimento, Double progresso, Double orcamento) {
        return new EmpresaResponseDto(empresa.getIdEmpresa(), empresa.getNome(), empresa.getCnpj(), nomeDiretor, comImpedimento, progresso, empresa.getUrlImagem(), orcamento);
    }

    public static DashboardEmpresaResponseDto toDashboard(Empresa empresa, String nomeDiretor, Double progresso, List<Area> areas, Double orcamento, Integer projetos, Boolean comImpedimento, List<InvestimentoResponseDto> financeiroResponseDtos) {
        return new DashboardEmpresaResponseDto(empresa.getIdEmpresa(), nomeDiretor, comImpedimento, progresso, orcamento, areas, projetos, financeiroResponseDtos);
    }
}
