package com.humanconsulting.humancore_api.controller.dto.response.usuario;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

public class LoginResponseDto {
    @Schema(description = "ID do usuário", example = "1")
    private Integer idUsuario;

    @Schema(description = "Nome completo do usuário", example = "João Silva")
    private String nome;

    @Schema(description = "Permissão atribuída ao usuário", example = "CONSULTOR")
    private String permissao;

    @Schema(description = "Id da empresa à qual o usuário pertence", example = "5")
    private Integer fkEmpresa;

    @Schema(description = "Nome da empresa à qual o usuário pertence", example = "Tech Corp")
    private String nomeEmpresa;

    @Schema(description = "Quantidade de tarefas atribuídas ao usuário", example = "3")
    private Integer qtdTarefas;

    @Schema(description = "Indica se o usuário tem alguma tarefa com impedimento", example = "false")
    private Boolean comImpedimento;

    @Schema(description = "Lista de identificadores dos projetos vinculados ao usuário", example = "[1, 2, 3]")
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
