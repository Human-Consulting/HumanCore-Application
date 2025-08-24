package com.humanconsulting.humancore_api.velho.controller.dto.atualizar.sprint;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SprintAtualizarRequestDto {
    @Schema(description = "Título da sprint", example = "Autenticação")
    @NotNull
    private String titulo;

    @Schema(description = "Descrição da sprint", example = "Sprint de desenvolvimento da nova funcionalidade de autenticação")
    @NotNull
    private String descricao;

    @Schema(description = "Data de início da sprint", example = "2025-05-01")
    @NotNull
    private LocalDate dtInicio;

    @Schema(description = "Data de término da sprint", example = "2025-05-15")
    @NotNull
    private LocalDate dtFim;

    @Schema(description = "ID do usuário que está realizando a requisição (editor)", example = "2")
    @NotNull
    private Integer idEditor;

    @Schema(description = "Permissão do editor", example = "CONSULTOR")
    @NotNull
    private String permissaoEditor;
}
