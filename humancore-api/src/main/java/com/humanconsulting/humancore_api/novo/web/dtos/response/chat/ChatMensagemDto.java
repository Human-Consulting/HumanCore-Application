package com.humanconsulting.humancore_api.novo.web.dtos.response.chat;

import java.time.LocalDateTime;

public record ChatMensagemDto(
        Integer idMensagem,
        Integer idUsuario,
        String conteudo,
        LocalDateTime dataEnvio
) {
}
