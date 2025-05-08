package com.humanconsulting.humancore_api.controller.dto.request;

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
public class InvestimentoRequestDto {
    @Schema(description = "Valor do investimento", example = "5000.00")
    @NotNull
    private Double valor;

    @Schema(description = "Data do investimento", example = "2025-05-01")
    @NotNull
    private LocalDate dtInvestimento;

    @Schema(description = "ID do projeto relacionado ao investimento", example = "1")
    @NotNull
    private Projeto projeto;

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
}
