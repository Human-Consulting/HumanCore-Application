package com.humanconsulting.humancore_api.application.usecases.mensagem;

import com.humanconsulting.humancore_api.domain.entities.Mensagem;
import com.humanconsulting.humancore_api.domain.exception.EntidadeNaoEncontradaException;
import com.humanconsulting.humancore_api.domain.repositories.MensagemRepository;
import com.humanconsulting.humancore_api.web.dtos.response.mensagem.MensagemResponseDto;
import com.humanconsulting.humancore_api.web.mappers.MensagemMapper;

import java.util.List;

public class BuscarMensagemPorIdUseCase {
    private final MensagemRepository mensagemRepository;

    public BuscarMensagemPorIdUseCase(MensagemRepository mensagemRepository) {
        this.mensagemRepository = mensagemRepository;
    }

    public MensagemResponseDto execute(Integer id) {
        List<Mensagem> optMensagem = mensagemRepository.findById(id);
        if (optMensagem.isEmpty()) throw new EntidadeNaoEncontradaException("Mensagem não encontrada.");
        return MensagemMapper.toDto(optMensagem.getFirst());
    }
}

