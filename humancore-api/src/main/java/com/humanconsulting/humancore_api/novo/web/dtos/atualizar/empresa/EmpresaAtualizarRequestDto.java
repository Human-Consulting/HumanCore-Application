package com.humanconsulting.humancore_api.novo.web.dtos.atualizar.empresa;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmpresaAtualizarRequestDto {
    @Schema(description = "CNPJ da empresa", example = "12.345.678/0001-99")
    @NotNull
    private String cnpj;

    @Schema(description = "Nome da empresa", example = "Human Consulting")
    @NotBlank
    private String nome;

    @Schema(description = "ID do usuário que está realizando a requisição (editor)", example = "1")
    @NotNull
    private Integer idEditor;

    @Schema(description = "Permissão do editor", example = "CONSULTOR")
    @NotBlank
    private String permissaoEditor;

    @Schema(description = "URL da imagem de logo da empresa", example = "https://linkdaimagem.com/logo.png")
    private String urlImagem;
}
