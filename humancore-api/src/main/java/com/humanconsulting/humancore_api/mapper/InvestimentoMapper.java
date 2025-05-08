package com.humanconsulting.humancore_api.mapper;

import com.humanconsulting.humancore_api.controller.dto.atualizar.investimento.AtualizarInvestimentoRequestDto;
import com.humanconsulting.humancore_api.controller.dto.request.InvestimentoRequestDto;
import com.humanconsulting.humancore_api.controller.dto.response.investimento.InvestimentoResponseDto;
import com.humanconsulting.humancore_api.model.Investimento;

public class InvestimentoMapper {

    public static Investimento toEntity(InvestimentoRequestDto financeiroRequestDto) {
        return Investimento.builder()
                .valor(financeiroRequestDto.getValor())
                .dtInvestimento(financeiroRequestDto.getDtInvestimento())
                .projeto(financeiroRequestDto.getProjeto())
                .build();
    }

    public static Investimento toEntity(AtualizarInvestimentoRequestDto atualizarRequest) {
        return Investimento.builder()
                .valor(atualizarRequest.getValor())
                .dtInvestimento(atualizarRequest.getDtInvestimento())
                .projeto(atualizarRequest.getProjeto())
                .build();
    }

    public static InvestimentoResponseDto toDto(Investimento financeiro) {
        return InvestimentoResponseDto.builder()
                .idInvestimento(financeiro.getIdInvestimento())
                .valor(financeiro.getValor())
                .dtInvestimento(financeiro.getDtInvestimento())
                .projeto(financeiro.getProjeto())
                .build();
    }
}
