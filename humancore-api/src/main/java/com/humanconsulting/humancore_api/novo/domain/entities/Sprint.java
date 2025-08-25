package com.humanconsulting.humancore_api.novo.domain.entities;

import java.time.LocalDate;

public class Sprint {
    private Integer idSprint;
    private String titulo;
    private String descricao;
    private LocalDate dtInicio;
    private LocalDate dtFim;
    private Projeto projeto;

    public Sprint() {}

    public Sprint(Integer idSprint, String titulo, String descricao, LocalDate dtInicio, LocalDate dtFim, Projeto projeto) {
        this.idSprint = idSprint;
        this.titulo = titulo;
        this.descricao = descricao;
        this.dtInicio = dtInicio;
        this.dtFim = dtFim;
        this.projeto = projeto;
    }

    public Integer getIdSprint() { return idSprint; }
    public void setIdSprint(Integer idSprint) { this.idSprint = idSprint; }
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
    public LocalDate getDtInicio() { return dtInicio; }
    public void setDtInicio(LocalDate dtInicio) { this.dtInicio = dtInicio; }
    public LocalDate getDtFim() { return dtFim; }
    public void setDtFim(LocalDate dtFim) { this.dtFim = dtFim; }
    public Projeto getProjeto() { return projeto; }
    public void setProjeto(Projeto projeto) { this.projeto = projeto; }
}
