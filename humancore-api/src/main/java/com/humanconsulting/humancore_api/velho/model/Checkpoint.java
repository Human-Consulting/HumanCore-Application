package com.humanconsulting.humancore_api.velho.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Checkpoint {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idCheckpoint;

    private String descricao;

    @Column(columnDefinition = "TINYINT(1)")
    private Boolean finalizado;

    @ManyToOne
    @JoinColumn(name = "fkTarefa")
    private Tarefa tarefa;
}
