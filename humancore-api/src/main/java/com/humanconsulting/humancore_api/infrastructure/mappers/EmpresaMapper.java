package com.humanconsulting.humancore_api.infrastructure.mappers;

import com.humanconsulting.humancore_api.domain.entities.Empresa;
import com.humanconsulting.humancore_api.infrastructure.entities.EmpresaEntity;

public class EmpresaMapper {
    public static EmpresaEntity toEntity(Empresa domain) {
        if (domain == null) return null;
        EmpresaEntity entity = new EmpresaEntity();
        entity.setIdEmpresa(domain.getIdEmpresa());
        entity.setNome(domain.getNome());
        entity.setCnpj(domain.getCnpj());
        entity.setUrlImagem(domain.getUrlImagem());
        return entity;
    }

    public static Empresa toDomain(EmpresaEntity entity) {
        if (entity == null) return null;
        Empresa domain = new Empresa();
        domain.setIdEmpresa(entity.getIdEmpresa());
        domain.setNome(entity.getNome());
        domain.setCnpj(entity.getCnpj());
        domain.setUrlImagem(entity.getUrlImagem());
        return domain;
    }
}

