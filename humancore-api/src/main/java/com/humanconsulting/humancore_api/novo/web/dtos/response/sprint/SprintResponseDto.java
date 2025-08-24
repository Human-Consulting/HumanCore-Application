package com.humanconsulting.humancore_api.novo.web.dtos.response.sprint;

import com.humanconsulting.humancore_api.novo.web.dtos.response.tarefa.TarefaResponseDto;
import com.humanconsulting.humancore_api.velho.model.Projeto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SprintResponseDto {

    @Schema(description = "ID da Sprint", example = "1")
    private Integer idSprint;

    @Schema(description = "Título da sprint", example = "Autenticação")
    private String titulo;

    @Schema(description = "Descrição da Sprint", example = "Sprint de desenvolvimento da API")
    private String descricao;

    @Schema(description = "Data de início da Sprint", example = "2025-04-01")
    private LocalDate dtInicio;

    @Schema(description = "Data de término da Sprint", example = "2025-04-15")
    private LocalDate dtFim;

    @Schema(description = "Progresso da Sprint em percentual", example = "75.5")
    private Double progresso;

    @Schema(description = "Indica se há impedimentos na Sprint", example = "true")
    private Boolean comImpedimento;

    @Schema(description = "Projeto associado à Sprint", example = "101")
    private Projeto projeto;

    @Schema(description = "Lista de tarefas associadas à Sprint", implementation = TarefaResponseDto.class)
    private List<TarefaResponseDto> tarefas;
}
