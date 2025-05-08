package com.humanconsulting.humancore_api.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Empresa {
    @Id
    private Integer fkEmpresa;

    private String nome;

    private String cnpj;

    private String urlImagem;
}
