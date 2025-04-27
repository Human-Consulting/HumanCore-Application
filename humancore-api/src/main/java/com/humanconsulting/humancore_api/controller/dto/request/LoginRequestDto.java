package com.humanconsulting.humancore_api.controller.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class LoginRequestDto {
    @Schema(description = "Endereço de email do usuário", example = "usuario@exemplo.com")
    @Email
    @NotBlank
    private String email;

    @Schema(description = "Senha do usuário", example = "senha123")
    @NotBlank
    private String senha;

    public @Email @NotBlank String getEmail() {
        return email;
    }

    public void setEmail(@Email @NotBlank String email) {
        this.email = email;
    }

    public @NotBlank String getSenha() {
        return senha;
    }

    public void setSenha(@NotBlank String senha) {
        this.senha = senha;
    }
}
