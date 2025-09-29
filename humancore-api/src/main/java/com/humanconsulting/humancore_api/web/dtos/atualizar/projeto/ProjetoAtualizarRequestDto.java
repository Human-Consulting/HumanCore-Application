package com.humanconsulting.humancore_api.web.dtos.atualizar.projeto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProjetoAtualizarRequestDto {
    @Schema(description = "Título do projeto", example = "Centro Comercial")
    @NotBlank
    private String titulo;

    @Schema(description = "Descrição detalhada do projeto", example = "ProjetoEntity de expansão do centro comercial")
    @NotBlank
    private String descricao;

    @Schema(description = "Responsável pelo projeto", example = "5")
    @NotNull
    private Integer fkResponsavel;

    @Schema(description = "Orçamento alocado para o projeto", example = "100000.00")
    @NotNull
    private Double orcamento;

    @Schema(description = "URL da imagem representativa do projeto", example = "http://exemplo.com/imagem.png")
    public String urlImagem;

    @Schema(description = "ID do usuário que está realizando a requisição (editor)", example = "2")
    @NotNull
    private Integer idEditor;

    @Schema(description = "Permissão do editor", example = "CONSULTOR")
    @NotNull
    private String permissaoEditor;
}
