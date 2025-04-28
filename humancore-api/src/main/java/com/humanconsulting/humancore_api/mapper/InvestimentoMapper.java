package com.humanconsulting.humancore_api.mapper;

import com.humanconsulting.humancore_api.controller.dto.request.InvestimentoRequestDto;
import com.humanconsulting.humancore_api.controller.dto.response.investimento.InvestimentoResponseDto;
import com.humanconsulting.humancore_api.model.Investimento;

public class InvestimentoMapper {

    public static Investimento toEntity(InvestimentoRequestDto financeiroRequestDto) {
        return new Investimento(financeiroRequestDto.getValor(), financeiroRequestDto.getDtInvestimento(), financeiroRequestDto.getFkProjeto());
    }

    public static InvestimentoResponseDto toDto(Investimento financeiro) {
        return new InvestimentoResponseDto(financeiro.getIdInvestimento(), financeiro.getValor(), financeiro.getDtInvestimento(), financeiro.getFkProjeto());
    }
}
