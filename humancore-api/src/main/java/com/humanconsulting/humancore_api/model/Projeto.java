package com.humanconsulting.humancore_api.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Projeto {
    @Id
    private Integer idProjeto;

    private String descricao;

    private Double orcamento;

    private String urlImagem;

    @ManyToOne
    @JoinColumn(name = "fkEmpresa")
    private Empresa empresa;

    @ManyToOne
    @JoinColumn(name = "fkResponsavel")
    private Usuario responsavel;
}
