package com.humanconsulting.humancore_api.application.usecases.empresa.mappers;

import com.humanconsulting.humancore_api.domain.entities.Area;
import com.humanconsulting.humancore_api.domain.entities.Checkpoint;
import com.humanconsulting.humancore_api.domain.entities.Empresa;
import com.humanconsulting.humancore_api.domain.utils.ProgressoCalculator;
import com.humanconsulting.humancore_api.web.dtos.response.empresa.DashboardEmpresaResponseDto;
import com.humanconsulting.humancore_api.web.dtos.response.investimento.InvestimentoResponseDto;
import com.humanconsulting.humancore_api.web.mappers.EmpresaMapper;

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
        Double progresso = ProgressoCalculator.execute(checkpoints);
        return EmpresaMapper.toDashboard(empresa, nomeDiretor, progresso, areas, orcamento, projetos, comImpedimento, allResponse);
    }
}

