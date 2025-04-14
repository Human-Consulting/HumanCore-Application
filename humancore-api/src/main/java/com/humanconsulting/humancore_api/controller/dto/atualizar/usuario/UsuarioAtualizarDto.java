package com.humanconsulting.humancore_api.controller.dto.atualizar.usuario;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class UsuarioAtualizarDto {

    @NotBlank
    private String nome;

    @NotNull
    @Email
    private String email;

    @NotBlank
    @Size(min = 6)
    private String senha;

    @NotBlank
    private String cargo;

    @NotBlank
    private String area;

    @NotBlank
    private String permissao;

    @NotNull
    private Integer idEditor;

    @NotNull
    private String permissaoEditor;

    public @NotBlank String getNome() {
        return nome;
    }

    public void setNome(@NotBlank String nome) {
        this.nome = nome;
    }

    public @NotNull @Email String getEmail() {
        return email;
    }

    public void setEmail(@NotNull @Email String email) {
        this.email = email;
    }

    public @NotBlank @Size(min = 6) String getSenha() {
        return senha;
    }

    public void setSenha(@NotBlank @Size(min = 6) String senha) {
        this.senha = senha;
    }

    public @NotBlank String getCargo() {
        return cargo;
    }

    public void setCargo(@NotBlank String cargo) {
        this.cargo = cargo;
    }

    public @NotBlank String getArea() {
        return area;
    }

    public void setArea(@NotBlank String area) {
        this.area = area;
    }

    public @NotBlank String getPermissao() {
        return permissao;
    }

    public void setPermissao(@NotBlank String permissao) {
        this.permissao = permissao;
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
