package com.humanconsulting.humancore_api.controller.dto.response.investimento;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InvestimentoDashResponseDto {
    @Schema(description = "ID do registro financeiro")
    @Id
    private Integer idInvestimento;

    @Schema(description = "Descrição do investimento", example = "Compra de novos recursos")
    @NotNull
    private String descricao;

    @Schema(description = "Valor do investimento", example = "15000.00")
    @NotNull
    private Double valor;

    @Schema(description = "Data do investimento", example = "2025-04-27")
    @NotNull
    private LocalDate dtInvestimento;

    @Schema(description = "ID do projeto relacionado ao investimento", example = "1")
    @NotNull
    private Integer fkProjeto;
}
