package com.humanconsulting.humancore_api.web.dtos.response.projeto;

import com.humanconsulting.humancore_api.web.dtos.response.sprint.SprintBurndownResponseDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProjetoBurndownResponseDto {
    @Schema(description = "ID do projeto", example = "1")
    private Integer idProjeto;

    @Schema(description = "Lista de sprints que contem o burndown")
    List<SprintBurndownResponseDto> sprints;
}
