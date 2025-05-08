package com.humanconsulting.humancore_api.controller.dto.response.empresa;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmpresaResponseDto {
    @Schema(description = "ID da Empresa", example = "1")
    private Integer idEmpresa;

    @Schema(description = "Nome da Empresa", example = "Human Consulting Ltda.")
    private String nome;

    @Schema(description = "CNPJ da Empresa", example = "12.345.678/0001-90")
    private String cnpj;

    @Schema(description = "Nome do Diretor da Empresa", example = "Carlos Silva")
    private String nomeDiretor;

    @Schema(description = "Indica se a empresa possui impedimentos", example = "false")
    private boolean comImpedimento;

    @Schema(description = "Progresso atual da empresa em porcentagem", example = "75.5")
    private Double progresso;

    @Schema(description = "URL do logo da empresa", example = "http://example.com/imagem-empresa.jpg")
    private String urlImagemEmpresa;

    @Schema(description = "Orçamento total da empresa", example = "5000000.0")
    private Double orcamento;

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
