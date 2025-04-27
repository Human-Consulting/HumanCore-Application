package com.humanconsulting.humancore_api.controller.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public class SprintRequestDto {
    @Schema(description = "Descrição da Sprint", example = "Sprint de desenvolvimento de funcionalidades para a versão 1.0")
    @NotNull
    private String descricao;

    @Schema(description = "Data de início da Sprint", example = "2025-05-01")
    @NotNull
    private LocalDate dtInicio;

    @Schema(description = "Data de término da Sprint", example = "2025-05-15")
    @NotNull
    private LocalDate dtFim;

    @Schema(description = "ID do projeto ao qual a Sprint pertence", example = "1001")
    @NotNull
    private Integer fkProjeto;

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

    public @NotNull Integer getFkProjeto() {
        return fkProjeto;
    }

    public void setFkProjeto(@NotNull Integer fkProjeto) {
        this.fkProjeto = fkProjeto;
    }
}
