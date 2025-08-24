package com.humanconsulting.humancore_api.novo.web.dtos.response.chat;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class ChatMensagemUnificadaDto {
    private Integer id;
    private Integer idUsuario;
    private String conteudo;
    private LocalDateTime horario;
    private boolean informativo;
}
