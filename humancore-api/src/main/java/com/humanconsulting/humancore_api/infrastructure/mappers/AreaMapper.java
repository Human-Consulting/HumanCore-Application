package com.humanconsulting.humancore_api.infrastructure.mappers;

import com.humanconsulting.humancore_api.domain.entities.Area;
import com.humanconsulting.humancore_api.infrastructure.entities.AreaEntity;

public class AreaMapper {
    public static AreaEntity toEntity(Area domain) {
        if (domain == null) return null;
        AreaEntity entity = new AreaEntity();
        entity.setNome(domain.getNome());
        entity.setValor(domain.getValor());
        return entity;
    }

    public static Area toDomain(AreaEntity entity) {
        if (entity == null) return null;
        Area domain = new Area();
        domain.setNome(entity.getNome());
        domain.setValor(entity.getValor());
        return domain;
    }
}
