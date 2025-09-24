package com.humanconsulting.humancore_api.novo.application.usecases.tarefa;

import com.humanconsulting.humancore_api.novo.application.usecases.tarefa.mappers.TarefaResponseMapper;
import com.humanconsulting.humancore_api.novo.domain.entities.Tarefa;
import com.humanconsulting.humancore_api.novo.domain.exception.EntidadeSemRetornoException;
import com.humanconsulting.humancore_api.novo.domain.repositories.TarefaRepository;
import com.humanconsulting.humancore_api.novo.web.dtos.response.tarefa.TarefaResponseDto;

import java.util.ArrayList;
import java.util.List;

public class ListarTarefasPorSprintUseCase {
    private final TarefaRepository tarefaRepository;
    private final TarefaResponseMapper tarefaResponseMapper;

    public ListarTarefasPorSprintUseCase(TarefaRepository tarefaRepository, TarefaResponseMapper tarefaResponseMapper) {
        this.tarefaRepository = tarefaRepository;
        this.tarefaResponseMapper = tarefaResponseMapper;
    }

    public List<TarefaResponseDto> execute(Integer idSprint) {
        List<Tarefa> all = tarefaRepository.findBySprint_IdSprint(idSprint);
        if (all.isEmpty()) throw new EntidadeSemRetornoException("Nenhuma tarefa registrada");
        List<TarefaResponseDto> allResponse = new ArrayList<>();
        for (Tarefa tarefa : all) {
            allResponse.add(tarefaResponseMapper.toResponse(tarefa));
        }
        return allResponse;
    }
}

