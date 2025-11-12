package com.humanconsulting.humancore_api.web.mappers;

import com.humanconsulting.humancore_api.domain.entities.Area;
import com.humanconsulting.humancore_api.domain.entities.Empresa;
import com.humanconsulting.humancore_api.domain.entities.Tarefa;
import com.humanconsulting.humancore_api.domain.entities.TarefaUsuario;
import com.humanconsulting.humancore_api.web.dtos.atualizar.empresa.EmpresaAtualizarRequestDto;
import com.humanconsulting.humancore_api.web.dtos.request.EmpresaRequestDto;
import com.humanconsulting.humancore_api.web.dtos.response.checkpoint.CheckpointResponseDto;
import com.humanconsulting.humancore_api.web.dtos.response.empresa.DashboardEmpresaResponseDto;
import com.humanconsulting.humancore_api.web.dtos.response.empresa.EmpresaResponseDto;
import com.humanconsulting.humancore_api.web.dtos.response.empresa.EmpresaResponseLoginDto;
import com.humanconsulting.humancore_api.web.dtos.response.empresa.KpiEmpresaResponseDto;
import com.humanconsulting.humancore_api.web.dtos.response.investimento.InvestimentoResponseDto;
import com.humanconsulting.humancore_api.web.dtos.response.sprint.SprintResponseLoginDto;
import com.humanconsulting.humancore_api.web.dtos.response.tarefa.TarefaLoginResponseDto;
import com.humanconsulting.humancore_api.web.dtos.response.usuario.UsuarioSprintResponseDto;

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

    public static EmpresaResponseDto toDto(Empresa empresa, UsuarioSprintResponseDto responsavel, Boolean comImpedimento, Double progresso, Double orcamento) {
        return EmpresaResponseDto.builder()
                .idEmpresa(empresa.getIdEmpresa())
                .nome(empresa.getNome())
                .cnpj(empresa.getCnpj())
                .responsavel(responsavel)
                .comImpedimento(comImpedimento)
                .progresso(progresso)
                .urlImagem(empresa.getUrlImagem())
                .orcamento(orcamento)
                .build();
    }

    public static DashboardEmpresaResponseDto toDashboard(Empresa empresa, UsuarioSprintResponseDto responsavel, Double progresso, List<Area> areas, List<TarefaUsuario> usuarios, Double orcamento, Integer projetos, Boolean comImpedimento, List<InvestimentoResponseDto> financeiroResponseDtos) {
        return DashboardEmpresaResponseDto.builder()
                .idEmpresa(empresa.getIdEmpresa())
                .responsavel(responsavel)
                .comImpedimento(comImpedimento)
                .progresso(progresso)
                .orcamento(orcamento)
                .totalItens(projetos)
                .areas(areas)
                .usuarios(usuarios)
                .financeiroResponseDtos(financeiroResponseDtos)
                .build();
    }

    public static KpiEmpresaResponseDto toKpiDto(List<EmpresaResponseDto> impedidos, List<EmpresaResponseDto> finalizadas, Integer totalAndamento) {
        return KpiEmpresaResponseDto.builder()
                .impedidos(impedidos)
                .finalizadas(finalizadas)
                .totalAndamento(totalAndamento)
                .build();
    }

    public static EmpresaResponseLoginDto toResponseLoginDto(Empresa empresa) {
        return EmpresaResponseLoginDto.builder()
                .idEmpresa(empresa.getIdEmpresa())
                .nome(empresa.getNome())
                .build();
    }
}
