package com.humanconsulting.humancore_api.novo.domain.entities;

public class Checkpoint {

    private Integer idCheckpoint;

    private String descricao;

    private Boolean finalizado;

    private Tarefa tarefa;

    public Checkpoint() {}

    public Checkpoint(Integer idCheckpoint, String descricao, Boolean finalizado, Tarefa tarefa) {
        this.idCheckpoint = idCheckpoint;
        this.descricao = descricao;
        this.finalizado = finalizado;
        this.tarefa = tarefa;
    }

    public Integer getIdCheckpoint() { return idCheckpoint; }
    public void setIdCheckpoint(Integer idCheckpoint) { this.idCheckpoint = idCheckpoint; }
    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
    public Boolean getFinalizado() { return finalizado; }
    public void setFinalizado(Boolean finalizado) { this.finalizado = finalizado; }
    public Tarefa getTarefa() { return tarefa; }
    public void setTarefa(Tarefa tarefa) { this.tarefa = tarefa; }
}
