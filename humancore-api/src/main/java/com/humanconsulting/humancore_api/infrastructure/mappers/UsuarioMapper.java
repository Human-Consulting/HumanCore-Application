package com.humanconsulting.humancore_api.infrastructure.mappers;

import com.humanconsulting.humancore_api.domain.entities.Usuario;
import com.humanconsulting.humancore_api.infrastructure.entities.UsuarioEntity;

import java.util.stream.Collectors;

public class UsuarioMapper {
    public static UsuarioEntity toEntity(Usuario usuario) {
        if (usuario == null) return null;
        UsuarioEntity entity = new UsuarioEntity();
        entity.setIdUsuario(usuario.getIdUsuario());
        entity.setNome(usuario.getNome());
        entity.setEmail(usuario.getEmail());
        entity.setSenha(usuario.getSenha());
        entity.setCargo(usuario.getCargo());
        entity.setArea(usuario.getArea());
        entity.setPermissao(usuario.getPermissao());
        entity.setCores(usuario.getCores());
        entity.setEmpresa(EmpresaMapper.toEntity(usuario.getEmpresa()));
        if (usuario.getSalas() != null) {
            entity.setSalas(usuario.getSalas().stream().map(SalaMapper::toEntity).collect(Collectors.toSet()));
        }
        return entity;
    }

    public static Usuario toDomain(UsuarioEntity entity) {
        if (entity == null) return null;
        Usuario usuario = new Usuario();
        usuario.setIdUsuario(entity.getIdUsuario());
        usuario.setNome(entity.getNome());
        usuario.setEmail(entity.getEmail());
        usuario.setSenha(entity.getSenha());
        usuario.setCargo(entity.getCargo());
        usuario.setArea(entity.getArea());
        usuario.setPermissao(entity.getPermissao());
        usuario.setCores(entity.getCores());
        usuario.setEmpresa(EmpresaMapper.toDomain(entity.getEmpresa()));
        /*if (entity.getSalas() != null) {
            usuario.setSalas(entity.getSalas().stream().map(SalaMapper::toDomain).collect(Collectors.toSet()));
        }*/
        return usuario;
    }
}
