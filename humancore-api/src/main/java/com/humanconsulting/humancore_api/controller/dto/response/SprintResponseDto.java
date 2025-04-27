package com.humanconsulting.humancore_api.controller.dto.response;

import java.time.LocalDate;
import java.util.List;

public class SprintResponseDto {

    private Integer idSprint;

    private String descricao;

    private LocalDate dtInicio;

    private LocalDate dtFim;

    private Double progresso;

    private Boolean comImpedimento;

    private Integer fkProjeto;

    private List<TarefaResponseDto> tarefas;

    public SprintResponseDto(Integer idSprint, String descricao, LocalDate dtInicio, LocalDate dtFim, Double progresso, Boolean comImpedimento, Integer fkProjeto, List<TarefaResponseDto> tarefas) {
        this.idSprint = idSprint;
        this.descricao = descricao;
        this.dtInicio = dtInicio;
        this.dtFim = dtFim;
        this.progresso = progresso;
        this.comImpedimento = comImpedimento;
        this.fkProjeto = fkProjeto;
        this.tarefas = tarefas;
    }

    public Integer getIdSprint() {
        return idSprint;
    }

    public void setIdSprint(Integer idSprint) {
        this.idSprint = idSprint;
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

    public Integer getFkProjeto() {
        return fkProjeto;
    }

    public void setFkProjeto(Integer fkProjeto) {
        this.fkProjeto = fkProjeto;
    }

    public List<TarefaResponseDto> getTarefas() {
        return tarefas;
    }

    public void setTarefas(List<TarefaResponseDto> tarefas) {
        this.tarefas = tarefas;
    }
}
