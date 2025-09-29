package com.humanconsulting.humancore_api.application.usecases.mensagem;

import com.humanconsulting.humancore_api.domain.entities.Mensagem;
import com.humanconsulting.humancore_api.domain.exception.EntidadeSemRetornoException;
import com.humanconsulting.humancore_api.domain.repositories.MensagemRepository;
import com.humanconsulting.humancore_api.web.dtos.response.chat.ChatMensagemUnificadaDto;
import com.humanconsulting.humancore_api.web.mappers.MensagemMapper;

import java.util.List;
import java.util.stream.Collectors;

public class ListarMensagensUseCase {
    private final MensagemRepository mensagemRepository;

    public ListarMensagensUseCase(MensagemRepository mensagemRepository) {
        this.mensagemRepository = mensagemRepository;
    }

    public List<ChatMensagemUnificadaDto> execute() {
        List<Mensagem> mensagens = mensagemRepository.findAll();
        if (mensagens.isEmpty()) throw new EntidadeSemRetornoException("Nenhuma mensagem encontrada para a sala.");
        return mensagens.stream()
                .map(MensagemMapper::toMensagemUnificadaResponse)
                .collect(Collectors.toList());
    }
}