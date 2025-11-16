package com.humanconsulting.humancore_api.web.dtos.response.global;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BurndownDTO {

    @Schema(description = "Dia no gráfico", example = "1")
    private LocalDate dia;

    @Schema(description = "Quantidade de tarefas no dia", example = "1")
    private long totalTarefas;

    @Schema(description = "Quantidade de tarefas concluídas", example = "1")
    private long tarefasConcluidas;
}