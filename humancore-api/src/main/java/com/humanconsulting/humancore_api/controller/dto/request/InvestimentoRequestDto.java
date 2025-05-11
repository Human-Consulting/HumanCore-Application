package com.humanconsulting.humancore_api.controller.dto.request;

import com.humanconsulting.humancore_api.model.Projeto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
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
}
