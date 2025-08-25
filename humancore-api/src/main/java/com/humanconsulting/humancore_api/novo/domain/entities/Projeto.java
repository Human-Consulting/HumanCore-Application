package com.humanconsulting.humancore_api.novo.domain.entities;

public class Projeto {

    private Integer idProjeto;

    private String titulo;

    private String descricao;

    private Double orcamento;

    private String urlImagem;

    private Empresa empresa;

    private Usuario responsavel;

    public Projeto() {}

    public Projeto(Integer idProjeto, String titulo, String descricao, Double orcamento, String urlImagem, Empresa empresa, Usuario responsavel) {
        this.idProjeto = idProjeto;
        this.titulo = titulo;
        this.descricao = descricao;
        this.orcamento = orcamento;
        this.urlImagem = urlImagem;
        this.empresa = empresa;
        this.responsavel = responsavel;
    }

    public Integer getIdProjeto() { return idProjeto; }
    public void setIdProjeto(Integer idProjeto) { this.idProjeto = idProjeto; }
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
    public Double getOrcamento() { return orcamento; }
    public void setOrcamento(Double orcamento) { this.orcamento = orcamento; }
    public String getUrlImagem() { return urlImagem; }
    public void setUrlImagem(String urlImagem) { this.urlImagem = urlImagem; }
    public Empresa getEmpresa() { return empresa; }
    public void setEmpresa(Empresa empresa) { this.empresa = empresa; }
    public Usuario getResponsavel() { return responsavel; }
    public void setResponsavel(Usuario responsavel) { this.responsavel = responsavel; }
}
