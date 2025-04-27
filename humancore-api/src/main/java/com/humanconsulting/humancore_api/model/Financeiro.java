package com.humanconsulting.humancore_api.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

@Entity
public class Financeiro {
    @Id
    private Integer idFinanceiro;

    private Double valor;

    private LocalDate dtInvestimento;

    private Integer fkProjeto;

    public Financeiro(Double valor, LocalDate dtInvestimento, Integer fkProjeto) {
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
