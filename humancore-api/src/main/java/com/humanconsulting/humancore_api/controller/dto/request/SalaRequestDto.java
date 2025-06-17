package com.humanconsulting.humancore_api.controller.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SalaRequestDto {
    @Schema(description = "Nome da sala", example = "Sala 1")
    @NotNull
    private String nome;

    @Schema(description = "Foto da capa", example = "image")
    private String urlImagem;

    @Schema(description = "Empresa vinculada", example = "1")
    private Integer fkEmpresa;

    @Schema(description = "Projeto vinculado", example = "1")
    private Integer fkProjeto;

    @Schema(description = "Lista de participantes", example = "[1, 2, 3]")
    @NotNull
    private List<Integer> participantes;

    @Schema(description = "Usuário que criou a sala, será cadastrado", example = "1")
    private Integer idEditor;
}
