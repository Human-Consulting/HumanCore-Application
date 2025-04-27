package com.humanconsulting.humancore_api.controller.dto.atualizar.tarefa;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public class AtualizarGeralRequestDto {
    @Schema(description = "Descrição da tarefa", example = "Desenvolver a funcionalidade de login")
    @NotNull
    private String descricao;

    @Schema(description = "Data de início da tarefa", example = "2025-05-01")
    @NotNull
    private LocalDate dtInicio;

    @Schema(description = "Data de término da tarefa", example = "2025-05-10")
    @NotNull
    private LocalDate dtFim;

    @Schema(description = "Progresso da tarefa em porcentagem", example = "75.5")
    @NotNull
    private Double progresso;

    @Schema(description = "ID do responsável pela tarefa", example = "3")
    @NotNull
    private Integer fkResponsavel;

    @Schema(description = "ID do usuário que está realizando a requisição (editor)", example = "2")
    @NotNull
    private Integer idEditor;

    @Schema(description = "Permissão do editor", example = "CONSULTOR")
    public @NotNull Integer getIdEditor() {
        return idEditor;
    }

    public void setIdEditor(@NotNull Integer idEditor) {
        this.idEditor = idEditor;
    }

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

    public @NotNull Double getProgresso() {
        return progresso;
    }

    public void setProgresso(@NotNull Double progresso) {
        this.progresso = progresso;
    }

    public @NotNull Integer getFkResponsavel() {
        return fkResponsavel;
    }

    public void setFkResponsavel(@NotNull Integer fkResponsavel) {
        this.fkResponsavel = fkResponsavel;
    }
}
