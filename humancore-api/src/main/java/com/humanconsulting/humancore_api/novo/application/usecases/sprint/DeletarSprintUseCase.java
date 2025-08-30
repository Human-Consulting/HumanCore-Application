package com.humanconsulting.humancore_api.novo.application.usecases.sprint;

import com.humanconsulting.humancore_api.novo.domain.repositories.SprintRepository;
import com.humanconsulting.humancore_api.novo.web.dtos.request.UsuarioPermissaoDto;

import java.util.Optional;

public class DeletarSprintUseCase {
    private final SprintRepository sprintRepository;

    public DeletarSprintUseCase(SprintRepository sprintRepository) {
        this.sprintRepository = sprintRepository;
    }

    public void execute(Integer id, UsuarioPermissaoDto usuarioPermissaoDto) {
        PermissaoValidator.validarPermissao(usuarioPermissaoDto.getPermissaoEditor(), "EXCLUIR_SPRINT");
        Optional<?> optSprint = sprintRepository.findById(id);
        if (optSprint.isEmpty()) throw new EntidadeNaoEncontradaException("SprintEntity não encontrada.");
        sprintRepository.deleteById(id);
    }
}

