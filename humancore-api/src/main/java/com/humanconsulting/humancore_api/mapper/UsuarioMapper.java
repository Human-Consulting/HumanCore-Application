package com.humanconsulting.humancore_api.mapper;

import com.humanconsulting.humancore_api.controller.dto.atualizar.usuario.UsuarioAtualizarDto;
import com.humanconsulting.humancore_api.controller.dto.atualizar.usuario.UsuarioAtualizarSenhaDto;
import com.humanconsulting.humancore_api.controller.dto.request.UsuarioRequestDto;
import com.humanconsulting.humancore_api.controller.dto.response.tarefa.TarefaResponseDto;
import com.humanconsulting.humancore_api.controller.dto.response.usuario.LoginResponseDto;
import com.humanconsulting.humancore_api.controller.dto.response.usuario.UsuarioResponseDto;
import com.humanconsulting.humancore_api.model.Empresa;
import com.humanconsulting.humancore_api.model.Usuario;

import java.util.List;

public class UsuarioMapper {

    public static Usuario toEntity(UsuarioRequestDto usuarioRequestDto) {
        return Usuario.builder()
                .nome(usuarioRequestDto.getNome())
                .email(usuarioRequestDto.getEmail())
                .cargo(usuarioRequestDto.getCargo())
                .area(usuarioRequestDto.getArea())
                .permissao(usuarioRequestDto.getPermissao())
                .empresa(null)
                .build();
    }

    public static Usuario toEntity(UsuarioAtualizarDto usuarioAtualizarDto, Integer idUsuario, Empresa empresa, String senha) {
        return Usuario.builder()
                .idUsuario(idUsuario)
                .nome(usuarioAtualizarDto.getNome())
                .email(usuarioAtualizarDto.getEmail())
                .senha(senha)
                .cargo(usuarioAtualizarDto.getCargo())
                .area(usuarioAtualizarDto.getArea())
                .permissao(usuarioAtualizarDto.getPermissao())
                .empresa(empresa)
                .build();
    }

    public static Usuario toEntity(Usuario usuario, UsuarioAtualizarSenhaDto usuarioAtualizarDto, Integer idUsuario, Empresa empresa) {
        return Usuario.builder()
                .idUsuario(idUsuario)
                .nome(usuario.getNome())
                .email(usuario.getEmail())
                .senha(usuarioAtualizarDto.getSenhaAtual())
                .cargo(usuario.getCargo())
                .area(usuario.getArea())
                .permissao(usuario.getPermissao())
                .empresa(empresa)
                .build();
    }

    public static LoginResponseDto toLoginDto(
            Usuario usuario,
            String nomeEmpresa,
            Integer qtdTarefas,
            Boolean comImpedimento,
            List<Integer> projetosVinculados,
            List<TarefaResponseDto> tarefasVinculadas,
            String token) {

        return LoginResponseDto.builder()
                .idUsuario(usuario.getIdUsuario())
                .nome(usuario.getNome())
                .email(usuario.getEmail())
                .permissao(usuario.getPermissao())
                .empresa(usuario.getEmpresa())
                .nomeEmpresa(nomeEmpresa)
                .qtdTarefas(qtdTarefas)
                .comImpedimento(comImpedimento)
                .projetosVinculados(projetosVinculados)
                .tarefasVinculadas(tarefasVinculadas)
                .token(token)
                .cores(usuario.getCores())
                .build();
    }

    public static UsuarioResponseDto toUsuarioDto(Usuario usuario, Integer qtdTarefas, Boolean comImpedimento) {
        return UsuarioResponseDto.builder()
                .idUsuario(usuario.getIdUsuario())
                .nome(usuario.getNome())
                .email(usuario.getEmail())
                .cargo(usuario.getCargo())
                .area(usuario.getArea())
                .permissao(usuario.getPermissao())
                .qtdTarefas(qtdTarefas)
                .comImpedimento(comImpedimento)
                .cores(usuario.getCores())
                .build();
    }
}
