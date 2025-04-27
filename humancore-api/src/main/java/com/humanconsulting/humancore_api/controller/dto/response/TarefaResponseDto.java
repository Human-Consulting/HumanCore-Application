package com.humanconsulting.humancore_api.controller.dto.response;

import java.time.LocalDate;

public class TarefaResponseDto {

    private Integer idTarefa;

    private String descricao;

    private LocalDate dtInicio;

    private LocalDate dtFim;

    private Double progresso;

    private Boolean comImpedimento;

    private Integer fkSprint;

    private Integer fkResponsavel;

    private String nomeResponsavel;

    private String areaResponsavel;

    public TarefaResponseDto(Integer idEntrega, String descricao, LocalDate dtInicio, LocalDate dtFim, Double progresso, Boolean comImpedimento, Integer fkSprint, Integer fkResponsavel, String nomeResponsavel, String areaResponsavel) {
        this.idTarefa = idEntrega;
        this.descricao = descricao;
        this.dtInicio = dtInicio;
        this.dtFim = dtFim;
        this.progresso = progresso;
        this.comImpedimento = comImpedimento;
        this.fkSprint = fkSprint;
        this.fkResponsavel = fkResponsavel;
        this.nomeResponsavel = nomeResponsavel;
        this.areaResponsavel = areaResponsavel;
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

    public String getNomeResponsavel() {
        return nomeResponsavel;
    }

    public void setNomeResponsavel(String nomeResponsavel) {
        this.nomeResponsavel = nomeResponsavel;
    }

    public String getAreaResponsavel() {
        return areaResponsavel;
    }

    public void setAreaResponsavel(String areaResponsavel) {
        this.areaResponsavel = areaResponsavel;
    }
}
