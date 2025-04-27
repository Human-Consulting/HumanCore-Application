package com.humanconsulting.humancore_api.controller.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class EmpresaRequestDto {
    @Schema(description = "CNPJ da empresa", example = "12.345.678/0001-90")
    @NotNull
    private String cnpj;

    @Schema(description = "Nome da empresa", example = "Human Consulting")
    @NotBlank
    private String nome;

    @Schema(description = "URL do logo da empresa", example = "https://www.exemplo.com/imagem.jpg")
    private String urlImagem;

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

    public String getUrlImagem() {
        return urlImagem;
    }

    public void setUrlImagem(String urlImagem) {
        this.urlImagem = urlImagem;
    }
}
