package com.humanconsulting.humancore_api.controller.dto.atualizar.financeiro;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public class AtualizarFinanceiroRequestDto {
    @NotNull
    private Double valor;

    @NotNull
    private LocalDate dtInvestimento;

    @NotNull
    private Integer fkProjeto;

    @NotNull
    private Integer idEditor;

    @NotNull
    private String permissaoEditor;

    public @NotNull Double getValor() {
        return valor;
    }

    public void setValor(@NotNull Double valor) {
        this.valor = valor;
    }

    public @NotNull LocalDate getDtInvestimento() {
        return dtInvestimento;
    }

    public void setDtInvestimento(@NotNull LocalDate dtInvestimento) {
        this.dtInvestimento = dtInvestimento;
    }

    public @NotNull Integer getFkProjeto() {
        return fkProjeto;
    }

    public void setFkProjeto(@NotNull Integer fkProjeto) {
        this.fkProjeto = fkProjeto;
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
