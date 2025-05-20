package com.humanconsulting.humancore_api.service;

import com.humanconsulting.humancore_api.config.GerenciadorTokenJwt;
import com.humanconsulting.humancore_api.controller.dto.atualizar.usuario.UsuarioAtualizarDto;
import com.humanconsulting.humancore_api.controller.dto.atualizar.usuario.UsuarioAtualizarSenhaDto;
import com.humanconsulting.humancore_api.controller.dto.response.tarefa.TarefaResponseDto;
import com.humanconsulting.humancore_api.controller.dto.response.usuario.LoginResponseDto;
import com.humanconsulting.humancore_api.controller.dto.response.usuario.UsuarioResponseDto;
import com.humanconsulting.humancore_api.controller.dto.token.UsuarioTokenMapper;
import com.humanconsulting.humancore_api.enums.PermissaoEnum;
import com.humanconsulting.humancore_api.exception.AcessoNegadoException;
import com.humanconsulting.humancore_api.exception.EntidadeNaoEncontradaException;
import com.humanconsulting.humancore_api.exception.EntidadeSemRetornoException;
import com.humanconsulting.humancore_api.mapper.UsuarioMapper;
import com.humanconsulting.humancore_api.model.Empresa;
import com.humanconsulting.humancore_api.model.Tarefa;
import com.humanconsulting.humancore_api.model.Usuario;
import com.humanconsulting.humancore_api.repository.EmpresaRepository;
import com.humanconsulting.humancore_api.repository.UsuarioRepository;
import jakarta.validation.Valid;
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

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private EmpresaRepository empresaRepository;

    @Autowired
    private TarefaService tarefaService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private GerenciadorTokenJwt gerenciadorTokenJwt;

    @Autowired
    private AuthenticationManager authenticationManager;

    public Usuario cadastrar(Usuario novoUsuario, Integer fkEmpresa) {
        String senhaCriptografada = passwordEncoder.encode(novoUsuario.getSenha());
        novoUsuario.setSenha(senhaCriptografada);
        novoUsuario.setEmpresa(empresaRepository.findById(fkEmpresa).get());

        this.usuarioRepository.save(novoUsuario);

        return novoUsuario;
    }

    public LoginResponseDto buscarPorId(Integer id) {
        Optional<Usuario> optUsuario = usuarioRepository.findById(id);

        if (optUsuario.isEmpty()) throw new EntidadeNaoEncontradaException("Usuário não encontrado.");

        return passarParaLoginResponse(optUsuario.get(), null);
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

        if (optUsuarioAlvo.isEmpty() || optUsuarioEditor.isEmpty())
            throw new EntidadeNaoEncontradaException("Usuário não encontrado.");

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
        Empresa empresa = usuarioRepository.findById(idUsuario).get().getEmpresa();
        Usuario usuarioAtualizado = usuarioRepository.save(UsuarioMapper.toEntity(usuarioAtualizar, idUsuario, empresa, usuarioAlvo.getSenha()));
        return passarParaResponse(usuarioAtualizado);
    }

    public UsuarioResponseDto atualizarSenhaPorId(Integer idUsuario, @Valid UsuarioAtualizarSenhaDto usuarioAtualizar) {
        if (!usuarioAtualizar.getIdEditor().equals(idUsuario)) {
            System.out.println("Apenas o dono da senha pode editá-la");
            throw new AcessoNegadoException("Apenas o dono da senha pode editá-la");
        }

        Usuario usuarioAtual = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Usuário não encontrado"));

        if (!passwordEncoder.matches(usuarioAtualizar.getSenhaAtual(), usuarioAtual.getSenha())) {
            System.out.println("Senha atual errada");
            throw new AcessoNegadoException("Senha atual errada");
        }

        if (passwordEncoder.matches(usuarioAtualizar.getSenhaAtualizada(), usuarioAtual.getSenha())) {
            System.out.println("Nova senha deve ser diferente da atual");
            throw new AcessoNegadoException("Nova senha deve ser diferente da atual");
        }

        usuarioAtual.setSenha(passwordEncoder.encode(usuarioAtualizar.getSenhaAtualizada()));
        Usuario usuarioAtualizado = usuarioRepository.save(usuarioAtual);
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
        Boolean comImpedimento = usuarioRepository.hasTarefaComImpedimento(usuario.getIdUsuario());
        List<Integer> projetosVinculados = usuarioRepository.findProjetosVinculados(usuario.getIdUsuario());
        List<Tarefa> tarefasVinculadas = usuarioRepository.findTarefasVinculadas(usuario.getIdUsuario());
        List<TarefaResponseDto> tarefasResponse = new ArrayList<>();
        for (Tarefa tarefasVinculada : tarefasVinculadas) {
            TarefaResponseDto novaTarefa = tarefaService.passarParaResponse(tarefasVinculada);
            if (novaTarefa.getProgresso() < 100) tarefasResponse.add(tarefaService.passarParaResponse(tarefasVinculada));
        }
        Integer qtdTarefas = tarefasResponse.size();
        return UsuarioMapper.toLoginDto(usuario, nomeEmpresa, qtdTarefas, comImpedimento, projetosVinculados, tarefasResponse, tokenUsuario);
    }
}
