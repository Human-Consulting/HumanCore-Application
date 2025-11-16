package com.humanconsulting.humancore_api.web.dtos.response.chat;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class ChatMensagemUnificadaDto {
    private Integer idMensagem;
    private Integer idSala;
    private Integer idUsuario;
    private String nome;
    private String conteudo;
    private LocalDateTime horario;
    private boolean informativo;
}
