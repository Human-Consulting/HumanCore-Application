package com.humanconsulting.humancore_api.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
