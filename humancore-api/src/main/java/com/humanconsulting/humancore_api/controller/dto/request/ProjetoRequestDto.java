package com.humanconsulting.humancore_api.controller.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

public class ProjetoRequestDto {

    @Schema(description = "Descrição do projeto", example = "Projeto de expansão imobiliária")
    @NotNull
    private String descricao;

    @Schema(description = "Orçamento do projeto", example = "1500000.00")
    @NotNull
    private Double orcamento;

    @Schema(description = "URL da imagem associada ao projeto", example = "http://exemplo.com/imagem.jpg")
    private String urlImagem;

    @Schema(description = "ID da empresa associada ao projeto", example = "1")
    @NotNull
    private Integer fkEmpresa;

    @Schema(description = "ID do responsável pelo projeto", example = "3")
    @NotNull
    private Integer fkResponsavel;

    public @NotNull String getDescricao() {
        return descricao;
    }

    public void setDescricao(@NotNull String descricao) {
        this.descricao = descricao;
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

    public @NotNull Integer getFkEmpresa() {
        return fkEmpresa;
    }

    public void setFkEmpresa(@NotNull Integer fkEmpresa) {
        this.fkEmpresa = fkEmpresa;
    }

    public @NotNull Integer getFkResponsavel() {
        return fkResponsavel;
    }

    public void setFkResponsavel(@NotNull Integer fkResponsavel) {
        this.fkResponsavel = fkResponsavel;
    }
}
