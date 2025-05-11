package com.humanconsulting.humancore_api.controller.dto.response.empresa;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmpresaResponseDto {
    @Schema(description = "ID da Empresa", example = "1")
    private Integer idEmpresa;

    @Schema(description = "Nome da Empresa", example = "Human Consulting Ltda.")
    private String nome;

    @Schema(description = "CNPJ da Empresa", example = "12.345.678/0001-90")
    private String cnpj;

    @Schema(description = "Nome do Diretor da Empresa", example = "Carlos Silva")
    private String nomeDiretor;

    @Schema(description = "Indica se a empresa possui impedimentos", example = "false")
    private boolean comImpedimento;

    @Schema(description = "Progresso atual da empresa em porcentagem", example = "75.5")
    private Double progresso;

    @Schema(description = "URL do logo da empresa", example = "http://example.com/imagem-empresa.jpg")
    private String urlImagemEmpresa;

    @Schema(description = "Orçamento total da empresa", example = "5000000.0")
    private Double orcamento;
}
