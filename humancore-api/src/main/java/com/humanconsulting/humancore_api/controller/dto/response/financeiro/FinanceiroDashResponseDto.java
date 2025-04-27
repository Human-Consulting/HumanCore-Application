package com.humanconsulting.humancore_api.controller.dto.response.financeiro;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public class FinanceiroDashResponseDto {
    @Schema(description = "ID do registro financeiro")
    @Id
    private Integer idFinanceiro;

    @Schema(description = "Valor do investimento", example = "15000.00")
    @NotNull
    private Double valor;

    @Schema(description = "Data do investimento", example = "2025-04-27")
    @NotNull
    private LocalDate dtInvestimento;

    @Schema(description = "ID do projeto relacionado ao investimento", example = "1")
    @NotNull
    private Integer fkProjeto;

    public FinanceiroDashResponseDto(Integer idFinanceiro, Double valor, LocalDate dtInvestimento, Integer fkProjeto) {
        this.idFinanceiro = idFinanceiro;
        this.valor = valor;
        this.dtInvestimento = dtInvestimento;
        this.fkProjeto = fkProjeto;
    }

    public Integer getIdFinanceiro() {
        return idFinanceiro;
    }

    public void setIdFinanceiro(Integer idFinanceiro) {
        this.idFinanceiro = idFinanceiro;
    }

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
