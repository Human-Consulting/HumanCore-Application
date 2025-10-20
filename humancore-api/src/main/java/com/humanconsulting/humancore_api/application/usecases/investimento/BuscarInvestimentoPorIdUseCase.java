package com.humanconsulting.humancore_api.application.usecases.investimento;

import com.humanconsulting.humancore_api.domain.entities.Investimento;
import com.humanconsulting.humancore_api.domain.exception.EntidadeNaoEncontradaException;
import com.humanconsulting.humancore_api.domain.repositories.InvestimentoRepository;
import com.humanconsulting.humancore_api.web.dtos.response.investimento.InvestimentoResponseDto;
import com.humanconsulting.humancore_api.web.mappers.InvestimentoMapper;

import java.util.Optional;

public class BuscarInvestimentoPorIdUseCase {
    private final InvestimentoRepository investimentoRepository;

    public BuscarInvestimentoPorIdUseCase(InvestimentoRepository investimentoRepository) {
        this.investimentoRepository = investimentoRepository;
    }

    public InvestimentoResponseDto execute(Integer id) {
        Optional<Investimento> optInvestimento = investimentoRepository.findById(id);
        if (optInvestimento.isEmpty()) throw new EntidadeNaoEncontradaException("InvestimentoEntity não encontrada");
        return InvestimentoMapper.toDto(optInvestimento.get());
    }
}

