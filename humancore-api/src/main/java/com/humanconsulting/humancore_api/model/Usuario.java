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
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idUsuario;

    private String nome;

    private String email;

    private String senha;

    private String cargo;

    private String area;

    private String permissao;

    private String cores;

    @JoinColumn(name = "fkEmpresa")
    @ManyToOne
    private Empresa empresa;

    @ManyToMany(mappedBy = "usuarios", fetch = FetchType.EAGER)
    private Set<Sala> salas = new HashSet<>();
}
