package com.humanconsulting.humancore_api.novo.web.mappers;

import com.humanconsulting.humancore_api.novo.domain.entities.Investimento;
import com.humanconsulting.humancore_api.novo.domain.entities.Projeto;
import com.humanconsulting.humancore_api.novo.web.dtos.atualizar.investimento.AtualizarInvestimentoRequestDto;
import com.humanconsulting.humancore_api.novo.web.dtos.request.InvestimentoRequestDto;
import com.humanconsulting.humancore_api.novo.web.dtos.response.investimento.InvestimentoResponseDto;

public class InvestimentoMapper {

    public static Investimento toEntity(InvestimentoRequestDto financeiroRequestDto, Projeto projeto) {
        Investimento investimento = new Investimento();
        investimento.setDescricao(financeiroRequestDto.getDescricao());
        investimento.setValor(financeiroRequestDto.getValor());
        investimento.setDtInvestimento(financeiroRequestDto.getDtInvestimento());
        investimento.setProjeto(projeto);
        return investimento;
    }

    public static Investimento toEntity(AtualizarInvestimentoRequestDto atualizarRequest, Integer idInvestimento, Projeto projeto) {
        Investimento investimento = new Investimento();
        investimento.setIdInvestimento(idInvestimento);
        investimento.setDescricao(atualizarRequest.getDescricao());
        investimento.setValor(atualizarRequest.getValor());
        investimento.setDtInvestimento(atualizarRequest.getDtInvestimento());
        investimento.setProjeto(projeto);
        return investimento;
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
