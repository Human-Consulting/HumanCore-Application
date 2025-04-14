package com.humanconsulting.humancore_api.service;

import com.humanconsulting.humancore_api.controller.dto.atualizar.usuario.UsuarioAtualizarDto;
import com.humanconsulting.humancore_api.controller.dto.request.LoginRequestDto;
import com.humanconsulting.humancore_api.controller.dto.request.UsuarioRequestDto;
import com.humanconsulting.humancore_api.controller.dto.response.LoginResponseDto;
import com.humanconsulting.humancore_api.controller.dto.response.UsuarioResponseDto;
import com.humanconsulting.humancore_api.enums.PermissaoEnum;
import com.humanconsulting.humancore_api.exception.AcessoNegadoException;
import com.humanconsulting.humancore_api.exception.EntidadeConflitanteException;
import com.humanconsulting.humancore_api.exception.EntidadeNaoEncontradaException;
import com.humanconsulting.humancore_api.exception.EntidadeSemRetornoException;
import com.humanconsulting.humancore_api.mapper.UsuarioMapper;
import com.humanconsulting.humancore_api.model.Usuario;
import com.humanconsulting.humancore_api.repository.EmpresaRepository;
import com.humanconsulting.humancore_api.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UsuarioService {

    @Autowired private UsuarioRepository usuarioRepository;

    @Autowired private EmpresaRepository empresaRepository;

    public UsuarioResponseDto cadastrar(UsuarioRequestDto usuarioRequestDto) {
        if (usuarioRepository.existsByEmail(usuarioRequestDto.getEmail())) throw new EntidadeConflitanteException(usuarioRequestDto.getEmail() + " já registrado.");
        Usuario usuario = UsuarioMapper.toEntity(usuarioRequestDto);
        return UsuarioMapper.toUsuarioDto(usuarioRepository.insert(usuario));
    }

    public UsuarioResponseDto buscarPorId(Integer id) {
        return UsuarioMapper.toUsuarioDto(usuarioRepository.selectWhereId(id));
    }

    public List<UsuarioResponseDto> listar() {
        List<Usuario> all = usuarioRepository.selectAll();
        if (all.isEmpty()) throw new EntidadeSemRetornoException("Nenhuma empresa registrada");
        List<UsuarioResponseDto> allResponse = new ArrayList<>();
        for (Usuario usuario : all) {
            allResponse.add(UsuarioMapper.toUsuarioDto(usuario));
        }
        return allResponse;
    }

    public void deletar(Integer id) {
        usuarioRepository.deleteWhere(id);
    }

    public UsuarioResponseDto atualizarPorId(Integer idUsuario, UsuarioAtualizarDto usuarioAtualizar) {
        usuarioRepository.existsById(idUsuario);

        Usuario usuarioAlvo = usuarioRepository.selectWhereId(idUsuario);

        Usuario usuarioEditor = usuarioRepository.selectWhereId(usuarioAtualizar.getIdEditor());

        PermissaoEnum permissaoEditor;
        PermissaoEnum permissaoAlvo;
        try {
            permissaoEditor = PermissaoEnum.valueOf(usuarioAtualizar.getPermissaoEditor());
            permissaoAlvo = PermissaoEnum.valueOf(usuarioAlvo.getPermissao());
        } catch (IllegalArgumentException e) {
            throw new AcessoNegadoException("Permissão inválida informada.");
        }

        if (usuarioEditor.getIdUsuario().equals(usuarioAlvo.getIdUsuario())) {
            if (!permissaoEditor.temPermissao("MODIFICAR_PROPRIO")) {
                throw new AcessoNegadoException("Você não tem permissão para editar suas próprias informações.");
            }
        } else {
            String chavePermissao = "MODIFICAR_" + permissaoAlvo.name();
            if (!permissaoEditor.temPermissao(chavePermissao)) {
                throw new AcessoNegadoException("Você não tem permissão para editar este tipo de usuário.");
            }
        }

        if (usuarioEditor.getIdUsuario().equals(usuarioAlvo.getIdUsuario()) &&
                !usuarioAtualizar.getPermissao().equals(usuarioAlvo.getPermissao())) {
            throw new AcessoNegadoException("Você não pode alterar sua própria permissão.");
        }

        Usuario usuarioAtualizado = usuarioRepository.update(idUsuario, usuarioAtualizar);
        return UsuarioMapper.toUsuarioDto(usuarioAtualizado);
    }

    public LoginResponseDto antenticar(LoginRequestDto usuarioAutenticar) {
        Usuario usuario = usuarioRepository.antenticar(usuarioAutenticar);
        if (usuario == null) throw new EntidadeSemRetornoException("Usuário não autenticado");
        String nomeEmpresa = empresaRepository.selectWhereId(usuario.getFkEmpresa()).getNome();
        return UsuarioMapper.toLoginDto(usuario, nomeEmpresa);
    }

    public List<UsuarioResponseDto> listarPorEmpresa(Integer idEmpresa) {
        List<Usuario> all = usuarioRepository.selectWhereIdEmpresa(idEmpresa);
        if (all.isEmpty()) throw new EntidadeSemRetornoException("Nenhuma empresa registrada");
        List<UsuarioResponseDto> allResponse = new ArrayList<>();
        for (Usuario usuario : all) {
            allResponse.add(UsuarioMapper.toUsuarioDto(usuario));
        }
        return allResponse;
    }
}
