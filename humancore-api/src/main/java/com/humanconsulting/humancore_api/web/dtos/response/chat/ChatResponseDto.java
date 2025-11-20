package com.humanconsulting.humancore_api.web.dtos.response.chat;

import java.util.List;
import java.util.Optional;

public record ChatResponseDto(Integer idSala,
                              String nome,
                              String urlImagem,
                              Optional<Integer> fkProjeto,
                              Optional<Integer> fkEmpresa,
                              Optional<String> nomeEmpresa,
                              List<ChatUsuarioDto> participants,
                              List<ChatMensagemUnificadaDto> mensagens) {
}

