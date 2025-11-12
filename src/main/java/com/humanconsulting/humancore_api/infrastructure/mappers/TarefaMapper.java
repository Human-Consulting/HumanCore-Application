package com.humanconsulting.humancore_api.infrastructure.mappers;

import com.humanconsulting.humancore_api.domain.entities.Tarefa;
import com.humanconsulting.humancore_api.infrastructure.entities.TarefaEntity;

public class TarefaMapper {
    public static TarefaEntity toEntity(Tarefa domain) {
        if (domain == null) return null;
        TarefaEntity entity = new TarefaEntity();
        entity.setIdTarefa(domain.getIdTarefa());
        entity.setTitulo(domain.getTitulo());
        entity.setDescricao(domain.getDescricao());
        entity.setDtInicio(domain.getDtInicio());
        entity.setDtFim(domain.getDtFim());
        entity.setComImpedimento(domain.getComImpedimento());
        entity.setComentario(domain.getComentario());
        entity.setSprint(SprintMapper.toEntity(domain.getSprint()));
        entity.setResponsavel(UsuarioMapper.toEntity(domain.getResponsavel()));
        return entity;
    }

    public static Tarefa toDomain(TarefaEntity entity) {
        if (entity == null) return null;
        Tarefa domain = new Tarefa();
        domain.setIdTarefa(entity.getIdTarefa());
        domain.setTitulo(entity.getTitulo());
        domain.setDescricao(entity.getDescricao());
        domain.setDtInicio(entity.getDtInicio());
        domain.setDtFim(entity.getDtFim());
        domain.setComImpedimento(entity.getComImpedimento());
        domain.setComentario(entity.getComentario());
        domain.setSprint(SprintMapper.toDomain(entity.getSprint()));
        domain.setResponsavel(UsuarioMapper.toDomain(entity.getResponsavel()));
        return domain;
    }
}
