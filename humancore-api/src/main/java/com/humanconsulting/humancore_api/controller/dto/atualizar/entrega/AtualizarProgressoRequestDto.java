package com.humanconsulting.humancore_api.controller.dto.atualizar.entrega;

import jakarta.validation.constraints.NotNull;

public class AtualizarProgressoRequestDto {
    @NotNull
    private Double novoProgresso;

    @NotNull
    private Integer idEditor;

    @NotNull
    private String permissaoEditor;

    public @NotNull Double getNovoProgresso() {
        return novoProgresso;
    }

    public void setNovoProgresso(@NotNull Double novoProgresso) {
        this.novoProgresso = novoProgresso;
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
