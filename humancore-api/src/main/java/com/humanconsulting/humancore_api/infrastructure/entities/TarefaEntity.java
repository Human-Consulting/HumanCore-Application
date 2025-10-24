package com.humanconsulting.humancore_api.infrastructure.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TarefaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idTarefa;

    private String titulo;

    private String descricao;

    private LocalDate dtInicio;

    private LocalDate dtFim;

    @Column(columnDefinition = "TINYINT(1)")
    private Boolean comImpedimento;

    private String comentario;

    @ManyToOne
    @JoinColumn(name = "fkSprint")
    private SprintEntity sprint;

    @ManyToOne
    @JoinColumn(name = "fkResponsavel")
    private UsuarioEntity responsavel;

    @OneToMany(mappedBy = "tarefa", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CheckpointEntity> checkpoints = new ArrayList<>();
}
