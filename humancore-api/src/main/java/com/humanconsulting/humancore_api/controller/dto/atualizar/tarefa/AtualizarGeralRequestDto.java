package com.humanconsulting.humancore_api.controller.dto.atualizar.tarefa;

import com.humanconsulting.humancore_api.model.Usuario;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AtualizarGeralRequestDto {
    @Schema(description = "Descrição da tarefa", example = "Desenvolver a funcionalidade de login")
    @NotNull
    private String descricao;

    @Schema(description = "Data de início da tarefa", example = "2025-05-01")
    @NotNull
    private LocalDate dtInicio;

    @Schema(description = "Data de término da tarefa", example = "2025-05-10")
    @NotNull
    private LocalDate dtFim;

    @Schema(description = "Progresso da tarefa em porcentagem", example = "75.5")
    @NotNull
    private Double progresso;

    @Schema(description = "Usuário responsável pela tarefa", example = "3")
    @NotNull
    private Usuario responsavel;

    @Schema(description = "ID do usuário que está realizando a requisição (editor)", example = "2")
    @NotNull
    private Integer idEditor;
}
