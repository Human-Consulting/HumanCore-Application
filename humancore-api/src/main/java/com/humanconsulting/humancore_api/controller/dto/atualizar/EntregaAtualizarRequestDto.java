package com.humanconsulting.humancore_api.controller.dto.atualizar;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public class EntregaAtualizarRequestDto {
    @NotNull
    private String descricao;

    @NotNull
    private LocalDate dtInicio;

    @NotNull
    private LocalDate dtFim;

    @NotNull
    private Integer fkResponsavel;

    public EntregaAtualizarRequestDto() {
    }

    public EntregaAtualizarRequestDto(String descricao, LocalDate dtInicio, LocalDate dtFim, Integer fkResponsavel) {
        this.descricao = descricao;
        this.dtInicio = dtInicio;
        this.dtFim = dtFim;
        this.fkResponsavel = fkResponsavel;
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

    public @NotNull Integer getFkResponsavel() {
        return fkResponsavel;
    }

    public void setFkResponsavel(@NotNull Integer fkResponsavel) {
        this.fkResponsavel = fkResponsavel;
    }
}
