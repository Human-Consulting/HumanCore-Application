package com.humanconsulting.humancore_api.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Sprint {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idSprint;

    private String descricao;

    private LocalDate dtInicio;

    private LocalDate dtFim;

    @ManyToOne
    @JoinColumn(name = "fkProjeto")
    private Projeto projeto;
}
