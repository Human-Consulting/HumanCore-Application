package com.humanconsulting.humancore_api.web.dtos.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TarefaRequestDto {
    @Schema(description = "Título da tarefa", example = "Login")
    @NotNull
    private String titulo;

    @Schema(description = "Descrição da tarefa", example = "Desenvolver a funcionalidade de login")
    @NotNull
    private String descricao;

    @Schema(description = "Data de início da tarefa", example = "2025-05-01")
    @NotNull
    private LocalDate dtInicio;

    @Schema(description = "Data de término da tarefa", example = "2025-05-10")
    @NotNull
    private LocalDate dtFim;

    @Schema(description = "ID da SprintEntity associada à tarefa", example = "2001")
    @NotNull
    private Integer fkSprint;

    @Schema(description = "ID do responsável pela tarefa", example = "150")
    @NotNull
    private Integer fkResponsavel;

    @Schema(description = "ID do usuário que está realizando a requisição (editor)", example = "2")
    @NotNull
    private Integer idEditor;

    @Schema(description = "Permissão do editor", example = "CONSULTOR")
    @NotNull
    private String permissaoEditor;
}
