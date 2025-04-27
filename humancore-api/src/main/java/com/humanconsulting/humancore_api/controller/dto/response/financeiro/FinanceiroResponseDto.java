package com.humanconsulting.humancore_api.controller.dto.response.financeiro;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Id;

import java.time.LocalDate;

public class FinanceiroResponseDto {
    @Schema(description = "ID do registro financeiro")
    @Id
    private Integer idFinanceiro;

    @Schema(description = "Valor do investimento", example = "10000.00")
    private Double valor;

    @Schema(description = "Data do investimento", example = "2025-04-27")
    private LocalDate dtInvestimento;

    @Schema(description = "ID do projeto relacionado ao investimento", example = "1")
    private Integer fkProjeto;

    public FinanceiroResponseDto(Integer idFinanceiro, Double valor, LocalDate dtInvestimento, Integer fkProjeto) {
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

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public LocalDate getDtInvestimento() {
        return dtInvestimento;
    }

    public void setDtInvestimento(LocalDate dtInvestimento) {
        this.dtInvestimento = dtInvestimento;
    }

    public Integer getFkProjeto() {
        return fkProjeto;
    }

    public void setFkProjeto(Integer fkProjeto) {
        this.fkProjeto = fkProjeto;
    }
}
