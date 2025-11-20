package com.humanconsulting.humancore_api.domain.entities;

public class TarefaUsuario {
    private String nome;
    private Integer qtdTarefas;

    public TarefaUsuario() {}

    public TarefaUsuario(String nome, Integer qtdTarefas) {
        this.nome = nome;
        this.qtdTarefas = qtdTarefas;
    }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public Integer getQtdTarefas() { return qtdTarefas; }
    public void setQtdTarefas(Integer qtdTarefas) { this.qtdTarefas = qtdTarefas; }
}
