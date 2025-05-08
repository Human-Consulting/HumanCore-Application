package com.humanconsulting.humancore_api.controller.dto.atualizar.investimento;

import com.humanconsulting.humancore_api.model.Projeto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AtualizarInvestimentoRequestDto {
    @Schema(description = "Valor do investimento", example = "15000.75")
    @NotNull
    private Double valor;

    @Schema(description = "Data do investimento", example = "2025-04-27")
    @NotNull
    private LocalDate dtInvestimento;

    @Schema(description = "ID do projeto associado ao investimento", example = "1")
    @NotNull
    private Projeto projeto;

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

    public @NotNull Projeto getProjeto() {
        return projeto;
    }

    public void setProjeto(@NotNull Projeto projeto) {
        this.projeto = projeto;
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
