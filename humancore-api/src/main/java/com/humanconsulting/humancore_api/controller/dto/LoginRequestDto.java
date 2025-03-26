package com.humanconsulting.humancore_api.controller.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class LoginRequestDto {
    @Email
    @NotBlank
    private String usuario;

    @NotBlank
    private String senha;

    public LoginRequestDto() {
    }

    public LoginRequestDto(String usuario, String senha) {
        this.usuario = usuario;
        this.senha = senha;
    }

    public @Email @NotBlank String getUsuario() {
        return usuario;
    }

    public void setUsuario(@Email @NotBlank String usuario) {
        this.usuario = usuario;
    }

    public @NotBlank String getSenha() {
        return senha;
    }

    public void setSenha(@NotBlank String senha) {
        this.senha = senha;
    }
}
