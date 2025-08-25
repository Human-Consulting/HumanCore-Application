package com.humanconsulting.humancore_api.novo.infrastructure.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CheckpointEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idCheckpoint;

    private String descricao;

    @Column(columnDefinition = "TINYINT(1)")
    private Boolean finalizado;

    @ManyToOne
    @JoinColumn(name = "fkTarefa")
    private TarefaEntity tarefa;
}
