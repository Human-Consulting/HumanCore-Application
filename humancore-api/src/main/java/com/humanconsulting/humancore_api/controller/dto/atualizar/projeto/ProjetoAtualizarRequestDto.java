package com.humanconsulting.humancore_api.controller.dto.atualizar.projeto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class ProjetoAtualizarRequestDto {
    @Schema(description = "Descrição detalhada do projeto", example = "Projeto de expansão do centro comercial")
    @NotBlank
    private String descricao;

    @Schema(description = "ID do responsável pelo projeto", example = "5")
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

    public @NotBlank String getDescricao() {
        return descricao;
    }

    public void setDescricao(@NotBlank String descricao) {
        this.descricao = descricao;
    }

    public @NotNull Integer getFkResponsavel() {
        return fkResponsavel;
    }

    public void setFkResponsavel(@NotNull Integer fkResponsavel) {
        this.fkResponsavel = fkResponsavel;
    }

    public @NotNull Double getOrcamento() {
        return orcamento;
    }

    public void setOrcamento(@NotNull Double orcamento) {
        this.orcamento = orcamento;
    }

    public String getUrlImagem() {
        return urlImagem;
    }

    public void setUrlImagem(String urlImagem) {
        this.urlImagem = urlImagem;
    }

    public @NotNull Integer getIdEditor() {
        return idEditor;
    }

    public void setIdEditor(@NotNull Integer idEditor) {
        this.idEditor = idEditor;
    }

    public @NotNull String getPermissaoEditor() {
        return permissaoEditor;
    }

    public void setPermissaoEditor(@NotNull String permissaoEditor) {
        this.permissaoEditor = permissaoEditor;
    }
}
