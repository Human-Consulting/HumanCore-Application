package com.humanconsulting.humancore_api.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
public class Projeto {

    @Id
    private Integer idProjeto;

    private String descricao;

    private Double orcamento;

    private String urlImagem;

    private Integer fkEmpresa;

    private Integer fkResponsavel;

    public Projeto(Integer idProjeto, String descricao, Double orcamento, String urlImagem, Integer fkEmpresa, Integer fkResponsavel) {
        this.idProjeto = idProjeto;
        this.descricao = descricao;
        this.orcamento = orcamento;
        this.urlImagem = urlImagem;
        this.fkEmpresa = fkEmpresa;
        this.fkResponsavel = fkResponsavel;
    }

    public Integer getIdProjeto() {
        return idProjeto;
    }

    public void setIdProjeto(Integer idProjeto) {
        this.idProjeto = idProjeto;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Double getOrcamento() {
        return orcamento;
    }

    public void setOrcamento(Double orcamento) {
        this.orcamento = orcamento;
    }

    public String getUrlImagem() {
        return urlImagem;
    }

    public void setUrlImagem(String urlImagem) {
        this.urlImagem = urlImagem;
    }

    public Integer getFkEmpresa() {
        return fkEmpresa;
    }

    public void setFkEmpresa(Integer fkEmpresa) {
        this.fkEmpresa = fkEmpresa;
    }

    public Integer getFkResponsavel() {
        return fkResponsavel;
    }

    public void setFkResponsavel(Integer fkResponsavel) {
        this.fkResponsavel = fkResponsavel;
    }
}
