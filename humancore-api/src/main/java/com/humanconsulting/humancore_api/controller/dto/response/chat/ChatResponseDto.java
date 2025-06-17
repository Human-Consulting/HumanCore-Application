package com.humanconsulting.humancore_api.controller.dto.response.chat;

import java.util.List;
import java.util.Optional;

public record ChatResponseDto(Integer idSala,
                              String nome,
                              String urlImagem,
                              Optional<Integer> fkProjeto,
                              Optional<Integer> fkEmpresa,
                              List<ChatUsuarioDto> participants,
                              List<ChatMensagemUnificadaDto> mensagens) {
}

