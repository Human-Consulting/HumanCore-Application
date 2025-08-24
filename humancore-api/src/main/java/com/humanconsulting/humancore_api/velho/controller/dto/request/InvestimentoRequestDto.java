package com.humanconsulting.humancore_api.velho.controller.dto.request;

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
    @Schema(description = "Descrição do investimento", example = "Compra de novos recursos")
    @NotNull
    private String descricao;

    @Schema(description = "Valor do investimento", example = "5000.00")
    @NotNull
    private Double valor;

    @Schema(description = "Data do investimento", example = "2025-05-01")
    @NotNull
    private LocalDate dtInvestimento;

    @Schema(description = "ID do projeto relacionado ao investimento", example = "1")
    @NotNull
    private Integer fkProjeto;

    @Schema(description = "ID do usuário que está realizando a requisição (editor)", example = "2")
    @NotNull
    private Integer idEditor;

    @Schema(description = "Permissão do editor", example = "CONSULTOR")
    @NotNull
    private String permissaoEditor;
}
