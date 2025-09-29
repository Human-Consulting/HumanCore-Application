package com.humanconsulting.humancore_api.web.mappers;

import com.humanconsulting.humancore_api.domain.entities.Checkpoint;
import com.humanconsulting.humancore_api.domain.entities.Tarefa;
import com.humanconsulting.humancore_api.web.dtos.request.CheckpointRequestDto;
import com.humanconsulting.humancore_api.web.dtos.response.checkpoint.CheckpointResponseDto;

public class CheckpointMapper {
    public static Checkpoint toEntity(CheckpointRequestDto checkpointRequestDto, Tarefa tarefa) {
        Checkpoint checkpoint = new Checkpoint();
        checkpoint.setDescricao(checkpointRequestDto.getDescricao());
        checkpoint.setFinalizado(checkpointRequestDto.getFinalizado());
        checkpoint.setTarefa(tarefa);
        return checkpoint;
    }

    public static CheckpointResponseDto toDto(Checkpoint checkpoint) {
        return CheckpointResponseDto.builder()
                .idCheckpoint(checkpoint.getIdCheckpoint())
                .descricao(checkpoint.getDescricao())
                .finalizado(checkpoint.getFinalizado())
                .build();
    }
}
