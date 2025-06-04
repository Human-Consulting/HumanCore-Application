package com.humanconsulting.humancore_api.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Sala {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idSala;

    private String nome;

    @OneToOne
    @JoinColumn(name = "fkProjeto", unique = true)
    private Projeto projeto;

    @ManyToMany
    @JoinTable(
            name = "sala_usuario",
            joinColumns = @JoinColumn(name = "idSala"),
            inverseJoinColumns = @JoinColumn(name = "idUsuario")
    )
    private Set<Usuario> usuarios = new HashSet<>();
}