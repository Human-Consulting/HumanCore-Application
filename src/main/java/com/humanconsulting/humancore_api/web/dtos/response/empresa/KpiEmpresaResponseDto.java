package com.humanconsulting.humancore_api.web.dtos.response.empresa;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class KpiEmpresaResponseDto {
    @Schema(description = "Lista das empresas com impedimento")
    private List<EmpresaResponseDto> impedidos;

    @Schema(description = "Total de empresas em andamento")
    private Integer totalAndamento;

    @Schema(description = "Lista das empresas finalizadas")
    private List<EmpresaResponseDto> finalizadas;
}
