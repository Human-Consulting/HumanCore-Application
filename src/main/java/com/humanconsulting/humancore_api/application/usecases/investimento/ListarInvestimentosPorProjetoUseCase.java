package com.humanconsulting.humancore_api.application.usecases.investimento;

import com.humanconsulting.humancore_api.domain.entities.Investimento;
import com.humanconsulting.humancore_api.domain.exception.EntidadeSemRetornoException;
import com.humanconsulting.humancore_api.domain.repositories.InvestimentoRepository;
import com.humanconsulting.humancore_api.domain.utils.PageResult;
import com.humanconsulting.humancore_api.infrastructure.utils.PageResultImpl;
import com.humanconsulting.humancore_api.web.dtos.response.investimento.InvestimentoResponseDto;
import com.humanconsulting.humancore_api.web.mappers.InvestimentoMapper;

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

