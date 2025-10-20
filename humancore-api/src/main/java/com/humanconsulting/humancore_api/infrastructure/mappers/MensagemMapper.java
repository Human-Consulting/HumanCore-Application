package com.humanconsulting.humancore_api.infrastructure.mappers;

import com.humanconsulting.humancore_api.domain.entities.Mensagem;
import com.humanconsulting.humancore_api.infrastructure.entities.MensagemEntity;


public class MensagemMapper {
    public static MensagemEntity toEntity(Mensagem domain) {
        if (domain == null) return null;
        MensagemEntity entity = new MensagemEntity();
        entity.setIdMensagem(domain.getIdMensagem());
        entity.setConteudo(domain.getConteudo());
        entity.setHorario(domain.getHorario());
        entity.setUsuario(UsuarioMapper.toEntity(domain.getUsuario()));
        entity.setSala(SalaMapper.toEntity(domain.getSala()));
        return entity;
    }

    public static Mensagem toDomain(MensagemEntity entity) {
        if (entity == null) return null;
        Mensagem domain = new Mensagem();
        domain.setIdMensagem(entity.getIdMensagem());
        domain.setConteudo(entity.getConteudo());
        domain.setHorario(entity.getHorario());
        domain.setUsuario(UsuarioMapper.toDomain(entity.getUsuario()));
        domain.setSala(SalaMapper.toDomain(entity.getSala()));
        return domain;
    }
}
