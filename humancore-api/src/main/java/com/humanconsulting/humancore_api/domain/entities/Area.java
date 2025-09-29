package com.humanconsulting.humancore_api.domain.entities;

public class Area {
    private String nome;
    private Integer valor;

    public Area() {}

    public Area(String nome, Integer valor) {
        this.nome = nome;
        this.valor = valor;
    }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public Integer getValor() { return valor; }
    public void setValor(Integer valor) { this.valor = valor; }
}
