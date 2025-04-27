package com.humanconsulting.humancore_api.controller.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public class TarefaRequestDto {

    @Schema(description = "Descrição da tarefa", example = "Desenvolver a funcionalidade de login")
    @NotNull
    private String descricao;

    @Schema(description = "Data de início da tarefa", example = "2025-05-01")
    @NotNull
    private LocalDate dtInicio;

    @Schema(description = "Data de término da tarefa", example = "2025-05-10")
    @NotNull
    private LocalDate dtFim;

    @Schema(description = "ID da Sprint associada à tarefa", example = "2001")
    @NotNull
    private Integer fkSprint;

    @Schema(description = "ID do responsável pela tarefa", example = "150")
    @NotNull
    private Integer fkResponsavel;

    public @NotNull String getDescricao() {
        return descricao;
    }

    public void setDescricao(@NotNull String descricao) {
        this.descricao = descricao;
    }

    public @NotNull LocalDate getDtInicio() {
        return dtInicio;
    }

    public void setDtInicio(@NotNull LocalDate dtInicio) {
        this.dtInicio = dtInicio;
    }

    public @NotNull LocalDate getDtFim() {
        return dtFim;
    }

    public void setDtFim(@NotNull LocalDate dtFim) {
        this.dtFim = dtFim;
    }

    public @NotNull Integer getFkSprint() {
        return fkSprint;
    }

    public void setFkSprint(@NotNull Integer fkSprint) {
        this.fkSprint = fkSprint;
    }

    public @NotNull Integer getFkResponsavel() {
        return fkResponsavel;
    }

    public void setFkResponsavel(@NotNull Integer fkResponsavel) {
        this.fkResponsavel = fkResponsavel;
    }
}
