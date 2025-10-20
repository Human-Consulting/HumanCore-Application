package com.humanconsulting.humancore_api.domain.entities;

public class Empresa {

    private Integer idEmpresa;

    private String nome;

    private String cnpj;

    private String urlImagem;

    public Empresa() {}

    public Empresa(Integer idEmpresa, String nome, String cnpj, String urlImagem) {
        this.idEmpresa = idEmpresa;
        this.nome = nome;
        this.cnpj = cnpj;
        this.urlImagem = urlImagem;
    }

    public Integer getIdEmpresa() { return idEmpresa; }
    public void setIdEmpresa(Integer idEmpresa) { this.idEmpresa = idEmpresa; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getCnpj() { return cnpj; }
    public void setCnpj(String cnpj) { this.cnpj = cnpj; }
    public String getUrlImagem() { return urlImagem; }
    public void setUrlImagem(String urlImagem) { this.urlImagem = urlImagem; }
}
