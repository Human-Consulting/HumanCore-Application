package com.humanconsulting.humancore_api.controller.dto.atualizar;

import jakarta.validation.constraints.NotBlank;

public class ProjetoAtualizarRequestDto {
    @NotBlank
    private String descricao;

    @NotBlank
    private String nome;

    @NotBlank
    private Integer fkResponsavel;

    @NotBlank
    private Double orcamento;

    public ProjetoAtualizarRequestDto() {
    }

    public ProjetoAtualizarRequestDto(String descricao, String nome, Integer fkResponsavel, Double orcamento) {
        this.descricao = descricao;
        this.nome = nome;
        this.fkResponsavel = fkResponsavel;
        this.orcamento = orcamento;
    }
    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getFkResponsavel() {
        return fkResponsavel;
    }

    public void setFkResponsavel(Integer fkResponsavel) {
        this.fkResponsavel = fkResponsavel;
    }

    public Double getOrcamento() {
        return orcamento;
    }

    public void setOrcamento(Double orcamento) {
        this.orcamento = orcamento;
    }
}
