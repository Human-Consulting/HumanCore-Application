package com.humanconsulting.humancore_api.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.time.LocalDate;

@Entity
public class Investimento {
    @Id
    private Integer idInvestimento;

    private Double valor;

    private LocalDate dtInvestimento;

    private Integer fkProjeto;

    public Investimento(Double valor, LocalDate dtInvestimento, Integer fkProjeto) {
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
