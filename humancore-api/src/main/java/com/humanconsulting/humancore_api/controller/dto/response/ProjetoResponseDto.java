package com.humanconsulting.humancore_api.controller.dto.response;

public class ProjetoResponseDto {

    private Integer idProjeto;

    private String descricao;

    private Double orcamento;

    private String urlImagem;

    private Integer idResponsavel;

    private String nomeResponsavel;

    private double progresso;

    private boolean comImpedimento;

    public ProjetoResponseDto(Integer idProjeto, String descricao, Double orcamento, String urlImagem, Integer idResponsavel, String nomeResponsavel, double progresso, boolean comImpedimento) {
        this.idProjeto = idProjeto;
        this.descricao = descricao;
        this.orcamento = orcamento;
        this.urlImagem = urlImagem;
        this.idResponsavel = idResponsavel;
        this.nomeResponsavel = nomeResponsavel;
        this.progresso = progresso;
        this.comImpedimento = comImpedimento;
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
}
