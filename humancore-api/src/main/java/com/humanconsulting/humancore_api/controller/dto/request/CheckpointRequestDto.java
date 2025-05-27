package com.humanconsulting.humancore_api.controller.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CheckpointRequestDto {
    @Schema(description = "Identificador do checkpoint", example = "1")
    private Integer idCheckpoint;

    @Schema(description = "Descrição do checkpoint", example = "Desenvolver a funcionalidade de login")
    @NotNull
    private String descricao;

    @Schema(description = "Status do checkpoint", example = "true")
    @NotNull
    private Boolean finalizado;
}
