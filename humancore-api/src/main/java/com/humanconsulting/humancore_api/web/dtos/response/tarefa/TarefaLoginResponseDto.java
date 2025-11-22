package com.humanconsulting.humancore_api.web.dtos.response.tarefa;

import com.humanconsulting.humancore_api.web.dtos.response.checkpoint.CheckpointResponseDto;
import com.humanconsulting.humancore_api.web.dtos.response.sprint.SprintResponseLoginDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TarefaLoginResponseDto {

    @Schema(description = "Título da tarefa", example = "Login")
    private String titulo;

    @Schema(description = "Data de término da TarefaEntity", example = "2025-04-10")
    private LocalDate dtFim;

    @Schema(description = "Progresso da TarefaEntity em percentual", example = "50.0")
    private Double progresso;

    @Schema(description = "Indica se há impedimentos na TarefaEntity", example = "false")
    private Boolean comImpedimento;

    @Schema(description = "SprintEntity associada à TarefaEntity", example = "2")
    private SprintResponseLoginDto sprint;

    @Schema(description = "Checkpoints da tarefa", example = "[{id: 1, descricao: 'Subtarefa', finalizado: true}]")
    private List<CheckpointResponseDto> checkpoints;

    @Schema(description = "ID do evento para mapeamento do Google Calendar", example = "evento12345")
    private String googleCalendarEventId;
}
