package com.humanconsulting.humancore_api.infrastructure.configs.usecases;

import com.humanconsulting.humancore_api.application.usecases.tarefa.mappers.TarefaResponseMapper;
import com.humanconsulting.humancore_api.application.usecases.usuario.*;
import com.humanconsulting.humancore_api.application.usecases.usuario.mappers.UsuarioLoginResponseMapper;
import com.humanconsulting.humancore_api.domain.notifiers.SalaNotifier;
import com.humanconsulting.humancore_api.domain.repositories.UsuarioRepository;
import com.humanconsulting.humancore_api.domain.repositories.EmpresaRepository;
import com.humanconsulting.humancore_api.infrastructure.configs.RabbitTemplateConfiguration;
import com.humanconsulting.humancore_api.infrastructure.mappers.EmailCadastroMapper;
import com.humanconsulting.humancore_api.application.usecases.usuario.mappers.UsuarioResponseMapper;
import com.humanconsulting.humancore_api.infrastructure.configs.GerenciadorTokenJwt;
import com.humanconsulting.humancore_api.infrastructure.repositories.adapters.UsuarioRepositoryAdapter;
import com.humanconsulting.humancore_api.infrastructure.repositories.jpa.JpaUsuarioRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class UsuarioConfig {
    @Bean
    public CadastrarUsuarioUseCase cadastrarUsuarioUseCase(
            UsuarioRepository usuarioRepository,
            EmpresaRepository empresaRepository,
            PasswordEncoder passwordEncoder,
            RabbitTemplateConfiguration rabbitMQ,
            SalaNotifier salaNotifier,
            UsuarioResponseMapper usuarioResponseMapper,
            EmailCadastroMapper emailCadastroMapper
    ) {
        return new CadastrarUsuarioUseCase(
                usuarioRepository,
                empresaRepository,
                passwordEncoder,
                rabbitMQ,
                salaNotifier,
                usuarioResponseMapper,
                emailCadastroMapper
        );
    }

    @Bean
    public AtualizarCoresPorIdUseCase atualizarCoresPorIdUseCase(UsuarioRepository usuarioRepository) {
        return new AtualizarCoresPorIdUseCase(usuarioRepository);
    }

    @Bean
    public AtualizarSenhaUseCase atualizarSenhaUseCase(
            UsuarioRepository usuarioRepository,
            PasswordEncoder passwordEncoder,
            UsuarioResponseMapper usuarioResponseMapper
    ) {
        return new AtualizarSenhaUseCase(
                usuarioRepository,
                passwordEncoder,
                usuarioResponseMapper
        );
    }

    @Bean
    public AtualizarUsuarioUseCase atualizarUsuarioUseCase(UsuarioRepository usuarioRepository, UsuarioResponseMapper usuarioResponseMapper) {
        return new AtualizarUsuarioUseCase(usuarioRepository, usuarioResponseMapper);
    }

    @Bean
    public AutenticarUsuarioUseCase autenticarUsuarioUseCase(
            UsuarioRepository usuarioRepository,
            AuthenticationManager authenticationManager,
            GerenciadorTokenJwt gerenciadorTokenJwt,
            UsuarioLoginResponseMapper usuarioLoginResponseMapper
    ) {
        return new AutenticarUsuarioUseCase(
                usuarioRepository,
                authenticationManager,
                gerenciadorTokenJwt,
                usuarioLoginResponseMapper
        );
    }

    @Bean
    public BuscarUsuarioPorEmailUseCase buscarUsuarioPorEmailUseCase(UsuarioRepository usuarioRepository) {
        return new BuscarUsuarioPorEmailUseCase(usuarioRepository);
    }

    @Bean
    public BuscarUsuarioPorIdUseCase buscarUsuarioPorIdUseCase(UsuarioRepository usuarioRepository, UsuarioLoginResponseMapper usuarioLoginResponseMapper) {
        return new BuscarUsuarioPorIdUseCase(usuarioRepository, usuarioLoginResponseMapper);
    }

    @Bean
    public DeletarUsuarioUseCase deletarUsuarioUseCase(UsuarioRepository usuarioRepository) {
        return new DeletarUsuarioUseCase(usuarioRepository);
    }

    @Bean
    public EnviarCodigoUseCase enviarCodigoUseCase(RabbitTemplateConfiguration rabbitMQ) {
        return new EnviarCodigoUseCase(rabbitMQ);
    }

    @Bean
    public ListarUsuariosPorEmpresaUseCase listarUsuariosPorEmpresaUseCase(UsuarioRepository usuarioRepository, UsuarioResponseMapper usuarioResponseMapper) {
        return new ListarUsuariosPorEmpresaUseCase(usuarioRepository, usuarioResponseMapper);
    }

    @Bean
    public ListarUsuariosUseCase listarUsuariosUseCase(UsuarioRepository usuarioRepository, UsuarioResponseMapper usuarioResponseMapper) {
        return new ListarUsuariosUseCase(usuarioRepository, usuarioResponseMapper);
    }

    @Bean
    public UsuarioResponseMapper usuarioResponseMapper(UsuarioRepository usuarioRepository) {
        return new UsuarioResponseMapper(usuarioRepository);
    }

    @Bean
    public UsuarioLoginResponseMapper usuarioLoginResponseMapper(UsuarioRepository usuarioRepository, TarefaResponseMapper tarefaResponseMapper) {
        return new UsuarioLoginResponseMapper(usuarioRepository, tarefaResponseMapper);
    }

    @Bean
    public UsuarioRepository usuarioRepository(JpaUsuarioRepository jpaUsuarioRepository) {
        return new UsuarioRepositoryAdapter(jpaUsuarioRepository);
    }

    @Bean
    public EmailCadastroMapper emailCadastroMapper() {
        return new EmailCadastroMapper();
    }
}
