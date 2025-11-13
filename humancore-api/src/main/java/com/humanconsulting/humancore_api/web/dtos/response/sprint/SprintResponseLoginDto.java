package com.humanconsulting.humancore_api.web.dtos.response.sprint;

import com.humanconsulting.humancore_api.web.dtos.response.projeto.ProjetoResponseLoginDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SprintResponseLoginDto {
    @Schema(description = "ID da sprint", example = "1")
    private Integer idSprint;

    @Schema(description = "Título da sprint", example = "Centro Comercial")
    private String titulo;

    @Schema(description = "ProjetoEntity associada à TarefaEntity", example = "2")
    private ProjetoResponseLoginDto projeto;
}
