package com.humanconsulting.humancore_api.domain.entities;

import java.time.LocalDateTime;

public class Mensagem {
    private Integer idMensagem;
    private String conteudo;
    private LocalDateTime horario;
    private Usuario usuario;
    private Sala sala;

    public Mensagem() {}

    public Mensagem(Integer idMensagem, String conteudo, LocalDateTime horario, Usuario usuario, Sala sala) {
        this.idMensagem = idMensagem;
        this.conteudo = conteudo;
        this.horario = horario;
        this.usuario = usuario;
        this.sala = sala;
    }

    public Integer getIdMensagem() { return idMensagem; }
    public void setIdMensagem(Integer idMensagem) { this.idMensagem = idMensagem; }
    public String getConteudo() { return conteudo; }
    public void setConteudo(String conteudo) { this.conteudo = conteudo; }
    public LocalDateTime getHorario() { return horario; }
    public void setHorario(LocalDateTime horario) { this.horario = horario; }
    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }
    public Sala getSala() { return sala; }
    public void setSala(Sala sala) { this.sala = sala; }
}
