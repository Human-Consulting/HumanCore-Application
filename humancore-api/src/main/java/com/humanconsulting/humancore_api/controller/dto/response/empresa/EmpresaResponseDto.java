package com.humanconsulting.humancore_api.controller.dto.response.empresa;

public class EmpresaResponseDto {
    private Integer idEmpresa;

    private String nome;

    private String cnpj;

    private String nomeDiretor;

    private boolean comImpedimento;

    private Double progresso;

    private String urlImagemEmpresa;

    private Double orcamento;

    public EmpresaResponseDto(Integer idEmpresa, String nome, String cnpj, String nomeDiretor, boolean comImpedimento, Double progreso, String urlImagemEmpresa, Double orcamento) {
        this.idEmpresa = idEmpresa;
        this.nome = nome;
        this.cnpj = cnpj;
        this.nomeDiretor = nomeDiretor;
        this.comImpedimento = comImpedimento;
        this.progresso = progreso;
        this.urlImagemEmpresa = urlImagemEmpresa;
        this.orcamento = orcamento;
    }

    public Integer getIdEmpresa() {
        return idEmpresa;
    }

    public void setIdEmpresa(Integer idEmpresa) {
        this.idEmpresa = idEmpresa;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getNomeDiretor() {
        return nomeDiretor;
    }

    public void setNomeDiretor(String nomeDiretor) {
        this.nomeDiretor = nomeDiretor;
    }

    public boolean isComImpedimento() {
        return comImpedimento;
    }

    public void setComImpedimento(boolean comImpedimento) {
        this.comImpedimento = comImpedimento;
    }

    public Double getProgresso() {
        return progresso;
    }

    public void setProgresso(Double progresso) {
        this.progresso = progresso;
    }

    public String getUrlImagem() {
        return urlImagemEmpresa;
    }

    public void setUrlImagem(String urlImagemEmpresa) {
        this.urlImagemEmpresa = urlImagemEmpresa;
    }

    public Double getOrcamento() {
        return orcamento;
    }

    public void setOrcamento(Double orcamento) {
        this.orcamento = orcamento;
    }
}
