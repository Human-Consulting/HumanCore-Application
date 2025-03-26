package com.humanconsulting.humancore_api.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
public class Projeto {

    @Id
    private Integer idProjeto;

    @NotNull
    private String descricao;

    @NotBlank
    private String nome;

    @NotNull
    private Double progresso;

    @NotNull
    private Double orcamento;

    @NotNull
    private Boolean com_impedimento;

    @NotNull
    private Integer fkEmpresa;

    @NotNull
    private Integer fkResponsavel;

    public Projeto(String descricao, String nome, Double orcamento, Integer fkResponsavel) {
        this.descricao = descricao;
        this.nome = nome;
        this.orcamento = orcamento;
        this.fkResponsavel = fkResponsavel;
    }

    public Projeto() {

    }

    public @NotBlank String getNome() {
        return nome;
    }

    public void setNome(@NotBlank String nome) {
        this.nome = nome;
    }

    public Integer getIdProjeto() {
        return idProjeto;
    }

    public void setIdProjeto(Integer idProjeto) {
        this.idProjeto = idProjeto;
    }

    public @NotNull String getDescricao() {
        return descricao;
    }

    public void setDescricao(@NotNull String descricao) {
        this.descricao = descricao;
    }

    public @NotNull Double getProgresso() {
        return progresso;
    }

    public void setProgresso(@NotNull Double progresso) {
        this.progresso = progresso;
    }

    public @NotNull Double getOrcamento() {
        return orcamento;
    }

    public void setOrcamento(@NotNull Double orcamento) {
        this.orcamento = orcamento;
    }

    public @NotNull Boolean getCom_impedimento() {
        return com_impedimento;
    }

    public void setCom_impedimento(@NotNull Boolean com_impedimento) {
        this.com_impedimento = com_impedimento;
    }

    public @NotNull Integer getFk_empresa() {
        return fkEmpresa;
    }

    public void setFk_empresa(@NotNull Integer fkEmpresa) {
        this.fkEmpresa = fkEmpresa;
    }

    public @NotNull Integer  getFk_responsavel() {
        return fkResponsavel;
    }

    public void setFk_responsavel(@NotNull Integer fkResponsavel) {
        this.fkResponsavel = fkResponsavel;
    }
}
