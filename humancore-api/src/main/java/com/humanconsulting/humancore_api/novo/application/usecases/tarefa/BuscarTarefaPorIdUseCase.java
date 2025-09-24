package com.humanconsulting.humancore_api.novo.application.usecases.tarefa;

import com.humanconsulting.humancore_api.novo.domain.entities.Tarefa;
import com.humanconsulting.humancore_api.novo.domain.exception.EntidadeNaoEncontradaException;
import com.humanconsulting.humancore_api.novo.domain.repositories.TarefaRepository;

import java.util.Optional;

public class BuscarTarefaPorIdUseCase {
    private final TarefaRepository tarefaRepository;

    public BuscarTarefaPorIdUseCase(TarefaRepository tarefaRepository) {
        this.tarefaRepository = tarefaRepository;
    }

    public Tarefa execute(Integer id) {
        Optional<Tarefa> optTarefa = tarefaRepository.findById(id);
        if (optTarefa.isEmpty()) throw new EntidadeNaoEncontradaException("TarefaEntity não encontrada");
        return optTarefa.get();
    }
}

