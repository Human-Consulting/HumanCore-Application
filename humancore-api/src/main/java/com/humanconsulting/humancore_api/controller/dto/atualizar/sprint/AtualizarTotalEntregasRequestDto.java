package com.humanconsulting.humancore_api.controller.dto.atualizar.sprint;

import jakarta.validation.constraints.NotNull;

public class AtualizarTotalEntregasRequestDto {
    @NotNull
    private Integer novoTotalEntregas;

    @NotNull
    private Integer idEditor;

    @NotNull
    private String permissaoEditor;

    public @NotNull Integer getNovoTotalEntregas() {
        return novoTotalEntregas;
    }

    public void setNovoTotalEntregas(@NotNull Integer novoTotalEntregas) {
        this.novoTotalEntregas = novoTotalEntregas;
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
