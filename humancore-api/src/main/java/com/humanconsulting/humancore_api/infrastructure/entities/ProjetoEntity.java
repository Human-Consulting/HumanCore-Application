package com.humanconsulting.humancore_api.infrastructure.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProjetoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idProjeto;

    private String titulo;

    private String descricao;

    private Double orcamento;

    @Lob
    @Column(columnDefinition = "LONGTEXT")
    private String urlImagem;

    @ManyToOne
    @JoinColumn(name = "fkEmpresa")
    private EmpresaEntity empresa;

    @ManyToOne
    @JoinColumn(name = "fkResponsavel")
    private UsuarioEntity responsavel;

    @OneToMany(mappedBy = "projeto", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SalaEntity> salas = new ArrayList<>();

    @OneToMany(mappedBy = "projeto", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SprintEntity> sprints = new ArrayList<>();

    @OneToMany(mappedBy = "projeto", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<InvestimentoEntity> investimentos = new ArrayList<>();
}
