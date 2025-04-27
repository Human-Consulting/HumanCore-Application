package com.humanconsulting.humancore_api.controller.dto.token;

import com.humanconsulting.humancore_api.controller.dto.request.LoginRequestDto;
import com.humanconsulting.humancore_api.controller.dto.request.UsuarioRequestDto;
import com.humanconsulting.humancore_api.controller.dto.response.usuario.UsuarioResponseDto;
import com.humanconsulting.humancore_api.model.Usuario;

public class UsuarioTokenMapper {
    public static Usuario of(UsuarioRequestDto usuarioRequestDto) {
        Usuario usuario = new Usuario();

        usuario.setEmail(usuarioRequestDto.getEmail());
        usuario.setNome(usuarioRequestDto.getNome());
        usuario.setSenha(usuarioRequestDto.getSenha());

        return usuario;
    }

    public static Usuario of(LoginRequestDto loginRequestDto) {
        Usuario usuario = new Usuario();

        usuario.setEmail(loginRequestDto.getEmail());
        usuario.setSenha(loginRequestDto.getSenha());

        return usuario;
    }

    public static UsuarioTokenDto of(Usuario usuario, String token) {
        UsuarioTokenDto usuarioTokenDto = new UsuarioTokenDto();

        usuarioTokenDto.setIdUsuario(usuario.getIdUsuario());
        usuarioTokenDto.setEmail(usuario.getEmail());
        usuarioTokenDto.setNome(usuario.getNome());
        usuarioTokenDto.setToken(token);

        return usuarioTokenDto;
    }

    public static UsuarioResponseDto of(Usuario usuario) {
        UsuarioResponseDto usuarioResponseDto = new UsuarioResponseDto();

        usuarioResponseDto.setIdUsuario(usuario.getIdUsuario());
        usuarioResponseDto.setEmail(usuario.getEmail());
        usuarioResponseDto.setNome(usuario.getNome());

        return usuarioResponseDto;
    }
}
