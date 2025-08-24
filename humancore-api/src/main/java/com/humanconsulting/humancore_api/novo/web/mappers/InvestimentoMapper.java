package com.humanconsulting.humancore_api.novo.web.mappers;

import com.humanconsulting.humancore_api.velho.controller.dto.atualizar.investimento.AtualizarInvestimentoRequestDto;
import com.humanconsulting.humancore_api.velho.controller.dto.request.InvestimentoRequestDto;
import com.humanconsulting.humancore_api.velho.controller.dto.response.investimento.InvestimentoResponseDto;
import com.humanconsulting.humancore_api.velho.model.Investimento;
import com.humanconsulting.humancore_api.velho.model.Projeto;

public class InvestimentoMapper {

    public static Investimento toEntity(InvestimentoRequestDto financeiroRequestDto, Projeto projeto) {
        return Investimento.builder()
                .descricao(financeiroRequestDto.getDescricao())
                .valor(financeiroRequestDto.getValor())
                .dtInvestimento(financeiroRequestDto.getDtInvestimento())
                .projeto(projeto)
                .build();
    }

    public static Investimento toEntity(AtualizarInvestimentoRequestDto atualizarRequest, Integer idInvestimento, Projeto projeto) {
        return Investimento.builder()
                .idInvestimento(idInvestimento)
                .descricao(atualizarRequest.getDescricao())
                .valor(atualizarRequest.getValor())
                .dtInvestimento(atualizarRequest.getDtInvestimento())
                .projeto(projeto)
                .build();
    }

    public static InvestimentoResponseDto toDto(Investimento financeiro) {
        return InvestimentoResponseDto.builder()
                .idInvestimento(financeiro.getIdInvestimento())
                .descricao(financeiro.getDescricao())
                .valor(financeiro.getValor())
                .dtInvestimento(financeiro.getDtInvestimento())
                .projeto(financeiro.getProjeto())
                .build();
    }
}
