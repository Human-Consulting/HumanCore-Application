package com.humanconsulting.humancore_api.service;

import com.humanconsulting.humancore_api.controller.dto.atualizar.usuario.UsuarioAtualizarDto;
import com.humanconsulting.humancore_api.controller.dto.request.LoginRequestDto;
import com.humanconsulting.humancore_api.controller.dto.request.UsuarioRequestDto;
import com.humanconsulting.humancore_api.controller.dto.response.UsuarioResponseDto;
import com.humanconsulting.humancore_api.exception.EntidadeConflitanteException;
import com.humanconsulting.humancore_api.exception.EntidadeSemRetornoException;
import com.humanconsulting.humancore_api.mapper.UsuarioMapper;
import com.humanconsulting.humancore_api.model.Usuario;
import com.humanconsulting.humancore_api.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository repository;

    public UsuarioResponseDto cadastrar(UsuarioRequestDto usuarioRequestDto) {
        if (repository.existsByEmail(usuarioRequestDto.getEmail())) throw new EntidadeConflitanteException(usuarioRequestDto.getEmail() + " já registrado.");
        Usuario usuario = UsuarioMapper.toEntity(usuarioRequestDto);
        return UsuarioMapper.toDto(repository.insert(usuario));
    }

    public UsuarioResponseDto buscarPorId(Integer id) {
        return UsuarioMapper.toDto(repository.selectWhereId(id));
    }

    public List<UsuarioResponseDto> listar() {
        List<Usuario> all = repository.selectAll();
        if (all.isEmpty()) throw new EntidadeSemRetornoException("Nenhuma empresa registrada");
        List<UsuarioResponseDto> allResponse = new ArrayList<>();
        for (Usuario usuario : all) {
            allResponse.add(UsuarioMapper.toDto(usuario));
        }
        return allResponse;
    }

    public void deletar(Integer id) {
        repository.deleteWhere(id);
    }

    public UsuarioResponseDto atualizarPorId(Integer idUsuario, UsuarioAtualizarDto usuarioAtualizar) {
        repository.existsById(idUsuario);

        Usuario usuario = repository.update(idUsuario, usuarioAtualizar);
        return UsuarioMapper.toDto(usuario);
    }

    public UsuarioResponseDto antenticar(LoginRequestDto usuarioAutenticar) {
        Usuario usuario = repository.antenticar(usuarioAutenticar);
        if (usuario == null) throw new EntidadeSemRetornoException("Usuário não autenticado");

        return UsuarioMapper.toDto(usuario);
    }
}
