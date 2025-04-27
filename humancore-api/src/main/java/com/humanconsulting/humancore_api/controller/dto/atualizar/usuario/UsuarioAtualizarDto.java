package com.humanconsulting.humancore_api.controller.dto.atualizar.usuario;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class UsuarioAtualizarDto {

    @Schema(description = "Nome completo do usuário", example = "João Silva")
    @NotBlank
    private String nome;

    @Schema(description = "E-mail do usuário", example = "joao.silva@email.com")
    @NotNull
    @Email
    private String email;

    @Schema(description = "Senha do usuário (mínimo de 6 caracteres)", example = "senha123")
    @NotBlank
    @Size(min = 6)
    private String senha;

    @Schema(description = "Cargo do usuário na empresa", example = "Desenvolvedor")
    @NotBlank
    private String cargo;

    @Schema(description = "Área de atuação do usuário", example = "Tecnologia")
    @NotBlank
    private String area;

    @Schema(description = "Permissão atribuída ao usuário", example = "CONSULTOR")
    @NotBlank
    private String permissao;

    @Schema(description = "ID do usuário que está realizando a requisição (editor)", example = "2")
    @NotNull
    private Integer idEditor;

    @Schema(description = "Permissão do editor", example = "CONSULTOR")
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
