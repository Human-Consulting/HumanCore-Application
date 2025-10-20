package com.humanconsulting.humancore_api.application.usecases.sprint.mappers;

import com.humanconsulting.humancore_api.application.usecases.tarefa.mappers.TarefaResponseMapper;
import com.humanconsulting.humancore_api.domain.entities.Checkpoint;
import com.humanconsulting.humancore_api.domain.entities.Sprint;
import com.humanconsulting.humancore_api.domain.entities.Tarefa;
import com.humanconsulting.humancore_api.domain.repositories.CheckpointRepository;
import com.humanconsulting.humancore_api.domain.repositories.TarefaRepository;
import com.humanconsulting.humancore_api.domain.utils.ProgressoCalculator;
import com.humanconsulting.humancore_api.web.dtos.response.sprint.SprintResponseDto;
import com.humanconsulting.humancore_api.web.dtos.response.tarefa.TarefaResponseDto;
import com.humanconsulting.humancore_api.web.mappers.SprintMapper;

import java.util.ArrayList;
import java.util.List;

public class SprintResponseMapper {
    private final TarefaRepository tarefaRepository;
    private final CheckpointRepository checkpointRepository;
    private final TarefaResponseMapper tarefaResponseMapper;

    public SprintResponseMapper(TarefaRepository tarefaRepository, CheckpointRepository checkpointRepository, TarefaResponseMapper tarefaResponseMapper) {
        this.tarefaRepository = tarefaRepository;
        this.checkpointRepository = checkpointRepository;
        this.tarefaResponseMapper = tarefaResponseMapper;
    }

    public SprintResponseDto toResponse(Sprint sprint, Integer idSprint) {
        boolean comImpedimento = tarefaRepository.existsImpedimentoBySprint(idSprint);
        List<Tarefa> tarefas = tarefaRepository.findByProjetoAndSprint(sprint.getProjeto().getIdProjeto(), sprint.getIdSprint());
        List<TarefaResponseDto> tarefasResponse = new ArrayList<>();
        for (Tarefa tarefa : tarefas) {
            tarefasResponse.add(tarefaResponseMapper.toResponse(tarefa));
        }
        List<Checkpoint> checkpoints = checkpointRepository.findAllByTarefa_Sprint_IdSprint(idSprint);
        Double progresso = ProgressoCalculator.execute(checkpoints);
        return SprintMapper.toDto(sprint, progresso, comImpedimento, tarefasResponse);
    }
}

