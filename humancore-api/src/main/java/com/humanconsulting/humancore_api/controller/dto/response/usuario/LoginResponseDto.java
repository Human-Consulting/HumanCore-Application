package com.humanconsulting.humancore_api.controller.dto.response.usuario;

import java.util.List;

public class LoginResponseDto {
    private Integer idUsuario;

    private String nome;

    private String permissao;

    private Integer fkEmpresa;

    private String nomeEmpresa;

    private Integer qtdTarefas;

    private Boolean comImpedimento;

    private List<Integer> projetosVinculados;

    public LoginResponseDto(Integer idUsuario, String nome, String permissao, Integer fkEmpresa, String nomeEmpresa, Integer qtdTarefas, Boolean comImpedimento, List<Integer> projetosVinculados) {
        this.idUsuario = idUsuario;
        this.nome = nome;
        this.permissao = permissao;
        this.fkEmpresa = fkEmpresa;
        this.nomeEmpresa = nomeEmpresa;
        this.qtdTarefas = qtdTarefas;
        this.comImpedimento = comImpedimento;
        this.projetosVinculados = projetosVinculados;
    }

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getPermissao() {
        return permissao;
    }

    public void setPermissao(String permissao) {
        this.permissao = permissao;
    }

    public Integer getFkEmpresa() {
        return fkEmpresa;
    }

    public void setFkEmpresa(Integer fkEmpresa) {
        this.fkEmpresa = fkEmpresa;
    }

    public String getNomeEmpresa() {
        return nomeEmpresa;
    }

    public void setNomeEmpresa(String nomeEmpresa) {
        this.nomeEmpresa = nomeEmpresa;
    }

    public Integer getQtdTarefas() {
        return qtdTarefas;
    }

    public void setQtdTarefas(Integer qtdTarefas) {
        this.qtdTarefas = qtdTarefas;
    }

    public Boolean getComImpedimento() {
        return comImpedimento;
    }

    public void setComImpedimento(Boolean comImpedimento) {
        this.comImpedimento = comImpedimento;
    }

    public List<Integer> getProjetosVinculados() {
        return projetosVinculados;
    }

    public void setProjetosVinculados(List<Integer> projetosVinculados) {
        this.projetosVinculados = projetosVinculados;
    }
}
