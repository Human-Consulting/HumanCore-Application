package com.humanconsulting.humancore_api.mapper;

import com.humanconsulting.humancore_api.controller.dto.request.UsuarioRequestDto;
import com.humanconsulting.humancore_api.controller.dto.response.usuario.LoginResponseDto;
import com.humanconsulting.humancore_api.controller.dto.response.usuario.UsuarioResponseDto;
import com.humanconsulting.humancore_api.model.Usuario;

import java.util.List;

public class UsuarioMapper {

    public static Usuario toEntity(UsuarioRequestDto usuarioRequestDto) {
        return new Usuario(usuarioRequestDto.getNome(), usuarioRequestDto.getEmail(), usuarioRequestDto.getSenha(), usuarioRequestDto.getCargo(), usuarioRequestDto.getArea(), usuarioRequestDto.getPermissao(), usuarioRequestDto.getFkEmpresa());
    }

    public static LoginResponseDto toLoginDto(Usuario usuario, String nomeEmpresa, Integer qtdTarefas, Boolean comImpedimento, List<Integer> projetosVinculados) {
        return new LoginResponseDto(usuario.getIdUsuario(), usuario.getNome(), usuario.getPermissao(), usuario.getFkEmpresa(), nomeEmpresa, qtdTarefas, comImpedimento, projetosVinculados);
    }

    public static UsuarioResponseDto toUsuarioDto(Usuario usuario, Integer qtdTarefas, Boolean comImpedimento) {
        return new UsuarioResponseDto(usuario.getIdUsuario(), usuario.getNome(), usuario.getEmail(), usuario.getSenha(), usuario.getCargo(), usuario.getArea(), usuario.getPermissao(), qtdTarefas, comImpedimento);
    }
}
