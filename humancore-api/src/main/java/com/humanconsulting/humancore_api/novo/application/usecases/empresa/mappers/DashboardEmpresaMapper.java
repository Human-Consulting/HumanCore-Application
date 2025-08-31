package com.humanconsulting.humancore_api.novo.application.usecases.empresa.mappers;

import com.humanconsulting.humancore_api.novo.domain.entities.Area;
import com.humanconsulting.humancore_api.novo.domain.entities.Checkpoint;
import com.humanconsulting.humancore_api.novo.domain.entities.Empresa;
import com.humanconsulting.humancore_api.novo.web.dtos.response.empresa.DashboardEmpresaResponseDto;
import com.humanconsulting.humancore_api.novo.web.dtos.response.investimento.InvestimentoResponseDto;
import com.humanconsulting.humancore_api.novo.web.mappers.EmpresaMapper;

import java.util.List;

public class DashboardEmpresaMapper {
    public DashboardEmpresaResponseDto toDashboard(
            Empresa empresa,
            String nomeDiretor,
            List<Checkpoint> checkpoints,
            List<Area> areas,
            Double orcamento,
            Integer projetos,
            Boolean comImpedimento,
            List<InvestimentoResponseDto> allResponse
    ) {
        Double progresso = ProgressoCalculator.calularProgresso(checkpoints);
        return EmpresaMapper.toDashboard(empresa, nomeDiretor, progresso, areas, orcamento, projetos, comImpedimento, allResponse);
    }
}

