package com.humanconsulting.humancore_api.infrastructure.mappers;

import com.humanconsulting.humancore_api.domain.entities.MensagemInfo;
import com.humanconsulting.humancore_api.infrastructure.entities.MensagemInfoEntity;

public class MensagemInfoMapper {
    public static MensagemInfoEntity toEntity(MensagemInfo domain) {
        if (domain == null) return null;
        MensagemInfoEntity entity = new MensagemInfoEntity();
        entity.setIdMensagemInfo(domain.getIdMensagemInfo());
        entity.setConteudo(domain.getConteudo());
        entity.setHorario(domain.getHorario());
        entity.setSala(SalaMapper.toEntity(domain.getSala()));
        return entity;
    }

    public static MensagemInfo toDomain(MensagemInfoEntity entity) {
        if (entity == null) return null;
        MensagemInfo domain = new MensagemInfo();
        domain.setIdMensagemInfo(entity.getIdMensagemInfo());
        domain.setConteudo(entity.getConteudo());
        domain.setHorario(entity.getHorario());
        domain.setSala(SalaMapper.toDomain(entity.getSala()));
        return domain;
    }
}
