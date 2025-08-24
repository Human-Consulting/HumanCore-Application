package com.humanconsulting.humancore_api.novo.infrastructure.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Mensagem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idMensagem;

    private String conteudo;

    private LocalDateTime horario;

    @ManyToOne
    @JoinColumn(name = "fkUsuario")
    private UsuarioEntity usuario;

    @ManyToOne
    @JoinColumn(name = "fkSala")
    private Sala sala;
}
