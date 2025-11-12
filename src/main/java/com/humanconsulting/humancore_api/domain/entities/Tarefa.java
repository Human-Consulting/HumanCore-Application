package com.humanconsulting.humancore_api.domain.entities;

import java.time.LocalDate;

public class Tarefa {
    private Integer idTarefa;
    private String titulo;
    private String descricao;
    private LocalDate dtInicio;
    private LocalDate dtFim;
    private Boolean comImpedimento;
    private String comentario;
    private Sprint sprint;
    private Usuario responsavel;

    public Tarefa() {}

    public Tarefa(Integer idTarefa, String titulo, String descricao, LocalDate dtInicio, LocalDate dtFim, Boolean comImpedimento, String comentario, Sprint sprint, Usuario responsavel) {
        this.idTarefa = idTarefa;
        this.titulo = titulo;
        this.descricao = descricao;
        this.dtInicio = dtInicio;
        this.dtFim = dtFim;
        this.comImpedimento = comImpedimento;
        this.comentario = comentario;
        this.sprint = sprint;
        this.responsavel = responsavel;
    }

    public Integer getIdTarefa() { return idTarefa; }
    public void setIdTarefa(Integer idTarefa) { this.idTarefa = idTarefa; }
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
    public LocalDate getDtInicio() { return dtInicio; }
    public void setDtInicio(LocalDate dtInicio) { this.dtInicio = dtInicio; }
    public LocalDate getDtFim() { return dtFim; }
    public void setDtFim(LocalDate dtFim) { this.dtFim = dtFim; }
    public Boolean getComImpedimento() { return comImpedimento; }
    public void setComImpedimento(Boolean comImpedimento) { this.comImpedimento = comImpedimento; }
    public String getComentario() { return comentario; }
    public void setComentario(String comentario) { this.comentario = comentario; }
    public Sprint getSprint() { return sprint; }
    public void setSprint(Sprint sprint) { this.sprint = sprint; }
    public Usuario getResponsavel() { return responsavel; }
    public void setResponsavel(Usuario responsavel) { this.responsavel = responsavel; }
}
