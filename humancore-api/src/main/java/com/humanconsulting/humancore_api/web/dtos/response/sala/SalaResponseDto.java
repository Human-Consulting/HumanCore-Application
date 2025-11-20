package com.humanconsulting.humancore_api.web.dtos.response.sala;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SalaResponseDto {
    @Schema(description = "ID da sala", example = "1")
    private Integer idSala;

    @Schema(description = "Nome da sala", example = "SalaEntity 1")
    private String nome;

    @Schema(description = "Nome da empresa associada", example = "Empresa 1")
    private String nomeEmpresa;
}