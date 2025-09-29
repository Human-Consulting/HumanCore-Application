package com.humanconsulting.humancore_api.application.usecases.mensagem;

import com.humanconsulting.humancore_api.domain.repositories.MensagemRepository;
import com.humanconsulting.humancore_api.web.dtos.request.UsuarioPermissaoDto;

public class DeletarMensagemUseCase {
    private final MensagemRepository mensagemRepository;
    private final BuscarMensagemPorIdUseCase buscarMensagemPorIdUseCase;

    public DeletarMensagemUseCase(MensagemRepository mensagemRepository, BuscarMensagemPorIdUseCase buscarMensagemPorIdUseCase) {
        this.mensagemRepository = mensagemRepository;
        this.buscarMensagemPorIdUseCase = buscarMensagemPorIdUseCase;
    }

    public void execute(Integer id, UsuarioPermissaoDto usuarioPermissaoDto) {
        buscarMensagemPorIdUseCase.execute(id);
        mensagemRepository.deleteById(id);
    }
}