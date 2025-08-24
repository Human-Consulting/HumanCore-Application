package com.humanconsulting.humancore_api.velho.service;

import com.humanconsulting.humancore_api.velho.config.GerenciadorTokenJwt;
import com.humanconsulting.humancore_api.velho.controller.dto.atualizar.usuario.UsuarioAtualizarCoresDto;
import com.humanconsulting.humancore_api.velho.controller.dto.atualizar.usuario.UsuarioAtualizarDto;
import com.humanconsulting.humancore_api.velho.controller.dto.atualizar.usuario.UsuarioAtualizarSenhaDto;
import com.humanconsulting.humancore_api.velho.controller.dto.atualizar.usuario.UsuarioEsqueciASenhaDto;
import com.humanconsulting.humancore_api.velho.controller.dto.request.UsuarioEnviarCodigoRequestDto;
import com.humanconsulting.humancore_api.velho.controller.dto.response.tarefa.TarefaResponseDto;
import com.humanconsulting.humancore_api.velho.controller.dto.response.usuario.LoginResponseDto;
import com.humanconsulting.humancore_api.velho.controller.dto.response.usuario.UsuarioResponseDto;
import com.humanconsulting.humancore_api.velho.controller.dto.token.UsuarioTokenMapper;
import com.humanconsulting.humancore_api.velho.enums.PermissaoEnum;
import com.humanconsulting.humancore_api.velho.exception.AcessoNegadoException;
import com.humanconsulting.humancore_api.velho.exception.EntidadeConflitanteException;
import com.humanconsulting.humancore_api.velho.exception.EntidadeNaoEncontradaException;
import com.humanconsulting.humancore_api.velho.exception.EntidadeSemRetornoException;
import com.humanconsulting.humancore_api.velho.mapper.UsuarioMapper;
import com.humanconsulting.humancore_api.velho.model.Empresa;
import com.humanconsulting.humancore_api.velho.model.Tarefa;
import com.humanconsulting.humancore_api.velho.model.Usuario;
import com.humanconsulting.humancore_api.velho.observer.EmailNotifier;
import com.humanconsulting.humancore_api.velho.observer.SalaNotifier;
import com.humanconsulting.humancore_api.velho.repository.EmpresaRepository;
import com.humanconsulting.humancore_api.velho.repository.UsuarioRepository;
import com.humanconsulting.humancore_api.velho.utils.SenhaGenerator;
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

    @Autowired
    private EmailNotifier emailNotifier;

    @Autowired
    private SalaNotifier salaNotifier;

    public Usuario cadastrar(Usuario novoUsuario, Integer fkEmpresa) {
        if (usuarioRepository.findByEmail(novoUsuario.getEmail()).isEmpty()) {
            novoUsuario.setCores("#606080|#8d7dca|#4e5e8c|true");
            novoUsuario.setEmpresa(empresaRepository.findById(fkEmpresa).get());
            novoUsuario.setSenha(SenhaGenerator.gerarSenha(novoUsuario.getNome(), novoUsuario.getEmpresa().getNome()));
            String senhaCriptografada = passwordEncoder.encode(novoUsuario.getSenha());
            try {
                emailNotifier.cadastro(novoUsuario);

            } catch (Exception exception) {
                throw new RuntimeException("Não foi possível cadastrar o usuário.");
            }
            novoUsuario.setSenha(senhaCriptografada);

            Usuario usuarioCadastrado = usuarioRepository.save(novoUsuario);

            salaNotifier.adicionarUsuarioEmSalaEmpresa(usuarioCadastrado);

            return novoUsuario;
        }
        throw new EntidadeConflitanteException("Este email já foi registrado");
    }

    public LoginResponseDto buscarPorId(Integer id) {
        Optional<Usuario> optUsuario = usuarioRepository.findById(id);

        if (optUsuario.isEmpty()) throw new EntidadeNaoEncontradaException("Usuário não encontrado.");

        return passarParaLoginResponse(optUsuario.get(), null);
    }

    public Integer buscarPorEmail(String email) {
        Optional<Usuario> optUsuario = usuarioRepository.findByEmail(email);
        System.out.println(optUsuario.isEmpty());
        if (optUsuario.isEmpty()) throw new EntidadeNaoEncontradaException("Usuário não encontrado.");
        return optUsuario.get().getIdUsuario();
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
            throw new AcessoNegadoException("Apenas o dono da senha pode editá-la");
        }

        Usuario usuarioAtual = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Usuário não encontrado"));

        if (!passwordEncoder.matches(usuarioAtualizar.getSenhaAtual(), usuarioAtual.getSenha())) {
            throw new AcessoNegadoException("Senha atual errada");
        }

        if (passwordEncoder.matches(usuarioAtualizar.getSenhaAtualizada(), usuarioAtual.getSenha())) {
            throw new AcessoNegadoException("Nova senha deve ser diferente da atual");
        }

        usuarioAtual.setSenha(passwordEncoder.encode(usuarioAtualizar.getSenhaAtualizada()));
        Usuario usuarioAtualizado = usuarioRepository.save(usuarioAtual);
        return passarParaResponse(usuarioAtualizado);
    }

    public void enviarCodigo(UsuarioEnviarCodigoRequestDto usuarioEnviarCodigoRequestDto) {
        emailNotifier.codigo(usuarioEnviarCodigoRequestDto);
    }

    public Boolean esqueciASenha(Integer idUsuario, @Valid UsuarioEsqueciASenhaDto usuarioEsqueciASenhaDto) {
        Usuario usuarioAtual = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Usuário não encontrado"));

        usuarioAtual.setSenha(passwordEncoder.encode(usuarioEsqueciASenhaDto.getSenhaAtualizada()));
        usuarioRepository.save(usuarioAtual);
        return true;
    }

    public Boolean atualizarCoresPorId(Integer idUsuario, @Valid UsuarioAtualizarCoresDto usuarioAtualizarCoresDto) {
        Usuario usuarioAtual = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Usuário não encontrado"));

        usuarioAtual.setCores(usuarioAtualizarCoresDto.getCores());
        usuarioRepository.save(usuarioAtual);
        return true;
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
            if (novaTarefa.getProgresso() < 100)
                tarefasResponse.add(tarefaService.passarParaResponse(tarefasVinculada));
        }
        Integer qtdTarefas = tarefasResponse.size();
        return UsuarioMapper.toLoginDto(usuario, nomeEmpresa, qtdTarefas, comImpedimento, projetosVinculados, tarefasResponse, tokenUsuario);
    }
}
