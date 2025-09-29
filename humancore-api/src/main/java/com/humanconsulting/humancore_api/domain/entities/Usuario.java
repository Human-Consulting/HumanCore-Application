package com.humanconsulting.humancore_api.domain.entities;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class Usuario {
    private Integer idUsuario;
    private String nome;
    private String email;
    private String senha;
    private String cargo;
    private String area;
    private String permissao;
    private String cores;
    private Empresa empresa;
    private Set<Sala> salas = new HashSet<>();

    public Usuario() {}

    public Usuario(Integer idUsuario, String nome, String email, String senha, String cargo, String area, String permissao, String cores, Empresa empresa, Set<Sala> salas) {
        this.idUsuario = idUsuario;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.cargo = cargo;
        this.area = area;
        this.permissao = permissao;
        this.cores = cores;
        this.empresa = empresa;
        this.salas = salas;
    }

    public Integer getIdUsuario() { return idUsuario; }
    public void setIdUsuario(Integer idUsuario) { this.idUsuario = idUsuario; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getSenha() { return senha; }
    public void setSenha(String senha) { this.senha = senha; }
    public String getCargo() { return cargo; }
    public void setCargo(String cargo) { this.cargo = cargo; }
    public String getArea() { return area; }
    public void setArea(String area) { this.area = area; }
    public String getPermissao() { return permissao; }
    public void setPermissao(String permissao) { this.permissao = permissao; }
    public String getCores() { return cores; }
    public void setCores(String cores) { this.cores = cores; }
    public Empresa getEmpresa() { return empresa; }
    public void setEmpresa(Empresa empresa) { this.empresa = empresa; }
    public Set<Sala> getSalas() { return salas; }
    public void setSalas(Set<Sala> salas) { this.salas = salas; }

    public void setEmpresa(Optional<Empresa> byId) {
    }
}
