package com.humanconsulting.humancore_api.infrastructure.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idUsuario;

    private String nome;

    private String email;

    private String senha;

    private String cargo;

    private String area;

    private String permissao;

    private String cores;

    @JoinColumn(name = "fkEmpresa")
    @ManyToOne
    private EmpresaEntity empresa;

    @JsonIgnore
    @ManyToMany(mappedBy = "usuarios", fetch = FetchType.EAGER)
    private Set<SalaEntity> salas = new HashSet<>();
}
