package com.humanconsulting.humancore_api.controller.dto.atualizar;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public class SprintAtualizarRequestDto {
    @NotNull
    private String descricao;

    @NotNull
    private LocalDate dtInicio;

    @NotNull
    private LocalDate dtFim;

    public SprintAtualizarRequestDto() {
    }

    public SprintAtualizarRequestDto(String descricao, LocalDate dtInicio, LocalDate dtFim) {
        this.descricao = descricao;
        this.dtInicio = dtInicio;
        this.dtFim = dtFim;
    }

    public @NotNull LocalDate getDtFim() {
        return dtFim;
    }

    public void setDtFim(@NotNull LocalDate dtFim) {
        this.dtFim = dtFim;
    }

    public @NotNull LocalDate getDtInicio() {
        return dtInicio;
    }

    public void setDtInicio(@NotNull LocalDate dtInicio) {
        this.dtInicio = dtInicio;
    }

    public @NotNull String getDescricao() {
        return descricao;
    }

    public void setDescricao(@NotNull String descricao) {
        this.descricao = descricao;
    }
}
