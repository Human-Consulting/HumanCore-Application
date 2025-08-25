package com.humanconsulting.humancore_api.novo.infrastructure.entities;

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
public class SalaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idSala;

    private String nome;

    @Lob
    @Column(columnDefinition = "LONGTEXT")
    private String urlImagem;

    @OneToOne
    @JoinColumn(name = "fkProjeto", unique = true)
    private ProjetoEntity projeto;

    @OneToOne
    @JoinColumn(name = "fkEmpresa", unique = true)
    private EmpresaEntity empresa;

    @ManyToMany
    @JoinTable(
            name = "sala_usuario",
            joinColumns = @JoinColumn(name = "idSala"),
            inverseJoinColumns = @JoinColumn(name = "idUsuario")
    )
    private Set<UsuarioEntity> usuarios = new HashSet<>();
}