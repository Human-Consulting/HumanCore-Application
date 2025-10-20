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
public class SprintRequestDto {
    @Schema(description = "Título da sprint", example = "Autenticação")
    @NotNull
    private String titulo;

    @Schema(description = "Descrição da SprintEntity", example = "SprintEntity de desenvolvimento de funcionalidades para a versão 1.0")
    @NotNull
    private String descricao;

    @Schema(description = "Data de início da SprintEntity", example = "2025-05-01")
    @NotNull
    private LocalDate dtInicio;

    @Schema(description = "Data de término da SprintEntity", example = "2025-05-15")
    @NotNull
    private LocalDate dtFim;

    @Schema(description = "ProjetoEntity ao qual a SprintEntity pertence", example = "1001")
    @NotNull
    private Integer fkProjeto;

    @Schema(description = "ID do usuário que está realizando a requisição (editor)", example = "2")
    @NotNull
    private Integer idEditor;

    @Schema(description = "Permissão do editor", example = "CONSULTOR")
    @NotNull
    private String permissaoEditor;
}
