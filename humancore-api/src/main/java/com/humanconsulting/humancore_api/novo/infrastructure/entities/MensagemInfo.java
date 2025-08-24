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
public class MensagemInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idMensagemInfo;

    private String conteudo;

    private LocalDateTime horario;

    @ManyToOne
    @JoinColumn(name = "fkSala")
    private Sala sala;
}
