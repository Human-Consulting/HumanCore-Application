package com.humanconsulting.humancore_api.controller.dto.atualizar.empresa;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class EmpresaAtualizarRequestDto {
    @NotNull
    private String cnpj;

    @NotBlank
    private String nome;

    @NotNull
    private Integer idEditor;

    @NotBlank
    private String permissaoEditor;

    private String urlImagem;

    public @NotNull Integer getIdEditor() {
        return idEditor;
    }

    public void setIdEditor(@NotNull Integer idEditor) {
        this.idEditor = idEditor;
    }

    public @NotNull String getCnpj() {
        return cnpj;
    }

    public void setCnpj(@NotNull String cnpj) {
        this.cnpj = cnpj;
    }

    public @NotBlank String getNome() {
        return nome;
    }

    public void setNome(@NotBlank String nome) {
        this.nome = nome;
    }

    public @NotBlank String getPermissaoEditor() {
        return permissaoEditor;
    }

    public void setPermissaoEditor(@NotBlank String permissaoEditor) {
        this.permissaoEditor = permissaoEditor;
    }

    public String getUrlImagem() {
        return urlImagem;
    }

    public void setUrlImagem(String urlImagem) {
        this.urlImagem = urlImagem;
    }
}
