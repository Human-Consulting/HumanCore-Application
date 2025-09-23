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

    private String titulo;

    @Column(columnDefinition = "LONGTEXT")
    private String descricao;

    private LocalDate dtInicio;

    private LocalDate dtFim;

    @Column(columnDefinition = "TINYINT(1)")
    private Boolean comImpedimento;

    private String comentario;

    @ManyToOne
    @JoinColumn(name = "fkSprint")
    private Sprint sprint;

    @ManyToOne
    @JoinColumn(name = "fkResponsavel")
    private Usuario responsavel;
}
