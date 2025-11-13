package com.humanconsulting.humancore_api.web.dtos.response.projeto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class KpiProjetoResponseDto {
    @Schema(description = "Lista das empresas com impedimento")
    private List<ProjetoResponseDto> impedidos;

    @Schema(description = "Total de empresas em andamento")
    private Integer totalAndamento;

    @Schema(description = "Lista das empresas finalizadas")
    private List<ProjetoResponseDto> finalizadas;
}
