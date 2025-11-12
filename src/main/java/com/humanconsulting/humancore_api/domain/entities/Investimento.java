package com.humanconsulting.humancore_api.domain.entities;

import java.time.LocalDate;

public class Investimento {
    private Integer idInvestimento;
    private String descricao;
    private Double valor;
    private LocalDate dtInvestimento;
    private Projeto projeto;

    public Investimento() {}

    public Investimento(Integer idInvestimento, String descricao, Double valor, LocalDate dtInvestimento, Projeto projeto) {
        this.idInvestimento = idInvestimento;
        this.descricao = descricao;
        this.valor = valor;
        this.dtInvestimento = dtInvestimento;
        this.projeto = projeto;
    }

    public Integer getIdInvestimento() { return idInvestimento; }
    public void setIdInvestimento(Integer idInvestimento) { this.idInvestimento = idInvestimento; }
    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
    public Double getValor() { return valor; }
    public void setValor(Double valor) { this.valor = valor; }
    public LocalDate getDtInvestimento() { return dtInvestimento; }
    public void setDtInvestimento(LocalDate dtInvestimento) { this.dtInvestimento = dtInvestimento; }
    public Projeto getProjeto() { return projeto; }
    public void setProjeto(Projeto projeto) { this.projeto = projeto; }
}
