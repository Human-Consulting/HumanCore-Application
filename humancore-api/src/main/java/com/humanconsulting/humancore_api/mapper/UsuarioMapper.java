package com.humanconsulting.humancore_api.mapper;

import com.humanconsulting.humancore_api.controller.dto.request.UsuarioRequestDto;
import com.humanconsulting.humancore_api.controller.dto.response.UsuarioResponseDto;
import com.humanconsulting.humancore_api.model.Usuario;

public class UsuarioMapper {

    public static Usuario toEntity(UsuarioRequestDto usuarioRequestDto) {
        return new Usuario(usuarioRequestDto.getNome(), usuarioRequestDto.getEmail(), usuarioRequestDto.getSenha(), usuarioRequestDto.getCargo(), usuarioRequestDto.getArea(), usuarioRequestDto.getPermissao(), usuarioRequestDto.getFkEmpresa());
    }

    public static UsuarioResponseDto toDto(Usuario usuario) {
        return new UsuarioResponseDto(usuario.getIdUsuario(), usuario.getNome(), usuario.getEmail(), usuario.getPermissao(), usuario.getFkEmpresa());
    }
}
