package com.humanconsulting.humancore_api.infrastructure.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InvestimentoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idInvestimento;

    private String descricao;

    private Double valor;

    private LocalDate dtInvestimento;

    @ManyToOne
    @JoinColumn(name = "fkProjeto")
    private ProjetoEntity projeto;
}
