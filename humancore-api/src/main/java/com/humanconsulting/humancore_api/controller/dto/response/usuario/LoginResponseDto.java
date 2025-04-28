package com.humanconsulting.humancore_api.controller.dto.response.usuario;

import com.humanconsulting.humancore_api.controller.dto.response.TarefaResponseDto;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

public class LoginResponseDto {
    @Schema(description = "ID do usuário", example = "1")
    private Integer idUsuario;

    @Schema(description = "Nome completo do usuário", example = "João Silva")
    private String nome;

    private String email;

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

    @Schema(description = "Lista de IDs dos projetos vinculados ao usuário", example = "[1, 2, 3]")
    private List<Integer> projetosVinculados;

    @Schema(description = "Lista de tarefas vinculadas ao usuário", implementation = TarefaResponseDto.class)
    private List<TarefaResponseDto> tarefasVinculadas;

    private String token;

    public LoginResponseDto(Integer idUsuario, String email, String nome, String permissao, Integer fkEmpresa, String nomeEmpresa, Integer qtdTarefas, Boolean comImpedimento, List<Integer> projetosVinculados, List<TarefaResponseDto> tarefasVinculadas, String token) {
        this.idUsuario = idUsuario;
        this.nome = nome;
        this.email = email;
        this.permissao = permissao;
        this.fkEmpresa = fkEmpresa;
        this.nomeEmpresa = nomeEmpresa;
        this.qtdTarefas = qtdTarefas;
        this.comImpedimento = comImpedimento;
        this.projetosVinculados = projetosVinculados;
        this.tarefasVinculadas = tarefasVinculadas;
        this.token = token;
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

    public String getEmail() { return email; }

    public void setEmail(String email) { this.email = email; }

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

    public List<TarefaResponseDto> getTarefasVinculadas() {
        return tarefasVinculadas;
    }

    public void setTarefasVinculadas(List<TarefaResponseDto> tarefasVinculadas) {
        this.tarefasVinculadas = tarefasVinculadas;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
