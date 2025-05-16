package com.humanconsulting.humancore_api.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idUsuario;

    private String nome;

    private String email;

    private String senha;

    private String cargo;

    private String area;

    private String permissao;

    @JoinColumn(name = "fkEmpresa")
    @ManyToOne
    private Empresa empresa;
}
