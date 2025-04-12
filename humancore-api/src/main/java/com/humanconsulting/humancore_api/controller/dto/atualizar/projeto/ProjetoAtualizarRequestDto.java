package com.humanconsulting.humancore_api.controller.dto.atualizar.projeto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class ProjetoAtualizarRequestDto {
    @NotBlank
    private String descricao;

    @NotNull
    private Integer fkResponsavel;

    @NotNull
    private Double orcamento;

    public String urlImagem;

    private Integer idEditor;

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

    public Integer getIdEditor() {
        return idEditor;
    }

    public void setIdEditor(Integer idEditor) {
        this.idEditor = idEditor;
    }

    public String getPermissaoEditor() {
        return permissaoEditor;
    }

    public void setPermissaoEditor(String permissaoEditor) {
        this.permissaoEditor = permissaoEditor;
    }
}
