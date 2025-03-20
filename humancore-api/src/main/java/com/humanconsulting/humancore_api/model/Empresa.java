package com.humanconsulting.humancore_api.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
public class Empresa {
    @Id
    private Integer idEmpresa;

    @NotNull
    private String cnpj;

    @NotBlank
    private String nome;

    public Integer getIdEmpresa() {
        return idEmpresa;
    }

    public void setIdEmpresa(Integer idEmpresa) {
        this.idEmpresa = idEmpresa;
    }

    public @NotNull String getCnpj() {
        return cnpj;
    }

    public void setCnpj(@NotNull String cnpj) {
        this.cnpj = cnpj;
    }

    public @NotBlank String getNome() {
        return nome;
    }

    public void setNome(@NotBlank String nome) {
        this.nome = nome;
    }
}
