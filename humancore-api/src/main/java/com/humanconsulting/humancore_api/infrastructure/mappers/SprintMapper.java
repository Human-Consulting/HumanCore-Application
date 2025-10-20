package com.humanconsulting.humancore_api.infrastructure.mappers;

import com.humanconsulting.humancore_api.domain.entities.Sprint;
import com.humanconsulting.humancore_api.infrastructure.entities.SprintEntity;

public class SprintMapper {
    public static SprintEntity toEntity(Sprint domain) {
        if (domain == null) return null;
        SprintEntity entity = new SprintEntity();
        entity.setIdSprint(domain.getIdSprint());
        entity.setTitulo(domain.getTitulo());
        entity.setDescricao(domain.getDescricao());
        entity.setDtInicio(domain.getDtInicio());
        entity.setDtFim(domain.getDtFim());
        entity.setProjeto(ProjetoMapper.toEntity(domain.getProjeto()));
        return entity;
    }

    public static Sprint toDomain(SprintEntity entity) {
        if (entity == null) return null;
        Sprint domain = new Sprint();
        domain.setIdSprint(entity.getIdSprint());
        domain.setTitulo(entity.getTitulo());
        domain.setDescricao(entity.getDescricao());
        domain.setDtInicio(entity.getDtInicio());
        domain.setDtFim(entity.getDtFim());
        domain.setProjeto(ProjetoMapper.toDomain(entity.getProjeto()));
        return domain;
    }
}
