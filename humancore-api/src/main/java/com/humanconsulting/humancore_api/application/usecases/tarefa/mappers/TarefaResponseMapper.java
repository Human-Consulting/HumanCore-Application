package com.humanconsulting.humancore_api.application.usecases.tarefa.mappers;

import com.humanconsulting.humancore_api.domain.entities.Checkpoint;
import com.humanconsulting.humancore_api.domain.entities.Tarefa;
import com.humanconsulting.humancore_api.domain.repositories.CheckpointRepository;
import com.humanconsulting.humancore_api.domain.utils.ProgressoCalculator;
import com.humanconsulting.humancore_api.web.dtos.response.checkpoint.CheckpointResponseDto;
import com.humanconsulting.humancore_api.web.dtos.response.empresa.EmpresaResponseLoginDto;
import com.humanconsulting.humancore_api.web.dtos.response.projeto.ProjetoResponseLoginDto;
import com.humanconsulting.humancore_api.web.dtos.response.sprint.SprintResponseLoginDto;
import com.humanconsulting.humancore_api.web.dtos.response.tarefa.TarefaLoginResponseDto;
import com.humanconsulting.humancore_api.web.dtos.response.tarefa.TarefaResponseDto;
import com.humanconsulting.humancore_api.web.mappers.*;

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

    public TarefaLoginResponseDto toLoginResponse(Tarefa tarefa) {
        List<Checkpoint> checkpoints = checkpointRepository.findAllByTarefa_IdTarefa(tarefa.getIdTarefa());
        List<CheckpointResponseDto> checkpointResponseDtos = new ArrayList<>();
        for (Checkpoint checkpoint : checkpoints) {
            checkpointResponseDtos.add(CheckpointMapper.toDto(checkpoint));
        }
        Double progresso = ProgressoCalculator.execute(checkpoints);
        EmpresaResponseLoginDto empresa = EmpresaMapper.toResponseLoginDto(tarefa.getSprint().getProjeto().getEmpresa());
        ProjetoResponseLoginDto projeto = ProjetoMapper.toResponseLoginDto(tarefa.getSprint().getProjeto(), empresa);
        SprintResponseLoginDto sprint = SprintMapper.toResponseLoginDto(tarefa.getSprint(), projeto);
        return TarefaMapper.toDtoLogin(tarefa, checkpointResponseDtos, progresso, sprint);
    }
}

