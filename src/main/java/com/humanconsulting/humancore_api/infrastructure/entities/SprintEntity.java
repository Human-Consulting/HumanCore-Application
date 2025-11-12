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
public class SprintEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idSprint;

    private String titulo;

    private String descricao;

    private LocalDate dtInicio;

    private LocalDate dtFim;

    @ManyToOne
    @JoinColumn(name = "fkProjeto")
    private ProjetoEntity projeto;

    @OneToMany(mappedBy = "sprint", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TarefaEntity> tarefas = new ArrayList<>();
}
