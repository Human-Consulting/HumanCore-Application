package com.humanconsulting.humancore_api.controller.dto.response.sala;

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

    @Schema(description = "Nome da sala", example = "Sala 1")
    private String nome;
}