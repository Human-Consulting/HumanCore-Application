package com.humanconsulting.humancore_api.mapper;

import com.humanconsulting.humancore_api.controller.dto.financeiro.FinanceiroRequestDto;
import com.humanconsulting.humancore_api.controller.dto.response.financeiro.FinanceiroResponseDto;
import com.humanconsulting.humancore_api.model.Financeiro;

public class FinanceiroMapper {

    public static Financeiro toEntity(FinanceiroRequestDto financeiroRequestDto) {
        return new Financeiro(financeiroRequestDto.getValor(), financeiroRequestDto.getDtInvestimento(), financeiroRequestDto.getFkProjeto());
    }

    public static FinanceiroResponseDto toDto(Financeiro financeiro) {
        return new FinanceiroResponseDto(financeiro.getIdFinanceiro(), financeiro.getValor(), financeiro.getDtInvestimento(), financeiro.getFkProjeto());
    }
}
