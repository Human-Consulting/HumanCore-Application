package com.humanconsulting.humancore_api.novo.web.dtos.response.investimento;

import com.humanconsulting.humancore_api.velho.model.Projeto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Id;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InvestimentoResponseDto {

    @Schema(description = "ID do registro financeiro")
    @Id
    private Integer idInvestimento;

    @Schema(description = "Descrição do investimento", example = "Compra de novos recursos")
    private String descricao;

    @Schema(description = "Valor do investimento", example = "10000.00")
    private Double valor;

    @Schema(description = "Data do investimento", example = "2025-04-27")
    private LocalDate dtInvestimento;

    @Schema(description = "ID do projeto relacionado ao investimento", example = "1")
    private Projeto projeto;
}
