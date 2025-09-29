package com.humanconsulting.humancore_api.domain.entities;

import java.time.LocalDateTime;

public class MensagemInfo {
    private Integer idMensagemInfo;
    private String conteudo;
    private LocalDateTime horario;
    private Sala sala;

    public MensagemInfo() {}

    public MensagemInfo(Integer idMensagemInfo, String conteudo, LocalDateTime horario, Sala sala) {
        this.idMensagemInfo = idMensagemInfo;
        this.conteudo = conteudo;
        this.horario = horario;
        this.sala = sala;
    }

    public Integer getIdMensagemInfo() { return idMensagemInfo; }
    public void setIdMensagemInfo(Integer idMensagemInfo) { this.idMensagemInfo = idMensagemInfo; }
    public String getConteudo() { return conteudo; }
    public void setConteudo(String conteudo) { this.conteudo = conteudo; }
    public LocalDateTime getHorario() { return horario; }
    public void setHorario(LocalDateTime horario) { this.horario = horario; }
    public Sala getSala() { return sala; }
    public void setSala(Sala sala) { this.sala = sala; }
}
