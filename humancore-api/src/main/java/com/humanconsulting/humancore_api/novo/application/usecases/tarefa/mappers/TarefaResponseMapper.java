package com.humanconsulting.humancore_api.novo.application.usecases.tarefa.mappers;

import com.humanconsulting.humancore_api.novo.domain.entities.Checkpoint;
import com.humanconsulting.humancore_api.novo.domain.entities.Tarefa;
import com.humanconsulting.humancore_api.novo.domain.repositories.CheckpointRepository;
import com.humanconsulting.humancore_api.novo.domain.utils.ProgressoCalculator;
import com.humanconsulting.humancore_api.novo.web.dtos.response.checkpoint.CheckpointResponseDto;
import com.humanconsulting.humancore_api.novo.web.dtos.response.tarefa.TarefaResponseDto;
import com.humanconsulting.humancore_api.novo.web.mappers.CheckpointMapper;
import com.humanconsulting.humancore_api.novo.web.mappers.TarefaMapper;

import java.util.ArrayList;
import java.util.List;

public class TarefaResponseMapper {
    private final CheckpointRepository checkpointRepository;

    public TarefaResponseMapper(CheckpointRepository checkpointRepository) {
        this.checkpointRepository = checkpointRepository;
    }

    public TarefaResponseDto toResponse(Tarefa tarefa) {
        List<Checkpoint> checkpoints = checkpointRepository.findAllByTarefa_IdTarefa(tarefa.getIdTarefa());
        List<CheckpointResponseDto> checkpointResponseDtos = new ArrayList<>();
        for (Checkpoint checkpoint : checkpoints) {
            checkpointResponseDtos.add(CheckpointMapper.toDto(checkpoint));
        }
        Double progresso = ProgressoCalculator.execute(checkpoints);
        return TarefaMapper.toDto(tarefa, checkpointResponseDtos, progresso);
    }
}

