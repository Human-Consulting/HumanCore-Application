package com.humanconsulting.humancore_api.infrastructure.configs.usecases;

import com.humanconsulting.humancore_api.application.usecases.sprint.*;
import com.humanconsulting.humancore_api.application.usecases.sprint.mappers.SprintResponseMapper;
import com.humanconsulting.humancore_api.application.usecases.tarefa.mappers.TarefaResponseMapper;
import com.humanconsulting.humancore_api.application.usecases.usuario.mappers.UsuarioResponseMapper;
import com.humanconsulting.humancore_api.domain.repositories.*;
import com.humanconsulting.humancore_api.infrastructure.repositories.adapters.SprintRepositoryAdapter;
import com.humanconsulting.humancore_api.infrastructure.repositories.jpa.JpaSprintRepository;
import com.humanconsulting.humancore_api.web.mappers.UsuarioMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SprintConfig {
    @Bean
    public AtualizarSprintUseCase atualizarSprintUseCase(
            SprintRepository sprintRepository,
            UsuarioRepository usuarioRepository,
            SprintResponseMapper sprintResponseMapper
    ) {
        return new AtualizarSprintUseCase(
                sprintRepository,
                usuarioRepository,
                sprintResponseMapper
        );
    }

    @Bean
    public BuscarSprintPorIdUseCase buscarSprintPorIdUseCase(
            SprintRepository sprintRepository,
            TarefaRepository tarefaRepository,
            CheckpointRepository checkpointRepository,
            SprintResponseMapper sprintResponseMapper
    ) {
        return new BuscarSprintPorIdUseCase(
                sprintRepository,
                tarefaRepository,
                checkpointRepository,
                sprintResponseMapper
        );
    }

    @Bean
    public BuscarSprintsPorProjetoUseCase buscarSprintsPorProjetoUseCase(SprintRepository sprintRepository, SprintResponseMapper sprintResponseMapper) {
        return new BuscarSprintsPorProjetoUseCase(sprintRepository, sprintResponseMapper);
    }

    @Bean
    public CadastrarSprintUseCase cadastrarSprintUseCase(
            ProjetoRepository projetoRepository,
            SprintRepository sprintRepository,
            SprintResponseMapper sprintResponseMapper
    ) {
        return new CadastrarSprintUseCase(projetoRepository, sprintRepository, sprintResponseMapper);
    }

    @Bean
    public DeletarSprintUseCase deletarSprintUseCase(SprintRepository sprintRepository) {
        return new DeletarSprintUseCase(sprintRepository);
    }

    @Bean
    public SprintResponseMapper sprintResponseMapper(
            TarefaRepository tarefaRepository,
            CheckpointRepository checkpointRepository,
            TarefaResponseMapper tarefaResponseMapper,
            UsuarioMapper usuarioMapper
    ) {
        return new SprintResponseMapper(
                tarefaRepository,
                checkpointRepository,
                tarefaResponseMapper,
                usuarioMapper
        );
    }

    @Bean
    public UsuarioMapper usuarioMapper() {
        return new UsuarioMapper();
    }

    @Bean
    public UsuarioResponseMapper usuarioResponseMapper(
            UsuarioRepository usuarioRepository,
            TarefaResponseMapper tarefaResponseMapper
    ) {
        return new UsuarioResponseMapper(
                usuarioRepository,
                tarefaResponseMapper
        );
    }

    @Bean
    public SprintRepository sprintRepository(JpaSprintRepository jpaSprintRepository) {
        return new SprintRepositoryAdapter(jpaSprintRepository);
    }
}

