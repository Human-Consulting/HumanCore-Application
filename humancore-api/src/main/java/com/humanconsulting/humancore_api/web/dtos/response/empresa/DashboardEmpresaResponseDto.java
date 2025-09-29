package com.humanconsulting.humancore_api.web.dtos.response.empresa;

import com.humanconsulting.humancore_api.domain.entities.Area;
import com.humanconsulting.humancore_api.web.dtos.response.investimento.InvestimentoResponseDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DashboardEmpresaResponseDto {
    @Schema(description = "ID da EmpresaEntity", example = "1")
    private Integer idEmpresa;

    @Schema(description = "Nome completo do Responsável pela EmpresaEntity", example = "Carlos Silva")
    private String nomeResponsavel;

    @Schema(description = "Indica se a empresa possui algum projeto com impedimentos", example = "false")
    private boolean comImpedimento;

    @Schema(description = "Progresso total da empresa em porcentagem", example = "75.5")
    private Double progresso;

    @Schema(description = "Orçamento total da empresa", example = "1000000.0")
    private Double orcamento;

    @Schema(description = "Áreas associadas à empresa", example = "[\"TI\", \"InvestimentoEntity\"]")
    private List<Area> areas;

    @Schema(description = "Lista de dados financeiros da empresa", implementation = InvestimentoResponseDto.class)
    private List<InvestimentoResponseDto> financeiroResponseDtos;

    @Schema(description = "Total de itens da empresa", example = "25")
    private Integer totalItens;
}
