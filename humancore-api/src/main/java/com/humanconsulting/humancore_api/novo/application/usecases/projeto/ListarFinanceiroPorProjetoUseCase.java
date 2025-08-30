package com.humanconsulting.humancore_api.novo.application.usecases.projeto;

import com.humanconsulting.humancore_api.novo.domain.entities.Investimento;
import com.humanconsulting.humancore_api.novo.web.dtos.response.investimento.InvestimentoResponseDto;
import com.humanconsulting.humancore_api.novo.web.mappers.InvestimentoMapper;

import java.util.ArrayList;
import java.util.List;

public class ListarFinanceiroPorProjetoUseCase {
    private final DashboardProjetoRepository dashboardProjetoRepository;

    public ListarFinanceiroPorProjetoUseCase(DashboardProjetoRepository dashboardProjetoRepository) {
        this.dashboardProjetoRepository = dashboardProjetoRepository;
    }

    public List<InvestimentoResponseDto> execute(Integer idProjeto) {
        List<Investimento> financeiros = dashboardProjetoRepository.listarFinanceiroPorEmpresa(idProjeto);
        List<InvestimentoResponseDto> financeiroResponseDtos = new ArrayList<>();
        for (Investimento financeiro : financeiros) {
            financeiroResponseDtos.add(InvestimentoMapper.toDto(financeiro));
        }
        return financeiroResponseDtos;
    }
}

