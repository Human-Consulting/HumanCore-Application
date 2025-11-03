package com.humanconsulting.humancore_api.web.mappers;

import com.humanconsulting.humancore_api.domain.entities.Usuario;
import com.humanconsulting.humancore_api.domain.entities.Empresa;
import com.humanconsulting.humancore_api.web.dtos.atualizar.usuario.UsuarioAtualizarDto;
import com.humanconsulting.humancore_api.web.dtos.atualizar.usuario.UsuarioAtualizarSenhaDto;
import com.humanconsulting.humancore_api.web.dtos.request.UsuarioRequestDto;
import com.humanconsulting.humancore_api.web.dtos.response.tarefa.TarefaLoginResponseDto;
import com.humanconsulting.humancore_api.web.dtos.response.tarefa.TarefaResponseDto;
import com.humanconsulting.humancore_api.web.dtos.response.usuario.LoginResponseDto;
import com.humanconsulting.humancore_api.web.dtos.response.usuario.UsuarioResponseDto;
import com.humanconsulting.humancore_api.web.dtos.response.usuario.UsuarioSprintResponseDto;

import java.util.List;
import java.util.Optional;

public class UsuarioMapper {

    public static Usuario toEntity(UsuarioRequestDto usuarioRequestDto) {
        Usuario usuario = new Usuario();
        usuario.setNome(usuarioRequestDto.getNome());
        usuario.setEmail(usuarioRequestDto.getEmail());
        usuario.setCargo(usuarioRequestDto.getCargo());
        usuario.setArea(usuarioRequestDto.getArea());
        usuario.setPermissao(usuarioRequestDto.getPermissao());
        usuario.setEmpresa(Optional.ofNullable(null));
        return usuario;
    }

    public static Usuario toEntity(UsuarioAtualizarDto usuarioAtualizarDto, Integer idUsuario, Empresa empresa, String senha) {
        Usuario usuario = new Usuario();
        usuario.setIdUsuario(idUsuario);
        usuario.setNome(usuarioAtualizarDto.getNome());
        usuario.setEmail(usuarioAtualizarDto.getEmail());
        usuario.setSenha(senha);
        usuario.setCargo(usuarioAtualizarDto.getCargo());
        usuario.setArea(usuarioAtualizarDto.getArea());
        usuario.setPermissao(usuarioAtualizarDto.getPermissao());
        usuario.setEmpresa(empresa);
        return usuario;
    }

    public static Usuario toEntity(Usuario usuario, UsuarioAtualizarSenhaDto usuarioAtualizarDto, Integer idUsuario, Empresa empresa) {
        Usuario novoUsuario = new Usuario();
        novoUsuario.setIdUsuario(idUsuario);
        novoUsuario.setNome(usuario.getNome());
        novoUsuario.setEmail(usuario.getEmail());
        novoUsuario.setSenha(usuarioAtualizarDto.getSenhaAtual());
        novoUsuario.setCargo(usuario.getCargo());
        novoUsuario.setArea(usuario.getArea());
        novoUsuario.setPermissao(usuario.getPermissao());
        novoUsuario.setEmpresa(empresa);
        return novoUsuario;
    }

    public static LoginResponseDto toLoginDto(
            Usuario usuario,
            String nomeEmpresa,
            Integer qtdTarefas,
            Boolean comImpedimento,
            List<Integer> projetosVinculados,
            List<TarefaLoginResponseDto> tarefasVinculadas,
            String token) {

        return LoginResponseDto.builder()
                .idUsuario(usuario.getIdUsuario())
                .nome(usuario.getNome())
                .email(usuario.getEmail())
                .permissao(usuario.getPermissao())
                .idEmpresa(usuario.getEmpresa().getIdEmpresa())
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

    public static UsuarioSprintResponseDto toUsuarioSprintDto(Usuario usuario) {
        return UsuarioSprintResponseDto.builder()
                .idUsuario(usuario.getIdUsuario())
                .nome(usuario.getNome())
                .cargo(usuario.getCargo())
                .build();
    }
}
