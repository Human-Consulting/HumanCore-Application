package com.humanconsulting.humancore_api.application.usecases.sprint;

import com.humanconsulting.humancore_api.domain.exception.EntidadeNaoEncontradaException;
import com.humanconsulting.humancore_api.domain.repositories.SprintRepository;
import com.humanconsulting.humancore_api.domain.security.ValidarPermissao;
import com.humanconsulting.humancore_api.web.dtos.request.UsuarioPermissaoDto;

import java.util.Optional;

public class DeletarSprintUseCase {
    private final SprintRepository sprintRepository;

    public DeletarSprintUseCase(SprintRepository sprintRepository) {
        this.sprintRepository = sprintRepository;
    }

    public void execute(Integer id, UsuarioPermissaoDto usuarioPermissaoDto) {
        ValidarPermissao.execute(usuarioPermissaoDto.getPermissaoEditor(), "EXCLUIR_SPRINT");
        Optional<?> optSprint = sprintRepository.findById(id);
        if (optSprint.isEmpty()) throw new EntidadeNaoEncontradaException("SprintEntity não encontrada.");
        sprintRepository.deleteById(id);
    }
}

