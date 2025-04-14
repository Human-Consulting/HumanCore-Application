package com.humanconsulting.humancore_api.mapper;

import com.humanconsulting.humancore_api.controller.dto.request.UsuarioRequestDto;
import com.humanconsulting.humancore_api.controller.dto.response.LoginResponseDto;
import com.humanconsulting.humancore_api.controller.dto.response.UsuarioResponseDto;
import com.humanconsulting.humancore_api.model.Usuario;

public class UsuarioMapper {

    public static Usuario toEntity(UsuarioRequestDto usuarioRequestDto) {
        return new Usuario(usuarioRequestDto.getNome(), usuarioRequestDto.getEmail(), usuarioRequestDto.getSenha(), usuarioRequestDto.getCargo(), usuarioRequestDto.getArea(), usuarioRequestDto.getPermissao(), usuarioRequestDto.getFkEmpresa());
    }

    public static LoginResponseDto toLoginDto(Usuario usuario, String nomeEmpresa) {
        return new LoginResponseDto(usuario.getIdUsuario(), usuario.getNome(), usuario.getPermissao(), usuario.getFkEmpresa(), nomeEmpresa);
    }

    public static UsuarioResponseDto toUsuarioDto(Usuario usuario) {
        return new UsuarioResponseDto(usuario.getIdUsuario(), usuario.getNome(), usuario.getEmail(), usuario.getSenha(), usuario.getCargo(), usuario.getArea(), usuario.getPermissao());
    }
}
