package com.humanconsulting.humancore_api.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
    private Integer idSprint;

    private String descricao;

    private LocalDate dtInicio;

    private LocalDate dtFim;

    @ManyToOne
    @JoinColumn(name = "fkProjeto")
    private Projeto projeto;
}
