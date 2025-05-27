package com.humanconsulting.humancore_api.mapper;

import com.humanconsulting.humancore_api.controller.dto.request.CheckpointRequestDto;
import com.humanconsulting.humancore_api.controller.dto.response.checkpoint.CheckpointResponseDto;
import com.humanconsulting.humancore_api.model.Checkpoint;
import com.humanconsulting.humancore_api.model.Tarefa;

public class CheckpointMapper {
    public static Checkpoint toEntity(CheckpointRequestDto checkpointRequestDto, Tarefa tarefa) {
        return Checkpoint.builder()
                .descricao(checkpointRequestDto.getDescricao())
                .finalizado(checkpointRequestDto.getFinalizado())
                .tarefa(tarefa)
                .build();
    }

    public static CheckpointResponseDto toDto(Checkpoint checkpoint) {
        return CheckpointResponseDto.builder()
                .idCheckpoint(checkpoint.getIdCheckpoint())
                .descricao(checkpoint.getDescricao())
                .finalizado(checkpoint.getFinalizado())
                .build();
    }
}
