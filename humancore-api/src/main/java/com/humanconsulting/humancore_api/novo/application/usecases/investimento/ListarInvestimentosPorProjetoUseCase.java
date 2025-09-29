package com.humanconsulting.humancore_api.novo.application.usecases.investimento;

import com.humanconsulting.humancore_api.novo.domain.entities.Investimento;
import com.humanconsulting.humancore_api.novo.domain.exception.EntidadeSemRetornoException;
import com.humanconsulting.humancore_api.novo.domain.repositories.InvestimentoRepository;
import com.humanconsulting.humancore_api.novo.domain.repositories.PageResult;
import com.humanconsulting.humancore_api.novo.infrastructure.repositories.adapters.PageResultImpl;
import com.humanconsulting.humancore_api.novo.web.dtos.response.investimento.InvestimentoResponseDto;
import com.humanconsulting.humancore_api.novo.web.mappers.InvestimentoMapper;

import java.util.ArrayList;
import java.util.List;

public class ListarInvestimentosPorProjetoUseCase {
    private final InvestimentoRepository investimentoRepository;

    public ListarInvestimentosPorProjetoUseCase(InvestimentoRepository investimentoRepository) {
        this.investimentoRepository = investimentoRepository;
    }

    public PageResult<InvestimentoResponseDto> execute(Integer idProjeto, int page, int size) {
        PageResult<Investimento> investimentos = investimentoRepository.findAllByProjeto_IdProjeto(idProjeto, page, size);
        if (investimentos.getContent().isEmpty()) throw new EntidadeSemRetornoException("Nenhuma financeiro registrada");
        List<InvestimentoResponseDto> allResponse = new ArrayList<>();
        for (Investimento financeiro : investimentos.getContent()) {
            allResponse.add(InvestimentoMapper.toDto(financeiro));
        }
        return new PageResultImpl<>(
            allResponse,
            investimentos.getPageNumber(),
            investimentos.getPageSize(),
            investimentos.getTotalElements(),
            investimentos.getTotalPages()
        );
    }
}
