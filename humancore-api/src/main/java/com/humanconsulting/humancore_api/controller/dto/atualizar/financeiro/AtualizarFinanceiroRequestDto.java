package com.humanconsulting.humancore_api.controller.dto.atualizar.financeiro;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public class AtualizarFinanceiroRequestDto {
    @Schema(description = "Valor do investimento", example = "15000.75")
    @NotNull
    private Double valor;

    @Schema(description = "Data do investimento", example = "2025-04-27")
    @NotNull
    private LocalDate dtInvestimento;

    @Schema(description = "ID do projeto associado ao investimento", example = "1")
    @NotNull
    private Integer fkProjeto;

    @Schema(description = "ID do usuário que está realizando a requisição (editor)", example = "2")
    @NotNull
    private Integer idEditor;

    @Schema(description = "Permissão do editor", example = "CONSULTOR")
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
