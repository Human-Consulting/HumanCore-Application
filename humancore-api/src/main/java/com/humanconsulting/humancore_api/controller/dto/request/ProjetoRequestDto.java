package com.humanconsulting.humancore_api.controller.dto.request;

import com.humanconsulting.humancore_api.model.Empresa;
import com.humanconsulting.humancore_api.model.Usuario;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProjetoRequestDto {

    @Schema(description = "Descrição do projeto", example = "Projeto de expansão imobiliária")
    @NotNull
    private String descricao;

    @Schema(description = "Orçamento do projeto", example = "1500000.00")
    @NotNull
    private Double orcamento;

    @Schema(description = "URL da imagem associada ao projeto", example = "http://exemplo.com/imagem.jpg")
    private String urlImagem;

    @Schema(description = "Empresa associada ao projeto", example = "1")
    @NotNull
    private Empresa empresa;

    @Schema(description = "Responsável pelo projeto", example = "3")
    @NotNull
    private Usuario responsavel;
}
