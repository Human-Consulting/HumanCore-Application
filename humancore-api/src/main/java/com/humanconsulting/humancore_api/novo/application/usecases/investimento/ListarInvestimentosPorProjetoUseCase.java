package com.humanconsulting.humancore_api.novo.application.usecases.investimento;

import com.humanconsulting.humancore_api.novo.domain.entities.Investimento;
import com.humanconsulting.humancore_api.novo.domain.exception.EntidadeSemRetornoException;
import com.humanconsulting.humancore_api.novo.domain.repositories.InvestimentoRepository;
import com.humanconsulting.humancore_api.novo.web.dtos.response.investimento.InvestimentoResponseDto;
import com.humanconsulting.humancore_api.novo.web.mappers.InvestimentoMapper;

import java.util.ArrayList;
import java.util.List;

public class ListarInvestimentosPorProjetoUseCase {
    private final InvestimentoRepository investimentoRepository;

    public ListarInvestimentosPorProjetoUseCase(InvestimentoRepository investimentoRepository) {
        this.investimentoRepository = investimentoRepository;
    }

    public List<InvestimentoResponseDto> execute(Integer idProjeto) {
        List<Investimento> all = investimentoRepository.findAllByProjeto_IdProjeto(idProjeto);
        if (all.isEmpty()) throw new EntidadeSemRetornoException("Nenhuma financeiro registrada");
        List<InvestimentoResponseDto> allResponse = new ArrayList<>();
        for (Investimento financeiro : all) {
            allResponse.add(InvestimentoMapper.toDto(financeiro));
        }
        return allResponse;
    }
}

