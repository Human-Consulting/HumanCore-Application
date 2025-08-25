package com.humanconsulting.humancore_api.velho.controller.dto.response.projeto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProjetoResponseDto {
    @Schema(description = "ID do projeto", example = "1")
    private Integer idProjeto;

    @Schema(description = "Título do projeto", example = "Centro Comercial")
    private String titulo;

    @Schema(description = "Descrição do projeto", example = "ProjetoEntity de construção de um novo edifício comercial")
    private String descricao;

    @Schema(description = "Valor do orçamento do projeto", example = "5000000.00")
    private Double orcamento;

    @Schema(description = "URL da imagem do projeto", example = "https://example.com/imagem-do-projeto.jpg")
    private String urlImagem;

    @Schema(description = "URL do logo da empresa responsável pelo projeto", example = "https://example.com/imagem-da-empresa.jpg")
    private String urlImagemEmpresa;

    @Schema(description = "Identificador do responsável pelo projeto", example = "1")
    private Integer fkResponsavel;

    @Schema(description = "Responsável pelo projeto", example = "Paulo")
    private String nomeResponsavel;

    @Schema(description = "Progresso atual do projeto em porcentagem", example = "75.5")
    private double progresso;

    @Schema(description = "Indica se o projeto possui algum impedimento", example = "false")
    private boolean comImpedimento;
}
