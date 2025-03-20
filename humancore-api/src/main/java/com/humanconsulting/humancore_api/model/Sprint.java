package com.humanconsulting.humancore_api.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

@Entity
public class Sprint {
    @Id
    private Integer idSprint;

    @NotNull
    private String descricao;

    @NotNull
    private LocalDate dtInicio;

    @NotNull
    private LocalDate dtFim;

    @NotNull
    private Double progresso;

    @NotNull
    private Boolean comImpedimento;

    @NotNull
    private Integer fkProjeto;

    public Integer getIdSprint() {
        return idSprint;
    }

    public void setIdSprint(Integer idSprint) {
        this.idSprint = idSprint;
    }

    public @NotBlank String getDescricao() {
        return descricao;
    }

    public void setDescricao(@NotBlank String descricao) {
        this.descricao = descricao;
    }

    public @NotNull LocalDate getDtInicio() {
        return dtInicio;
    }

    public void setDtInicio(@NotNull LocalDate dtInicio) {
        this.dtInicio = dtInicio;
    }

    public @NotNull LocalDate getDtFim() {
        return dtFim;
    }

    public void setDtFim(@NotNull LocalDate dtFim) {
        this.dtFim = dtFim;
    }

    public @NotNull Double getProgresso() {
        return progresso;
    }

    public void setProgresso(@NotNull Double progresso) {
        this.progresso = progresso;
    }

    public @NotNull Boolean getComImpedimento() {
        return comImpedimento;
    }

    public void setComImpedimento(@NotNull Boolean comImpedimento) {
        this.comImpedimento = comImpedimento;
    }

    public @NotNull Integer getFkProjeto() {
        return fkProjeto;
    }

    public void setFkProjeto(@NotNull Integer fkProjeto) {
        this.fkProjeto = fkProjeto;
    }

    public boolean getFinalizado() {
        return this.progresso == 100;
    }
}
