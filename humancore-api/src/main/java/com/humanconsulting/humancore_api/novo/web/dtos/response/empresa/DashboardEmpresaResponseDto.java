package com.humanconsulting.humancore_api.novo.web.dtos.response.empresa;

import com.humanconsulting.humancore_api.novo.web.dtos.response.investimento.InvestimentoResponseDto;
import com.humanconsulting.humancore_api.velho.model.Area;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DashboardEmpresaResponseDto {
    @Schema(description = "ID da Empresa", example = "1")
    private Integer idEmpresa;

    @Schema(description = "Nome completo do Responsável pela Empresa", example = "Carlos Silva")
    private String nomeResponsavel;

    @Schema(description = "Indica se a empresa possui algum projeto com impedimentos", example = "false")
    private boolean comImpedimento;

    @Schema(description = "Progresso total da empresa em porcentagem", example = "75.5")
    private Double progresso;

    @Schema(description = "Orçamento total da empresa", example = "1000000.0")
    private Double orcamento;

    @Schema(description = "Áreas associadas à empresa", example = "[\"TI\", \"Investimento\"]")
    private List<Area> areas;

    @Schema(description = "Lista de dados financeiros da empresa", implementation = InvestimentoResponseDto.class)
    private List<InvestimentoResponseDto> financeiroResponseDtos;

    @Schema(description = "Total de itens da empresa", example = "25")
    private Integer totalItens;
}
