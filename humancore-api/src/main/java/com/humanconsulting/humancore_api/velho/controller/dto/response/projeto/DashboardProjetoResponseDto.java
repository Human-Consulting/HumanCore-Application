package com.humanconsulting.humancore_api.velho.controller.dto.response.projeto;

import com.humanconsulting.humancore_api.velho.controller.dto.response.investimento.InvestimentoResponseDto;
import com.humanconsulting.humancore_api.velho.model.Area;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DashboardProjetoResponseDto {
    @Schema(description = "ID do projeto", example = "1")
    private Integer idProjeto;

    @Schema(description = "Nome do responsável pelo projeto", example = "João Silva")
    private String nomeResponsavel;

    @Schema(description = "Indica se há impedimento no projeto", example = "false")
    private boolean comImpedimento;

    @Schema(description = "Progresso atual do projeto, em porcentagem", example = "75.5")
    private Double progresso;

    @Schema(description = "Valor do orçamento do projeto", example = "1000000.00")
    private Double orcamento;

    @Schema(description = "Lista de áreas envolvidas no projeto", implementation = Area.class)
    private List<Area> areas;

    @Schema(description = "Lista de informações financeiras do projeto", implementation = InvestimentoResponseDto.class)
    private List<InvestimentoResponseDto> financeiroResponseDtos;

    @Schema(description = "Total de itens no projeto", example = "10")
    private Integer totalItens;
}
