package com.humanconsulting.humancore_api.controller.dto.response.investimento;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Id;

import java.time.LocalDate;

public class InvestimentoResponseDto {
    @Schema(description = "ID do registro financeiro")
    @Id
    private Integer idInvestimento;

    @Schema(description = "Valor do investimento", example = "10000.00")
    private Double valor;

    @Schema(description = "Data do investimento", example = "2025-04-27")
    private LocalDate dtInvestimento;

    @Schema(description = "ID do projeto relacionado ao investimento", example = "1")
    private Integer fkProjeto;

    public InvestimentoResponseDto(Integer idInvestimento, Double valor, LocalDate dtInvestimento, Integer fkProjeto) {
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
