package com.humanconsulting.humancore_api.infrastructure.mappers;

import com.humanconsulting.humancore_api.domain.entities.Checkpoint;
import com.humanconsulting.humancore_api.infrastructure.entities.CheckpointEntity;

public class CheckpointMapper {
    public static CheckpointEntity toEntity(Checkpoint domain) {
        if (domain == null) return null;
        CheckpointEntity entity = new CheckpointEntity();
        entity.setIdCheckpoint(domain.getIdCheckpoint());
        entity.setDescricao(domain.getDescricao());
        entity.setFinalizado(domain.getFinalizado());
        entity.setTarefa(TarefaMapper.toEntity(domain.getTarefa()));
        return entity;
    }

    public static Checkpoint toDomain(CheckpointEntity entity) {
        if (entity == null) return null;
        Checkpoint domain = new Checkpoint();
        domain.setIdCheckpoint(entity.getIdCheckpoint());
        domain.setDescricao(entity.getDescricao());
        domain.setFinalizado(entity.getFinalizado());
        domain.setTarefa(TarefaMapper.toDomain(entity.getTarefa()));
        return domain;
    }
}
