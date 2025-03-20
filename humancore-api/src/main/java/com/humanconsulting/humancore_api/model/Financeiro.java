package com.humanconsulting.humancore_api.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

@Entity
public class Financeiro {
    @Id
    private Integer idFinanceiro;

    @NotNull
    private Double valor;

    @NotNull
    private LocalDate dtInvestimento;

    @NotNull
    private Integer fkProjeto;

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
