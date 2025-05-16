package com.humanconsulting.humancore_api.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Tarefa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idTarefa;

    private String descricao;

    private LocalDate dtInicio;

    private LocalDate dtFim;

    private Double progresso;

    private Boolean comImpedimento;

    @ManyToOne
    @JoinColumn(name = "fkSprint")
    private Sprint sprint;

    @ManyToOne
    @JoinColumn(name = "fkResponsavel")
    private Usuario responsavel;
}
