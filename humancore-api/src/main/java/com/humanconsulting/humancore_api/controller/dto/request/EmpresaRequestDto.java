package com.humanconsulting.humancore_api.controller.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmpresaRequestDto {
    @Schema(description = "CNPJ da empresa", example = "12.345.678/0001-90")
    @NotNull
    private String cnpj;

    @Schema(description = "Nome da empresa", example = "Human Consulting")
    @NotBlank
    private String nome;

    @Schema(description = "URL do logo da empresa", example = "https://www.exemplo.com/imagem.jpg")
    private String urlImagem;

    @Schema(description = "ID do usuário que está realizando a requisição (editor)", example = "2")
    @NotNull
    private Integer idEditor;

    @Schema(description = "Permissão do editor", example = "CONSULTOR")
    @NotNull
    private String permissaoEditor;
}
