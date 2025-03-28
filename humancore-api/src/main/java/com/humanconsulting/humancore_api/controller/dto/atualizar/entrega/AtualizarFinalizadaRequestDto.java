package com.humanconsulting.humancore_api.controller.dto.atualizar.entrega;

import jakarta.validation.constraints.NotNull;

public class AtualizarFinalizadaRequestDto {
    @NotNull
    private Boolean novoFinalizada;

    @NotNull
    private Integer idEditor;

    @NotNull
    private String permissaoEditor;

    public @NotNull Boolean getNovoFinalizada() {
        return novoFinalizada;
    }

    public void setNovoFinalizada(@NotNull Boolean novoFinalizada) {
        this.novoFinalizada = novoFinalizada;
    }

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
}
