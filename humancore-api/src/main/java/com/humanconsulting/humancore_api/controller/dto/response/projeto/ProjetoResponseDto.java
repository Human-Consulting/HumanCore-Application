package com.humanconsulting.humancore_api.controller.dto.response.projeto;

import io.swagger.v3.oas.annotations.media.Schema;

public class ProjetoResponseDto {
    @Schema(description = "ID do projeto", example = "1")
    private Integer idProjeto;

    @Schema(description = "Descrição do projeto", example = "Projeto de construção de um novo edifício comercial")
    private String descricao;

    @Schema(description = "Valor do orçamento do projeto", example = "5000000.00")
    private Double orcamento;

    @Schema(description = "URL da imagem do projeto", example = "https://example.com/imagem-do-projeto.jpg")
    private String urlImagem;

    @Schema(description = "URL do logo da empresa responsável pelo projeto", example = "https://example.com/imagem-da-empresa.jpg")
    private String urlImagemEmpresa;

    @Schema(description = "ID do responsável pelo projeto", example = "10")
    private Integer idResponsavel;

    @Schema(description = "Nome do responsável pelo projeto", example = "João Silva")
    private String nomeResponsavel;

    @Schema(description = "Progresso atual do projeto em porcentagem", example = "75.5")
    private double progresso;

    @Schema(description = "Indica se o projeto possui algum impedimento", example = "false")
    private boolean comImpedimento;

    @Schema(description = "ID da empresa responsável pelo projeto", example = "5")
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
