package com.humanconsulting.humancore_api.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.time.LocalDate;

@Entity
public class Tarefa {
    @Id
    private Integer idTarefa;

    private String descricao;

    private LocalDate dtInicio;

    private LocalDate dtFim;

    private Double progresso;

    private Boolean comImpedimento;

    private Integer fkSprint;

    private Integer fkResponsavel;

    public Tarefa (String descricao, LocalDate dtInicio, LocalDate dtFim, Integer fkSprint, Integer fkResponsavel) {
        this.idTarefa = null;
        this.descricao = descricao;
        this.dtInicio = dtInicio;
        this.dtFim = dtFim;
        this.progresso = 0.0;
        this.comImpedimento = false;
        this.fkSprint = fkSprint;
        this.fkResponsavel = fkResponsavel;
    }

    public Integer getIdTarefa() {
        return idTarefa;
    }

    public void setIdTarefa(Integer idTarefa) {
        this.idTarefa = idTarefa;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public LocalDate getDtInicio() {
        return dtInicio;
    }

    public void setDtInicio(LocalDate dtInicio) {
        this.dtInicio = dtInicio;
    }

    public LocalDate getDtFim() {
        return dtFim;
    }

    public void setDtFim(LocalDate dtFim) {
        this.dtFim = dtFim;
    }

    public Double getProgresso() {
        return progresso;
    }

    public void setProgresso(Double progresso) {
        this.progresso = progresso;
    }

    public Boolean getComImpedimento() {
        return comImpedimento;
    }

    public void setComImpedimento(Boolean comImpedimento) {
        this.comImpedimento = comImpedimento;
    }

    public Integer getFkSprint() {
        return fkSprint;
    }

    public void setFkSprint(Integer fkSprint) {
        this.fkSprint = fkSprint;
    }

    public Integer getFkResponsavel() {
        return fkResponsavel;
    }

    public void setFkResponsavel(Integer fkResponsavel) {
        this.fkResponsavel = fkResponsavel;
    }
}
