package com.humanconsulting.humancore_api.application.usecases.usuario;

import com.humanconsulting.humancore_api.application.usecases.usuario.mappers.UsuarioResponseMapper;
import com.humanconsulting.humancore_api.domain.entities.Usuario;
import com.humanconsulting.humancore_api.domain.exception.EntidadeConflitanteException;
import com.humanconsulting.humancore_api.domain.exception.EntidadeNaoEncontradaException;
import com.humanconsulting.humancore_api.domain.notifiers.EmailNotifier;
import com.humanconsulting.humancore_api.domain.notifiers.SalaNotifier;
import com.humanconsulting.humancore_api.domain.repositories.EmpresaRepository;
import com.humanconsulting.humancore_api.domain.repositories.UsuarioRepository;
import com.humanconsulting.humancore_api.infrastructure.utils.SenhaGenerator;
import com.humanconsulting.humancore_api.web.dtos.request.UsuarioRequestDto;
import com.humanconsulting.humancore_api.web.dtos.response.usuario.UsuarioResponseDto;
import com.humanconsulting.humancore_api.web.mappers.UsuarioMapper;
import org.springframework.security.crypto.password.PasswordEncoder;

public class CadastrarUsuarioUseCase {
    private final UsuarioRepository usuarioRepository;
    private final EmpresaRepository empresaRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailNotifier emailNotifier;
    private final SalaNotifier salaNotifier;
    private final UsuarioResponseMapper usuarioResponseMapper;

    public CadastrarUsuarioUseCase(
            UsuarioRepository usuarioRepository,
            EmpresaRepository empresaRepository,
            PasswordEncoder passwordEncoder,
            EmailNotifier emailNotifier,
            SalaNotifier salaNotifier,
            UsuarioResponseMapper usuarioResponseMapper
    ) {
        this.usuarioRepository = usuarioRepository;
        this.empresaRepository = empresaRepository;
        this.passwordEncoder = passwordEncoder;
        this.emailNotifier = emailNotifier;
        this.salaNotifier = salaNotifier;
        this.usuarioResponseMapper = usuarioResponseMapper;
    }

    public UsuarioResponseDto execute(UsuarioRequestDto novoUsuario) {
        if (usuarioRepository.findByEmail(novoUsuario.getEmail()).isEmpty()) {
            Usuario usuario = UsuarioMapper.toEntity(novoUsuario);
            usuario.setCores("#606080|#8d7dca|#4e5e8c|true");
            System.out.println("fkEmpresa: " + novoUsuario.getFkEmpresa());
            usuario.setEmpresa(
                    empresaRepository.findById(novoUsuario.getFkEmpresa())
                            .orElseThrow(() -> new EntidadeNaoEncontradaException("Empresa não encontrada"))
            );
            usuario.setSenha(SenhaGenerator.execute(usuario.getNome(), usuario.getEmpresa().getNome()));
            String senhaCriptografada = passwordEncoder.encode(usuario.getSenha());
            try {
                emailNotifier.cadastro(usuario);
            } catch (Exception exception) {
                throw new RuntimeException("Não foi possível cadastrar o usuário.");
            }
            usuario.setSenha(senhaCriptografada);
            Usuario usuarioCadastrado = usuarioRepository.save(usuario);
            salaNotifier.adicionarUsuarioEmSalaEmpresa(usuarioCadastrado);
            return usuarioResponseMapper.toResponse(usuarioCadastrado);
        }
        throw new EntidadeConflitanteException("Este email já foi registrado");
    }
}

