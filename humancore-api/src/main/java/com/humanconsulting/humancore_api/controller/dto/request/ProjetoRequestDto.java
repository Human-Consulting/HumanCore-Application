package com.humanconsulting.humancore_api.controller.dto.request;

import jakarta.validation.constraints.NotNull;

public class ProjetoRequestDto {

    @NotNull
    private String descricao;

    @NotNull
    private Double orcamento;

    private String urlImagem;

    @NotNull
    private Integer fkEmpresa;

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
