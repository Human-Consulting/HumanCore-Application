package com.humanconsulting.humancore_api.controller.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;
import java.util.List;

public class SprintResponseDto {

    @Schema(description = "ID da Sprint", example = "1")
    private Integer idSprint;

    @Schema(description = "Descrição da Sprint", example = "Sprint de desenvolvimento da API")
    private String descricao;

    @Schema(description = "Data de início da Sprint", example = "2025-04-01")
    private LocalDate dtInicio;

    @Schema(description = "Data de término da Sprint", example = "2025-04-15")
    private LocalDate dtFim;

    @Schema(description = "Progresso da Sprint em percentual", example = "75.5")
    private Double progresso;

    @Schema(description = "Indica se há impedimentos na Sprint", example = "true")
    private Boolean comImpedimento;

    @Schema(description = "ID do projeto associado à Sprint", example = "101")
    private Integer fkProjeto;

    @Schema(description = "Lista de tarefas associadas à Sprint", implementation = TarefaResponseDto.class)
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
