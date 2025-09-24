package com.humanconsulting.humancore_api.novo.application.usecases.mensagem;

import com.humanconsulting.humancore_api.novo.domain.entities.Mensagem;
import com.humanconsulting.humancore_api.novo.domain.exception.EntidadeNaoEncontradaException;
import com.humanconsulting.humancore_api.novo.domain.repositories.MensagemRepository;

import java.util.List;
import java.util.Optional;

public class BuscarMensagemPorIdUseCase {
    private final MensagemRepository mensagemRepository;

    public BuscarMensagemPorIdUseCase(MensagemRepository mensagemRepository) {
        this.mensagemRepository = mensagemRepository;
    }

    public Mensagem execute(Integer id) {
        List<Mensagem> optMensagem = mensagemRepository.findById(id);
        if (optMensagem.isEmpty()) throw new EntidadeNaoEncontradaException("Mensagem não encontrada.");
        return optMensagem.getFirst();
    }
}

