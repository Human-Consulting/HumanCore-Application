package com.humanconsulting.humancore_api.velho.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Empresa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idEmpresa;

    private String nome;

    private String cnpj;

    @Lob
    @Column(columnDefinition = "LONGTEXT")
    private String urlImagem;
}
