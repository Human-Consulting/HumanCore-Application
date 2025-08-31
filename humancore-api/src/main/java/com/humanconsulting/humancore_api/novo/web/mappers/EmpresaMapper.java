package com.humanconsulting.humancore_api.novo.web.mappers;

import com.humanconsulting.humancore_api.novo.domain.entities.Area;
import com.humanconsulting.humancore_api.novo.domain.entities.Empresa;
import com.humanconsulting.humancore_api.novo.web.dtos.atualizar.empresa.EmpresaAtualizarRequestDto;
import com.humanconsulting.humancore_api.novo.web.dtos.request.EmpresaRequestDto;
import com.humanconsulting.humancore_api.novo.web.dtos.response.empresa.DashboardEmpresaResponseDto;
import com.humanconsulting.humancore_api.novo.web.dtos.response.empresa.EmpresaResponseDto;
import com.humanconsulting.humancore_api.novo.web.dtos.response.investimento.InvestimentoResponseDto;

import java.util.List;

public class EmpresaMapper {
    public static Empresa toEntity(EmpresaRequestDto empresaRequestDto) {
        Empresa empresa = new Empresa();
        empresa.setCnpj(empresaRequestDto.getCnpj());
        empresa.setNome(empresaRequestDto.getNome());
        empresa.setUrlImagem(empresaRequestDto.getUrlImagem());
        return empresa;
    }

    public static Empresa toEntity(EmpresaAtualizarRequestDto empresaRequestDto, Integer idEmpresa) {
        Empresa empresa = new Empresa();
        empresa.setIdEmpresa(idEmpresa);
        empresa.setCnpj(empresaRequestDto.getCnpj());
        empresa.setNome(empresaRequestDto.getNome());
        empresa.setUrlImagem(empresaRequestDto.getUrlImagem());
        return empresa;
    }

    public static EmpresaResponseDto toDto(Empresa empresa, String nomeDiretor, Boolean comImpedimento, Double progresso, Double orcamento) {
        return EmpresaResponseDto.builder()
                .idEmpresa(empresa.getIdEmpresa())
                .nome(empresa.getNome())
                .cnpj(empresa.getCnpj())
                .nomeDiretor(nomeDiretor)
                .comImpedimento(comImpedimento)
                .progresso(progresso)
                .urlImagem(empresa.getUrlImagem())
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
