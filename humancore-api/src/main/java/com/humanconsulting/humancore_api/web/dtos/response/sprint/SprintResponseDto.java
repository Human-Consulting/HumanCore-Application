package com.humanconsulting.humancore_api.web.dtos.response.sprint;

import com.humanconsulting.humancore_api.web.dtos.response.tarefa.TarefaResponseDto;
import com.humanconsulting.humancore_api.domain.entities.Projeto;
import com.humanconsulting.humancore_api.web.dtos.response.usuario.UsuarioSprintResponseDto;
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

    @Schema(description = "ID da SprintEntity", example = "1")
    private Integer idSprint;

    @Schema(description = "Título da sprint", example = "Autenticação")
    private String titulo;

    @Schema(description = "Descrição da SprintEntity", example = "SprintEntity de desenvolvimento da API")
    private String descricao;

    @Schema(description = "Data de início da SprintEntity", example = "2025-04-01")
    private LocalDate dtInicio;

    @Schema(description = "Data de término da SprintEntity", example = "2025-04-15")
    private LocalDate dtFim;

    @Schema(description = "Progresso da SprintEntity em percentual", example = "75.5")
    private Double progresso;

    @Schema(description = "Indica se há impedimentos na SprintEntity", example = "true")
    private Boolean comImpedimento;

    @Schema(description = "Lista de tarefas associadas à SprintEntity", implementation = TarefaResponseDto.class)
    private List<TarefaResponseDto> tarefas;
}
