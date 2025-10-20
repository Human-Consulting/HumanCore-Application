package com.humanconsulting.humancore_api.application.usecases.sala;

import com.humanconsulting.humancore_api.domain.entities.Sala;
import com.humanconsulting.humancore_api.domain.exception.EntidadeNaoEncontradaException;
import com.humanconsulting.humancore_api.domain.repositories.SalaRepository;
import com.humanconsulting.humancore_api.domain.security.ValidarPermissao;
import com.humanconsulting.humancore_api.web.dtos.request.UsuarioPermissaoDto;

import java.util.Optional;

public class DeletarSalaUseCase {
    private final SalaRepository salaRepository;

    public DeletarSalaUseCase(SalaRepository salaRepository) {
        this.salaRepository = salaRepository;
    }

    public void execute(Integer id, UsuarioPermissaoDto usuarioPermissaoDto) {
        Optional<Sala> optSala = salaRepository.findById(id);
        if (optSala.isEmpty()) throw new EntidadeNaoEncontradaException("SalaEntity não encontrada.");
        ValidarPermissao.execute(usuarioPermissaoDto.getPermissaoEditor(), "DELETAR_SALA");
        salaRepository.deleteById(id);
    }
}

