package com.humanconsulting.humancore_api.controller.dto.response.usuario;

public class UsuarioResponseDto {

    private Integer idUsuario;

    private String nome;

    private String email;

    private String senha;

    private String cargo;

    private String area;

    private String permissao;

    private Integer qtdTarefas;

    private Boolean comImpedimento;

    public UsuarioResponseDto(Integer idUsuario, String nome, String email, String senha, String cargo, String area, String permissao, Integer qtdTarefas, Boolean comImpedimento) {
        this.idUsuario = idUsuario;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.cargo = cargo;
        this.area = area;
        this.permissao = permissao;
        this.qtdTarefas = qtdTarefas;
        this.comImpedimento = comImpedimento;
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

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
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

    public String getPermissao() {
        return permissao;
    }

    public void setPermissao(String permissao) {
        this.permissao = permissao;
    }

    public Integer getQtdTarefas() {
        return qtdTarefas;
    }

    public void setQtdTarefas(Integer qtdTarefas) {
        this.qtdTarefas = qtdTarefas;
    }

    public Boolean getComImpedimento() {
        return comImpedimento;
    }

    public void setComImpedimento(Boolean comImpedimento) {
        this.comImpedimento = comImpedimento;
    }
}
