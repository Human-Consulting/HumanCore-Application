package com.humanconsulting.humancore_api.application.usecases.sprint;

import com.humanconsulting.humancore_api.application.usecases.sprint.mappers.SprintResponseMapper;
import com.humanconsulting.humancore_api.domain.entities.Sprint;
import com.humanconsulting.humancore_api.domain.entities.Tarefa;
import com.humanconsulting.humancore_api.domain.entities.Checkpoint;
import com.humanconsulting.humancore_api.domain.exception.EntidadeNaoEncontradaException;
import com.humanconsulting.humancore_api.domain.repositories.SprintRepository;
import com.humanconsulting.humancore_api.domain.repositories.TarefaRepository;
import com.humanconsulting.humancore_api.domain.repositories.CheckpointRepository;
import com.humanconsulting.humancore_api.domain.utils.ProgressoCalculator;
import com.humanconsulting.humancore_api.web.dtos.response.sprint.SprintResponseDto;
import com.humanconsulting.humancore_api.web.dtos.response.tarefa.TarefaResponseDto;
import com.humanconsulting.humancore_api.web.mappers.CheckpointMapper;
import com.humanconsulting.humancore_api.web.mappers.SprintMapper;
import com.humanconsulting.humancore_api.web.mappers.TarefaMapper;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class BuscarSprintPorIdUseCase {
    private final SprintRepository sprintRepository;
    private final TarefaRepository tarefaRepository;
    private final CheckpointRepository checkpointRepository;
    private final SprintResponseMapper sprintResponseMapper;

    public BuscarSprintPorIdUseCase(SprintRepository sprintRepository, TarefaRepository tarefaRepository, CheckpointRepository checkpointRepository, SprintResponseMapper sprintResponseMapper) {
        this.sprintRepository = sprintRepository;
        this.tarefaRepository = tarefaRepository;
        this.checkpointRepository = checkpointRepository;
        this.sprintResponseMapper = sprintResponseMapper;
    }

    public SprintResponseDto execute(Integer id) {
        Optional<Sprint> optSprint = sprintRepository.findById(id);
        if (optSprint.isEmpty()) throw new EntidadeNaoEncontradaException("SprintEntity não encontrada.");
        return sprintResponseMapper.toResponse(optSprint.get());
    }
}
