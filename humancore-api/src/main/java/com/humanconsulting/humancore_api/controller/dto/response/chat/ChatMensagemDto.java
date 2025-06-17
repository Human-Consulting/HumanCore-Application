package com.humanconsulting.humancore_api.controller.dto.response.chat;

import java.time.LocalDateTime;

public record ChatMensagemDto(
        Integer idMensagem,
        Integer idUsuario,
        String conteudo,
        LocalDateTime dataEnvio
) {
}
