package com.humanconsulting.humancore_api.controller.dto.financeiro;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public class FinanceiroRequestDto {
    @NotNull
    private Double valor;

    @NotNull
    private LocalDate dtInvestimento;

    @NotNull
    private Integer fkProjeto;

    public @NotNull Double getValor() {
        return valor;
    }

    public void setValor(@NotNull Double valor) {
        this.valor = valor;
    }

    public @NotNull LocalDate getDtInvestimento() {
        return dtInvestimento;
    }

    public void setDtInvestimento(@NotNull LocalDate dtInvestimento) {
        this.dtInvestimento = dtInvestimento;
    }

    public @NotNull Integer getFkProjeto() {
        return fkProjeto;
    }

    public void setFkProjeto(@NotNull Integer fkProjeto) {
        this.fkProjeto = fkProjeto;
    }
}
