package com.humanconsulting.humancore_api.novo.application.usecases.investimento;

import com.humanconsulting.humancore_api.novo.domain.entities.Investimento;
import com.humanconsulting.humancore_api.novo.domain.repositories.InvestimentoRepository;

import java.util.Optional;

public class BuscarInvestimentoPorIdUseCase {
    private final InvestimentoRepository investimentoRepository;

    public BuscarInvestimentoPorIdUseCase(InvestimentoRepository investimentoRepository) {
        this.investimentoRepository = investimentoRepository;
    }

    public Investimento execute(Integer id) {
        Optional<Investimento> optInvestimento = investimentoRepository.findById(id);
        if (optInvestimento.isEmpty()) throw new EntidadeNaoEncontradaException("InvestimentoEntity não encontrada");
        return optInvestimento.get();
    }
}

