package com.humanconsulting.humancore_api.controller.dto.atualizar.entrega;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public class AtualizarGeralRequestDto {
    @NotNull
    private String descricao;

    @NotNull
    private LocalDate dtInicio;

    @NotNull
    private LocalDate dtFim;

    @NotNull
    private Double progresso;

    @NotNull
    private Integer fkResponsavel;

    @NotNull
    private Integer idEditor;

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
