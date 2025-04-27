package com.humanconsulting.humancore_api.controller.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;

public class TarefaResponseDto {

    @Schema(description = "ID da Tarefa", example = "1")
    private Integer idTarefa;

    @Schema(description = "Descrição da Tarefa", example = "Desenvolver API para gestão de tarefas")
    private String descricao;

    @Schema(description = "Data de início da Tarefa", example = "2025-04-01")
    private LocalDate dtInicio;

    @Schema(description = "Data de término da Tarefa", example = "2025-04-10")
    private LocalDate dtFim;

    @Schema(description = "Progresso da Tarefa em percentual", example = "50.0")
    private Double progresso;

    @Schema(description = "Indica se há impedimentos na Tarefa", example = "false")
    private Boolean comImpedimento;

    @Schema(description = "ID da Sprint associada à Tarefa", example = "2")
    private Integer fkSprint;

    @Schema(description = "ID do responsável pela Tarefa", example = "101")
    private Integer fkResponsavel;

    @Schema(description = "Nome do responsável pela Tarefa", example = "João Silva")
    private String nomeResponsavel;

    @Schema(description = "Área do responsável pela Tarefa", example = "Desenvolvimento")
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
