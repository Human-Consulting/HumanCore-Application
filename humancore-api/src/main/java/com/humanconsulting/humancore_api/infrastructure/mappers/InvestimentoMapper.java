package com.humanconsulting.humancore_api.infrastructure.mappers;

import com.humanconsulting.humancore_api.domain.entities.Investimento;
import com.humanconsulting.humancore_api.infrastructure.entities.InvestimentoEntity;

public class InvestimentoMapper {
    public static InvestimentoEntity toEntity(Investimento domain) {
        if (domain == null) return null;
        InvestimentoEntity entity = new InvestimentoEntity();
        entity.setIdInvestimento(domain.getIdInvestimento());
        entity.setDescricao(domain.getDescricao());
        entity.setValor(domain.getValor());
        entity.setDtInvestimento(domain.getDtInvestimento());
        entity.setProjeto(ProjetoMapper.toEntity(domain.getProjeto()));
        return entity;
    }

    public static Investimento toDomain(InvestimentoEntity entity) {
        if (entity == null) return null;
        Investimento domain = new Investimento();
        domain.setIdInvestimento(entity.getIdInvestimento());
        domain.setDescricao(entity.getDescricao());
        domain.setValor(entity.getValor());
        domain.setDtInvestimento(entity.getDtInvestimento());
        domain.setProjeto(ProjetoMapper.toDomain(entity.getProjeto()));
        return domain;
    }
}
