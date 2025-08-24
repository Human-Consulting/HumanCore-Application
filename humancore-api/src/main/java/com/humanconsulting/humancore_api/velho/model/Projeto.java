package com.humanconsulting.humancore_api.velho.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Projeto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idProjeto;

    private String titulo;

    private String descricao;

    private Double orcamento;

    @Lob
    @Column(columnDefinition = "LONGTEXT")
    private String urlImagem;

    @ManyToOne
    @JoinColumn(name = "fkEmpresa")
    private Empresa empresa;

    @ManyToOne
    @JoinColumn(name = "fkResponsavel")
    private Usuario responsavel;
}
