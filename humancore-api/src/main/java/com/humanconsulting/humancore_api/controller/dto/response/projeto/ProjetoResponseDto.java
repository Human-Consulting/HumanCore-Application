package com.humanconsulting.humancore_api.controller.dto.response.projeto;

public class ProjetoResponseDto {

    private Integer idProjeto;

    private String descricao;

    private Double orcamento;

    private String urlImagem;

    private String urlImagemEmpresa;

    private Integer idResponsavel;

    private String nomeResponsavel;

    private double progresso;

    private boolean comImpedimento;

    private Integer fkEmpresa;

    public ProjetoResponseDto(Integer idProjeto, String descricao, Double orcamento, String urlImagem, String urlImagemEmpresa, Integer idResponsavel, String nomeResponsavel, double progresso, boolean comImpedimento, Integer fkEmpresa) {
        this.idProjeto = idProjeto;
        this.descricao = descricao;
        this.orcamento = orcamento;
        this.urlImagem = urlImagem;
        this.urlImagemEmpresa = urlImagemEmpresa;
        this.idResponsavel = idResponsavel;
        this.nomeResponsavel = nomeResponsavel;
        this.progresso = progresso;
        this.comImpedimento = comImpedimento;
        this.fkEmpresa = fkEmpresa;
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

    public String getUrlImagemEmpresa() {
        return urlImagemEmpresa;
    }

    public void setUrlImagemEmpresa(String urlImagemEmpresa) {
        this.urlImagemEmpresa = urlImagemEmpresa;
    }

    public Integer getIdResponsavel() {
        return idResponsavel;
    }

    public void setIdResponsavel(Integer idResponsavel) {
        this.idResponsavel = idResponsavel;
    }

    public String getNomeResponsavel() {
        return nomeResponsavel;
    }

    public void setNomeResponsavel(String nomeResponsavel) {
        this.nomeResponsavel = nomeResponsavel;
    }

    public double getProgresso() {
        return progresso;
    }

    public void setProgresso(double progresso) {
        this.progresso = progresso;
    }

    public boolean isComImpedimento() {
        return comImpedimento;
    }

    public void setComImpedimento(boolean comImpedimento) {
        this.comImpedimento = comImpedimento;
    }

    public Integer getFkEmpresa() {
        return fkEmpresa;
    }

    public void setFkEmpresa(Integer fkEmpresa) {
        this.fkEmpresa = fkEmpresa;
    }
}
