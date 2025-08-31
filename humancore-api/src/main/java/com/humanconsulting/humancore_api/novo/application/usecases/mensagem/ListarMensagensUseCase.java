package com.humanconsulting.humancore_api.novo.application.usecases.mensagem;

import com.humanconsulting.humancore_api.novo.domain.entities.Mensagem;
import com.humanconsulting.humancore_api.novo.domain.repositories.MensagemRepository;
import com.humanconsulting.humancore_api.novo.web.dtos.response.chat.ChatMensagemUnificadaDto;
import com.humanconsulting.humancore_api.novo.web.mappers.MensagemMapper;

import java.util.List;
import java.util.stream.Collectors;

public class ListarMensagensUseCase {
    private final MensagemRepository mensagemRepository;

    public ListarMensagensUseCase(MensagemRepository mensagemRepository) {
        this.mensagemRepository = mensagemRepository;
    }

    public List<ChatMensagemUnificadaDto> execute(Integer idSala) {
        List<Mensagem> mensagens = mensagemRepository.findById(idSala);
        if (mensagens.isEmpty()) throw new EntidadeSemRetornoException("Nenhuma mensagem encontrada para a sala.");
        return mensagens.stream()
                .map(MensagemMapper::toMensagemUnificadaResponse)
                .collect(Collectors.toList());
    }
}