package com.humanconsulting.humancore_api.infrastructure.mappers;

import com.humanconsulting.humancore_api.domain.entities.Sala;
import com.humanconsulting.humancore_api.infrastructure.entities.SalaEntity;

import java.util.stream.Collectors;

public class SalaMapper {
    public static SalaEntity toEntity(Sala domain) {
        if (domain == null) return null;
        SalaEntity entity = new SalaEntity();
        entity.setIdSala(domain.getIdSala());
        entity.setNome(domain.getNome());
        entity.setUrlImagem(domain.getUrlImagem());
        entity.setProjeto(ProjetoMapper.toEntity(domain.getProjeto()));
        entity.setEmpresa(EmpresaMapper.toEntity(domain.getEmpresa()));
        if (domain.getUsuarios() != null) {
            entity.setUsuarios(domain.getUsuarios().stream().map(UsuarioMapper::toEntity).collect(Collectors.toSet()));
        }
        return entity;
    }

    public static Sala toDomain(SalaEntity entity) {
        if (entity == null) return null;
        Sala domain = new Sala();
        domain.setIdSala(entity.getIdSala());
        domain.setNome(entity.getNome());
        domain.setUrlImagem(entity.getUrlImagem());
        domain.setProjeto(ProjetoMapper.toDomain(entity.getProjeto()));
        domain.setEmpresa(EmpresaMapper.toDomain(entity.getEmpresa()));
        if (entity.getUsuarios() != null) {
            domain.setUsuarios(entity.getUsuarios().stream().map(UsuarioMapper::toDomain).collect(Collectors.toSet()));
        }
        return domain;
    }
}
