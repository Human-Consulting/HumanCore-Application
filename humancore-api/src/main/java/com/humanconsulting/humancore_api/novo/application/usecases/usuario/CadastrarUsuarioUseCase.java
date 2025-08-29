package com.humanconsulting.humancore_api.novo.application.usecases.usuario;

import com.humanconsulting.humancore_api.novo.domain.entities.Usuario;
import com.humanconsulting.humancore_api.novo.domain.repositories.EmpresaRepository;
import com.humanconsulting.humancore_api.novo.domain.repositories.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;

public class CadastrarUsuarioUseCase {
    private final UsuarioRepository usuarioRepository;
    private final EmpresaRepository empresaRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailNotifier emailNotifier;
    private final SalaNotifier salaNotifier;

    public CadastrarUsuarioUseCase(
            UsuarioRepository usuarioRepository,
            EmpresaRepository empresaRepository,
            PasswordEncoder passwordEncoder,
            EmailNotifier emailNotifier,
            SalaNotifier salaNotifier
    ) {
        this.usuarioRepository = usuarioRepository;
        this.empresaRepository = empresaRepository;
        this.passwordEncoder = passwordEncoder;
        this.emailNotifier = emailNotifier;
        this.salaNotifier = salaNotifier;
    }

    public Usuario execute(Usuario novoUsuario, Integer fkEmpresa) {
        if (usuarioRepository.findByEmail(novoUsuario.getEmail()).isEmpty()) {
            novoUsuario.setCores("#606080|#8d7dca|#4e5e8c|true");
            novoUsuario.setEmpresa(empresaRepository.findById(fkEmpresa));
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
}

