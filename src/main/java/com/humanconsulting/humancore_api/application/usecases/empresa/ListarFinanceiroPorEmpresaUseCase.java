package com.humanconsulting.humancore_api.application.usecases.empresa;

import com.humanconsulting.humancore_api.domain.entities.Investimento;
import com.humanconsulting.humancore_api.domain.repositories.DashboardEmpresaRepository;
import com.humanconsulting.humancore_api.web.dtos.response.investimento.InvestimentoResponseDto;
import com.humanconsulting.humancore_api.web.mappers.InvestimentoMapper;

import java.util.List;
import java.util.ArrayList;

public class ListarFinanceiroPorEmpresaUseCase {
    private final DashboardEmpresaRepository dashRepository;

    public ListarFinanceiroPorEmpresaUseCase(DashboardEmpresaRepository dashRepository) {
        this.dashRepository = dashRepository;
    }

    public List<InvestimentoResponseDto> execute(Integer idEmpresa) {
        List<Investimento> financeiros = dashRepository.listarFinanceiroPorEmpresa(idEmpresa);
        List<InvestimentoResponseDto> financeiroResponseDtos = new ArrayList<>();
        for (Investimento financeiro : financeiros) {
            financeiroResponseDtos.add(InvestimentoMapper.toDto(financeiro));
        }
        return financeiroResponseDtos;
    }
}

