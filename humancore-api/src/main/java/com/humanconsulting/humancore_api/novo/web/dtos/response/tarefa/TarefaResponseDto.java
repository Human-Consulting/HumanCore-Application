package com.humanconsulting.humancore_api.novo.web.dtos.response.tarefa;

import com.humanconsulting.humancore_api.novo.web.dtos.response.checkpoint.CheckpointResponseDto;
import com.humanconsulting.humancore_api.novo.domain.entities.Sprint;
import com.humanconsulting.humancore_api.novo.domain.entities.Usuario;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TarefaResponseDto {

    @Schema(description = "ID da TarefaEntity", example = "1")
    private Integer idTarefa;

    @Schema(description = "Título da tarefa", example = "Login")
    private String titulo;

    @Schema(description = "Descrição da TarefaEntity", example = "Desenvolver API para gestão de tarefas")
    private String descricao;

    @Schema(description = "Data de início da TarefaEntity", example = "2025-04-01")
    private LocalDate dtInicio;

    @Schema(description = "Data de término da TarefaEntity", example = "2025-04-10")
    private LocalDate dtFim;

    @Schema(description = "Progresso da TarefaEntity em percentual", example = "50.0")
    private Double progresso;

    @Schema(description = "Indica se há impedimentos na TarefaEntity", example = "false")
    private Boolean comImpedimento;

    @Schema(description = "SprintEntity associada à TarefaEntity", example = "2")
    private Sprint sprint;

    @Schema(description = "Usuário responsável pela TarefaEntity", example = "101")
    private Usuario responsavel;

    @Schema(description = "Comentário da TarefaEntity", example = "Impedimento referente a...")
    private String comentario;

    @Schema(description = "Checkpoints da tarefa", example = "[{id: 1, descricao: 'Subtarefa', finalizado: true}]")
    private List<CheckpointResponseDto> checkpoints;
}
