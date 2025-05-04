package com.humanconsulting.humancore_api.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Usuario {
    @Id
    private Integer idUsuario;

    private String nome;

    private String email;

    private String senha;

    private String cargo;

    private String area;

    private String permissao;

    @ManyToOne
    @JoinColumn(name = "fkEmpresa")
    private Empresa empresa;
}
