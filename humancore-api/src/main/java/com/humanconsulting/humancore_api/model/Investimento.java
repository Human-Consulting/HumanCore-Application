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
public class Investimento {
    @Id
    private Integer idInvestimento;

    private Double valor;

    private LocalDate dtInvestimento;

    @ManyToOne
    @JoinColumn(name = "fkProjeto")
    private Projeto projeto;
}
