package com.humanconsulting.humancore_api.controller.dto.response.projeto;

import com.humanconsulting.humancore_api.model.Empresa;
import com.humanconsulting.humancore_api.model.Usuario;
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

    @Schema(description = "Descrição do projeto", example = "Projeto de construção de um novo edifício comercial")
    private String descricao;

    @Schema(description = "Valor do orçamento do projeto", example = "5000000.00")
    private Double orcamento;

    @Schema(description = "URL da imagem do projeto", example = "https://example.com/imagem-do-projeto.jpg")
    private String urlImagem;

    @Schema(description = "URL do logo da empresa responsável pelo projeto", example = "https://example.com/imagem-da-empresa.jpg")
    private String urlImagemEmpresa;

    @Schema(description = "Responsável pelo projeto", example = "10")
    private Usuario responsavel;

    @Schema(description = "Progresso atual do projeto em porcentagem", example = "75.5")
    private double progresso;

    @Schema(description = "Indica se o projeto possui algum impedimento", example = "false")
    private boolean comImpedimento;

    @Schema(description = "Empresa responsável pelo projeto", example = "5")
    private Empresa empresa;
}
