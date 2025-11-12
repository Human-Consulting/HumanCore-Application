package com.humanconsulting.humancore_api.web.dtos.response.sprint;

import com.humanconsulting.humancore_api.web.dtos.response.global.BurndownDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SprintBurndownResponseDto {
    @Schema(description = "ID da sprint", example = "1")
    private Integer idSprint;

    @Schema(description = "Título da sprint", example = "Nova sprint")
    private String titulo;

    @Schema(description = "Lista com total de tarefas e total de tarefas concluídas POR DIA")
    List<BurndownDTO> burndown;
}
