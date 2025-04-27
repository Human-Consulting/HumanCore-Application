package com.humanconsulting.humancore_api.controller.dto.atualizar.empresa;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class EmpresaAtualizarRequestDto {
    @Schema(description = "CNPJ da empresa", example = "12.345.678/0001-99")
    @NotNull
    private String cnpj;

    @Schema(description = "Nome da empresa", example = "Human Consulting")
    @NotBlank
    private String nome;

    @Schema(description = "ID do usuário que está realizando a requisição (editor)", example = "1")
    @NotNull
    private Integer idEditor;

    @Schema(description = "Permissão do editor", example = "CONSULTOR")
    @NotBlank
    private String permissaoEditor;

    @Schema(description = "URL da imagem de logo da empresa", example = "https://linkdaimagem.com/logo.png")
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
