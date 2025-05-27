package com.humanconsulting.humancore_api.controller.dto.response.checkpoint;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CheckpointResponseDto {
    @Schema(description = "Identificador único do checkpoint", example = "1")
    private Integer idCheckpoint;

    @Schema(description = "Descrição do checkpoint", example = "Desenvolver a funcionalidade de login")
    private String descricao;

    @Schema(description = "Status do checkpoint", example = "true")
    private Boolean finalizado;
}
