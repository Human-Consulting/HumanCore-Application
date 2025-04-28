package com.humanconsulting.humancore_api.controller.dto.response.investimento;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public class InvestimentoDashResponseDto {
    @Schema(description = "ID do registro financeiro")
    @Id
    private Integer idInvestimento;

    @Schema(description = "Valor do investimento", example = "15000.00")
    @NotNull
    private Double valor;

    @Schema(description = "Data do investimento", example = "2025-04-27")
    @NotNull
    private LocalDate dtInvestimento;

    @Schema(description = "ID do projeto relacionado ao investimento", example = "1")
    @NotNull
    private Integer fkProjeto;

    public InvestimentoDashResponseDto(Integer idInvestimento, Double valor, LocalDate dtInvestimento, Integer fkProjeto) {
        this.idInvestimento = idInvestimento;
        this.valor = valor;
        this.dtInvestimento = dtInvestimento;
        this.fkProjeto = fkProjeto;
    }

    public Integer getIdInvestimento() {
        return idInvestimento;
    }

    public void setIdInvestimento(Integer idInvestimento) {
        this.idInvestimento = idInvestimento;
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
