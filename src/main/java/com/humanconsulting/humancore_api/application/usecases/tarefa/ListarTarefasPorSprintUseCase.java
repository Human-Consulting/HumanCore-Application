package com.humanconsulting.humancore_api.application.usecases.tarefa;

import com.humanconsulting.humancore_api.application.usecases.tarefa.mappers.TarefaResponseMapper;
import com.humanconsulting.humancore_api.domain.entities.Tarefa;
import com.humanconsulting.humancore_api.domain.exception.EntidadeSemRetornoException;
import com.humanconsulting.humancore_api.domain.repositories.TarefaRepository;
import com.humanconsulting.humancore_api.web.dtos.response.tarefa.TarefaResponseDto;

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
        System.out.println("Entrei no execute do ListarTarefasPorSprintUseCase!");
        if (all.isEmpty()) throw new EntidadeSemRetornoException("Nenhuma tarefa registrada");
        System.out.println("Passei do throw!");
        List<TarefaResponseDto> allResponse = new ArrayList<>();
        for (Tarefa tarefa : all) {
            allResponse.add(tarefaResponseMapper.toResponse(tarefa));
        }
        return allResponse;
    }
}

