package com.humanconsulting.humancore_api.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

@Entity
public class Entrega {
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
    private Boolean finalizada;

    @NotNull
    private Boolean comImpedimento;

    @NotNull
    private Integer fkSprint;

    @NotNull
    private Integer fkResponsavel;

    public Entrega(String descricao, LocalDate dtInicio, LocalDate dtFim, Integer fkResponsavel) {
        this.descricao = descricao;
        this.dtInicio = dtInicio;
        this.dtFim = dtFim;
        this.fkResponsavel = fkResponsavel;
    }

    public Entrega() {

    }

    public @NotNull Boolean getFinalizada() {
        return finalizada;
    }

    public void setFinalizada(@NotNull Boolean finalizada) {
        this.finalizada = finalizada;
    }

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

    public @NotNull Integer getFkSprint() {
        return fkSprint;
    }

    public void setFkSprint(@NotNull Integer fkSprint) {
        this.fkSprint = fkSprint;
    }

    public @NotNull Integer getFkResponsavel() {
        return fkResponsavel;
    }

    public void setFkResponsavel(@NotNull Integer fkResponsavel) {
        this.fkResponsavel = fkResponsavel;
    }

    public boolean getFinalizado() {
        return this.progresso == 100;
    }
}
