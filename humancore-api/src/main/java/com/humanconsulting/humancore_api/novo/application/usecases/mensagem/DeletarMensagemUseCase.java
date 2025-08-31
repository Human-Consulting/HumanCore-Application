package com.humanconsulting.humancore_api.novo.application.usecases.mensagem;

import com.humanconsulting.humancore_api.novo.domain.repositories.MensagemRepository;

public class DeletarMensagemUseCase {
    private final MensagemRepository mensagemRepository;
    private final BuscarMensagemPorIdUseCase buscarMensagemPorIdUseCase;

    public DeletarMensagemUseCase(MensagemRepository mensagemRepository, BuscarMensagemPorIdUseCase buscarMensagemPorIdUseCase) {
        this.mensagemRepository = mensagemRepository;
        this.buscarMensagemPorIdUseCase = buscarMensagemPorIdUseCase;
    }

    public void execute(Integer id) {
        buscarMensagemPorIdUseCase.execute(id);
        mensagemRepository.deleteById(id);
    }
}