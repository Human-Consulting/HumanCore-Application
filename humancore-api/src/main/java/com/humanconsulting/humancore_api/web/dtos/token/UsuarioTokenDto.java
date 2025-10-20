package com.humanconsulting.humancore_api.web.dtos.token;

public class UsuarioTokenDto {
    private Integer idUsuario;
    private String nome;
    private String email;
    private String token;

    public Integer getIdUsuario() { return idUsuario; }

    public void setIdUsuario(Integer idUsuario) { this.idUsuario = idUsuario; }

    public String getNome() { return nome; }

    public void setNome(String nome) { this.nome = nome; }

    public String getEmail() { return email; }

    public void setEmail(String email) { this.email = email; }

    public String getToken() { return token; }

    public void setToken(String token) { this.token = token; }
}
