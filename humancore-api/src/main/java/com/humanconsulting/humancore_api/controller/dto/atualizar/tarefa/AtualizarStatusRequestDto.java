package com.humanconsulting.humancore_api.controller.dto.atualizar.tarefa;

import jakarta.validation.constraints.NotNull;

public class AtualizarStatusRequestDto {

    @NotNull
    private Integer idEditor;

    public @NotNull Integer getIdEditor() {
        return idEditor;
    }

    public void setIdEditor(@NotNull Integer idEditor) {
        this.idEditor = idEditor;
    }
}
