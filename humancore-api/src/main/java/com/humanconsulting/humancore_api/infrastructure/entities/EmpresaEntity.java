package com.humanconsulting.humancore_api.infrastructure.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmpresaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idEmpresa;

    private String nome;

    private String cnpj;

    @Lob
    @Column(columnDefinition = "LONGTEXT")
    private String urlImagem;
}
