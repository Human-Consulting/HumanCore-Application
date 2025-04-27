package com.humanconsulting.humancore_api.controller.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class UsuarioRequestDto {
    @Schema(description = "Nome do usuário", example = "João Silva")
    @NotBlank
    private String nome;

    @Schema(description = "Email do usuário", example = "joao.silva@example.com")
    @NotNull
    @Email
    private String email;

    @Schema(description = "Senha do usuário", example = "senha123")
    @NotBlank
    @Size(min = 6)
    private String senha;

    @Schema(description = "Cargo do usuário na empresa", example = "Desenvolvedor Backend")
    @NotBlank
    private String cargo;

    @Schema(description = "Área de atuação do usuário", example = "Tecnologia da Informação")
    @NotBlank
    private String area;

    @Schema(description = "Permissão do usuário", example = "CONSULTOR")
    @NotBlank
    private String permissao;

    @Schema(description = "ID da empresa associada ao usuário", example = "1")
    @NotNull
    private Integer fkEmpresa;

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

    public @NotNull Integer getFkEmpresa() {
        return fkEmpresa;
    }

    public void setFkEmpresa(@NotNull Integer fkEmpresa) {
        this.fkEmpresa = fkEmpresa;
    }
}
