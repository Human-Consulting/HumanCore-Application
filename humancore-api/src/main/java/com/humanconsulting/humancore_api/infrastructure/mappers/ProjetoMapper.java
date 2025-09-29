package com.humanconsulting.humancore_api.infrastructure.mappers;

import com.humanconsulting.humancore_api.domain.entities.Projeto;
import com.humanconsulting.humancore_api.infrastructure.entities.ProjetoEntity;

public class ProjetoMapper {
    public static ProjetoEntity toEntity(Projeto domain) {
        if (domain == null) return null;
        ProjetoEntity entity = new ProjetoEntity();
        entity.setIdProjeto(domain.getIdProjeto());
        entity.setTitulo(domain.getTitulo());
        entity.setDescricao(domain.getDescricao());
        entity.setOrcamento(domain.getOrcamento());
        entity.setUrlImagem(domain.getUrlImagem());
        entity.setEmpresa(EmpresaMapper.toEntity(domain.getEmpresa()));
        entity.setResponsavel(UsuarioMapper.toEntity(domain.getResponsavel()));
        return entity;
    }

    public static Projeto toDomain(ProjetoEntity entity) {
        if (entity == null) return null;
        Projeto domain = new Projeto();
        domain.setIdProjeto(entity.getIdProjeto());
        domain.setTitulo(entity.getTitulo());
        domain.setDescricao(entity.getDescricao());
        domain.setOrcamento(entity.getOrcamento());
        domain.setUrlImagem(entity.getUrlImagem());
        domain.setEmpresa(EmpresaMapper.toDomain(entity.getEmpresa()));
        domain.setResponsavel(UsuarioMapper.toDomain(entity.getResponsavel()));
        return domain;
    }
}
