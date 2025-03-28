package com.humanconsulting.humancore_api.controller.dto.response;

import com.humanconsulting.humancore_api.model.Usuario;

public class UsuarioResponseDto {
    private Integer idUsuario;
    private String nome;
    private String email;
    private String cargo;
    private String area;
    private Integer fkEmpresa;

    public UsuarioResponseDto() {
    }

    public UsuarioResponseDto(Integer idUsuario, String nome, String email, String cargo, String area, Integer fkEmpresa) {
        this.idUsuario = idUsuario;
        this.nome = nome;
        this.email = email;
        this.cargo = cargo;
        this.area = area;
        this.fkEmpresa = fkEmpresa;
    }

    public static UsuarioResponseDto toResponse(Usuario usuario) {
        UsuarioResponseDto dto = new UsuarioResponseDto();
        dto.setIdUsuario(usuario.getIdUsuario());
        dto.setNome(usuario.getNome());
        dto.setEmail(usuario.getEmail());
        dto.setCargo(usuario.getCargo());
        dto.setArea(usuario.getArea());
        dto.setFkEmpresa(usuario.getFkEmpresa());
        return dto;
    }

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public Integer getFkEmpresa() {
        return fkEmpresa;
    }

    public void setFkEmpresa(Integer fkEmpresa) {
        this.fkEmpresa = fkEmpresa;
    }
}
