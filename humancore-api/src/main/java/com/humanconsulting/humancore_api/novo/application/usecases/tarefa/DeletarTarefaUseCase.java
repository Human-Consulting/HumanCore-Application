package com.humanconsulting.humancore_api.novo.application.usecases.tarefa;

import com.humanconsulting.humancore_api.novo.domain.entities.Tarefa;
import com.humanconsulting.humancore_api.novo.domain.repositories.TarefaRepository;
import com.humanconsulting.humancore_api.novo.web.dtos.request.UsuarioPermissaoDto;

import java.util.Optional;

public class DeletarTarefaUseCase {
    private final TarefaRepository tarefaRepository;

    public DeletarTarefaUseCase(TarefaRepository tarefaRepository) {
        this.tarefaRepository = tarefaRepository;
    }

    public void execute(Integer id, UsuarioPermissaoDto usuarioPermissaoDto) {
        PermissaoValidator.validarPermissao(usuarioPermissaoDto.getPermissaoEditor(), "EXCLUIR_TAREFA");
        Optional<Tarefa> optTarefa = tarefaRepository.findById(id);
        if (optTarefa.isEmpty()) throw new EntidadeNaoEncontradaException("TarefaEntity não encontrada.");
        tarefaRepository.deleteById(id);
    }
}

