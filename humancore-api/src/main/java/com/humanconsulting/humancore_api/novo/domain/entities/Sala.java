package com.humanconsulting.humancore_api.novo.domain.entities;

import java.util.HashSet;
import java.util.Set;

public class Sala {
    private Integer idSala;
    private String nome;
    private String urlImagem;
    private Projeto projeto;
    private Empresa empresa;
    private Set<Usuario> usuarios = new HashSet<>();

    public Sala() {}

    public Sala(Integer idSala, String nome, String urlImagem, Projeto projeto, Empresa empresa, Set<Usuario> usuarios) {
        this.idSala = idSala;
        this.nome = nome;
        this.urlImagem = urlImagem;
        this.projeto = projeto;
        this.empresa = empresa;
        this.usuarios = usuarios;
    }

    public Integer getIdSala() { return idSala; }
    public void setIdSala(Integer idSala) { this.idSala = idSala; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getUrlImagem() { return urlImagem; }
    public void setUrlImagem(String urlImagem) { this.urlImagem = urlImagem; }
    public Projeto getProjeto() { return projeto; }
    public void setProjeto(Projeto projeto) { this.projeto = projeto; }
    public Empresa getEmpresa() { return empresa; }
    public void setEmpresa(Empresa empresa) { this.empresa = empresa; }
    public Set<Usuario> getUsuarios() { return usuarios; }
    public void setUsuarios(Set<Usuario> usuarios) { this.usuarios = usuarios; }
}