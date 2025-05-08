package com.humanconsulting.humancore_api.service;

import com.humanconsulting.humancore_api.config.GerenciadorTokenJwt;
import com.humanconsulting.humancore_api.controller.dto.atualizar.usuario.UsuarioAtualizarDto;
import com.humanconsulting.humancore_api.controller.dto.request.LoginRequestDto;
import com.humanconsulting.humancore_api.controller.dto.request.UsuarioRequestDto;
import com.humanconsulting.humancore_api.controller.dto.response.TarefaResponseDto;
import com.humanconsulting.humancore_api.controller.dto.response.usuario.LoginResponseDto;
import com.humanconsulting.humancore_api.controller.dto.response.usuario.UsuarioResponseDto;
import com.humanconsulting.humancore_api.controller.dto.token.UsuarioTokenDto;
import com.humanconsulting.humancore_api.controller.dto.token.UsuarioTokenMapper;
import com.humanconsulting.humancore_api.enums.PermissaoEnum;
import com.humanconsulting.humancore_api.exception.AcessoNegadoException;
import com.humanconsulting.humancore_api.exception.EntidadeConflitanteException;
import com.humanconsulting.humancore_api.exception.EntidadeNaoEncontradaException;
import com.humanconsulting.humancore_api.exception.EntidadeSemRetornoException;
import com.humanconsulting.humancore_api.mapper.UsuarioMapper;
import com.humanconsulting.humancore_api.model.Tarefa;
import com.humanconsulting.humancore_api.model.Usuario;
import com.humanconsulting.humancore_api.repository.EmpresaRepository;
import com.humanconsulting.humancore_api.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired private UsuarioRepository usuarioRepository;

    @Autowired private EmpresaRepository empresaRepository;

    @Autowired private TarefaService tarefaService;

    @Autowired private PasswordEncoder passwordEncoder;

    @Autowired private GerenciadorTokenJwt gerenciadorTokenJwt;

    @Autowired private AuthenticationManager authenticationManager;

    public Usuario cadastrar(Usuario novoUsuario) {
        String senhaCriptografada = passwordEncoder.encode(novoUsuario.getSenha());
        novoUsuario.setSenha(senhaCriptografada);

        this.usuarioRepository.save(novoUsuario);

        return novoUsuario;
    }

    public LoginResponseDto buscarPorId(Integer id) {
        Optional<Usuario> optUsuario = usuarioRepository.findById(id);

        if (optUsuario.isEmpty()) throw new EntidadeNaoEncontradaException("Usuário não encontrado.");

        Usuario usuario = optUsuario.get();
        return passarParaLoginResponse(usuario, null);
    }

    public List<UsuarioResponseDto> listar() {
        List<Usuario> all = usuarioRepository.findAll();
        if (all.isEmpty()) throw new EntidadeSemRetornoException("Nenhuma empresa registrada");
        List<UsuarioResponseDto> allResponse = new ArrayList<>();
        for (Usuario usuario : all) {
            allResponse.add(passarParaResponse(usuario));
        }
        return allResponse;
    }

    public void deletar(Integer id) {
        Optional<Usuario> usuario = usuarioRepository.findById(id);

        if (usuario.isEmpty()) throw new EntidadeNaoEncontradaException("Usuário não encontrado.");

        usuarioRepository.deleteById(id);
    }

    public UsuarioResponseDto atualizarPorId(Integer idUsuario, UsuarioAtualizarDto usuarioAtualizar) {
        Optional<Usuario> optUsuarioAlvo = usuarioRepository.findById(idUsuario);
        Usuario usuarioAlvo = optUsuarioAlvo.get();

        Optional<Usuario> optUsuarioEditor = usuarioRepository.findById(usuarioAtualizar.getIdEditor());
        Usuario usuarioEditor = optUsuarioEditor.get();

        if (optUsuarioAlvo.isEmpty() || optUsuarioEditor.isEmpty()) throw new EntidadeNaoEncontradaException("Usuário não encontrado.");

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

        Usuario usuarioAtualizado = usuarioRepository.save(usuarioEditor);
        return passarParaResponse(usuarioAtualizado);
    }

    public LoginResponseDto autenticar(Usuario usuario) {
        final UsernamePasswordAuthenticationToken credentials = new UsernamePasswordAuthenticationToken(usuario.getEmail(), usuario.getSenha());
        final Authentication authentication = this.authenticationManager.authenticate(credentials);

        Optional<Usuario> optUsuarioAutenticado = usuarioRepository.findByEmail(usuario.getEmail());
        Usuario usuarioAutenticado = optUsuarioAutenticado.get();

        SecurityContextHolder.getContext().setAuthentication(authentication);
        final String token = gerenciadorTokenJwt.generateToken(authentication);
        String tokenUsuario = UsuarioTokenMapper.of(usuarioAutenticado, token).getToken();
        return passarParaLoginResponse(usuarioAutenticado, tokenUsuario);
    }

    public List<UsuarioResponseDto> listarPorEmpresa(Integer idEmpresa) {
        List<Usuario> all = usuarioRepository.findByFkEmpresa(idEmpresa);
        if (all.isEmpty()) throw new EntidadeSemRetornoException("Nenhuma empresa registrada");
        List<UsuarioResponseDto> allResponse = new ArrayList<>();
        for (Usuario usuario : all) {
            allResponse.add(passarParaResponse(usuario));
        }
        return allResponse;
    }

    public UsuarioResponseDto passarParaResponse(Usuario usuario) {
        Integer qtdTarefas = usuarioRepository.countTarefasByUsuario(usuario.getIdUsuario());
        Boolean comImpedimento = usuarioRepository.hasTarefaComImpedimento(usuario.getIdUsuario());
        return UsuarioMapper.toUsuarioDto(usuario, qtdTarefas, comImpedimento);
    }

    public LoginResponseDto passarParaLoginResponse(Usuario usuario, String tokenUsuario) {
        String nomeEmpresa = usuario.getEmpresa().getNome();
        Integer qtdTarefas = usuarioRepository.countTarefasByUsuario(usuario.getIdUsuario());
        Boolean comImpedimento = usuarioRepository.hasTarefaComImpedimento(usuario.getIdUsuario());
        List<Integer> projetosVinculados = usuarioRepository.findProjetosVinculados(usuario.getIdUsuario());
        List<Tarefa> tarefasVinculadas = usuarioRepository.findTarefasVinculadas(usuario.getIdUsuario());
        List<TarefaResponseDto> tarefasResponse = new ArrayList<>();
        for (Tarefa tarefasVinculada : tarefasVinculadas) {
            tarefasResponse.add(tarefaService.passarParaResponse(tarefasVinculada));
        }
        return UsuarioMapper.toLoginDto(usuario, nomeEmpresa, qtdTarefas, comImpedimento, projetosVinculados, tarefasResponse, tokenUsuario);
    }
}
