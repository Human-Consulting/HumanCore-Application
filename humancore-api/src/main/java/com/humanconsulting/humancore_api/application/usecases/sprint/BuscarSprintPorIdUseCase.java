package com.humanconsulting.humancore_api.application.usecases.sprint;

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

    public BuscarSprintPorIdUseCase(SprintRepository sprintRepository, TarefaRepository tarefaRepository, CheckpointRepository checkpointRepository) {
        this.sprintRepository = sprintRepository;
        this.tarefaRepository = tarefaRepository;
        this.checkpointRepository = checkpointRepository;
    }

    public SprintResponseDto execute(Integer id) {
        Optional<Sprint> optSprint = sprintRepository.findById(id);
        if (optSprint.isEmpty()) throw new EntidadeNaoEncontradaException("SprintEntity não encontrada.");
        Sprint sprint = optSprint.get();
        List<Tarefa> tarefas = tarefaRepository.findBySprint_IdSprint(id);
        List<TarefaResponseDto> entregas = tarefas.stream().map(tarefa -> {
            List<Checkpoint> checkpoints = checkpointRepository.findAllByTarefa_IdTarefa(tarefa.getIdTarefa());
            double progresso = ProgressoCalculator.execute(checkpoints);
            return TarefaMapper.toDto(tarefa, checkpoints.stream().map(cp -> CheckpointMapper.toDto(cp)).collect(Collectors.toList()), progresso);
        }).collect(Collectors.toList());
        double progressoSprint = entregas.isEmpty() ? 0.0 : entregas.stream().mapToDouble(TarefaResponseDto::getProgresso).average().orElse(0.0);
        boolean comImpedimento = tarefaRepository.existsImpedimentoBySprint(id);
        return SprintMapper.toDto(sprint, progressoSprint, comImpedimento, entregas);
    }
}
