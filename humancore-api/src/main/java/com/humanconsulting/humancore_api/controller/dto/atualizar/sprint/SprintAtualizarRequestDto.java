package com.humanconsulting.humancore_api.controller.dto.atualizar.sprint;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public class SprintAtualizarRequestDto {
    @Schema(description = "Descrição da sprint", example = "Sprint de desenvolvimento da nova funcionalidade de autenticação")
    @NotNull
    private String descricao;

    @Schema(description = "Data de início da sprint", example = "2025-05-01")
    @NotNull
    private LocalDate dtInicio;

    @Schema(description = "Data de término da sprint", example = "2025-05-15")
    @NotNull
    private LocalDate dtFim;

    @Schema(description = "ID do usuário que está realizando a requisição (editor)", example = "2")
    @NotNull
    private Integer idEditor;

    @Schema(description = "Permissão do editor", example = "CONSULTOR")
    @NotNull
    private String permissaoEditor;

    public @NotNull Integer getIdEditor() {
        return idEditor;
    }

    public void setIdEditor(@NotNull Integer idEditor) {
        this.idEditor = idEditor;
    }

    public @NotNull String getPermissaoEditor() {
        return permissaoEditor;
    }

    public void setPermissaoEditor(@NotNull String permissaoEditor) {
        this.permissaoEditor = permissaoEditor;
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
