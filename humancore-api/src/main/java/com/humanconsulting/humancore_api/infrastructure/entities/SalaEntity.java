package com.humanconsulting.humancore_api.infrastructure.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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
    @JoinColumn(name = "fkEmpresa")
    private EmpresaEntity empresa;

    @ManyToMany
    @JoinTable(
            name = "sala_usuario",
            joinColumns = @JoinColumn(name = "idSala"),
            inverseJoinColumns = @JoinColumn(name = "idUsuario")
    )
    private Set<UsuarioEntity> usuarios = new HashSet<>();

    @OneToMany(mappedBy = "sala", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MensagemEntity> mensagens = new ArrayList<>();

    @OneToMany(mappedBy = "sala", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MensagemInfoEntity> mensagensInfo = new ArrayList<>();
}