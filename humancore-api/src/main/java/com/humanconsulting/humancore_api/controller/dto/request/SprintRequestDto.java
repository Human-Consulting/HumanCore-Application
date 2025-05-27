package com.humanconsulting.humancore_api.controller.dto.request;

import com.humanconsulting.humancore_api.model.Projeto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
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

    @Schema(description = "Descrição da Sprint", example = "Sprint de desenvolvimento de funcionalidades para a versão 1.0")
    @NotNull
    private String descricao;

    @Schema(description = "Data de início da Sprint", example = "2025-05-01")
    @NotNull
    private LocalDate dtInicio;

    @Schema(description = "Data de término da Sprint", example = "2025-05-15")
    @NotNull
    private LocalDate dtFim;

    @Schema(description = "Projeto ao qual a Sprint pertence", example = "1001")
    @NotNull
    private Integer fkProjeto;

    @Schema(description = "ID do usuário que está realizando a requisição (editor)", example = "2")
    @NotNull
    private Integer idEditor;

    @Schema(description = "Permissão do editor", example = "CONSULTOR")
    @NotNull
    private String permissaoEditor;
}
