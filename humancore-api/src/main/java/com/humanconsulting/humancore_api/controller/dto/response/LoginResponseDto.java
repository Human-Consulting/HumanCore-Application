package com.humanconsulting.humancore_api.controller.dto.response;

public class LoginResponseDto {
    private Integer idUsuario;
    private String nome;
    private String permissao;
    private Integer fkEmpresa;
    private String nomeEmpresa;

    public LoginResponseDto(Integer idUsuario, String nome, String permissao, Integer fkEmpresa, String nomeEmpresa) {
        this.idUsuario = idUsuario;
        this.nome = nome;
        this.permissao = permissao;
        this.fkEmpresa = fkEmpresa;
        this.nomeEmpresa = nomeEmpresa;
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

    public String getPermissao() {
        return permissao;
    }

    public void setPermissao(String permissao) {
        this.permissao = permissao;
    }

    public Integer getFkEmpresa() {
        return fkEmpresa;
    }

    public void setFkEmpresa(Integer fkEmpresa) {
        this.fkEmpresa = fkEmpresa;
    }

    public String getNomeEmpresa() {
        return nomeEmpresa;
    }

    public void setNomeEmpresa(String nomeEmpresa) {
        this.nomeEmpresa = nomeEmpresa;
    }
}
