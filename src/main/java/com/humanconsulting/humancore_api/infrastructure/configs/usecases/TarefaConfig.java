package com.humanconsulting.humancore_api.infrastructure.configs.usecases;

import com.humanconsulting.humancore_api.application.usecases.checkpoint.SincronizarCheckpointsDaTarefaUseCase;
import com.humanconsulting.humancore_api.application.usecases.projeto.BuscarProjetoPorIdUseCase;
import com.humanconsulting.humancore_api.application.usecases.sprint.BuscarSprintPorIdUseCase;
import com.humanconsulting.humancore_api.application.usecases.tarefa.*;
import com.humanconsulting.humancore_api.application.usecases.usuario.mappers.UsuarioResponseMapper;
import com.humanconsulting.humancore_api.infrastructure.configs.RabbitTemplateConfiguration;
import com.humanconsulting.humancore_api.infrastructure.mappers.EmailUpdateMapper;
import com.humanconsulting.humancore_api.domain.notifiers.SalaNotifier;
import com.humanconsulting.humancore_api.domain.repositories.CheckpointRepository;
import com.humanconsulting.humancore_api.domain.repositories.TarefaRepository;
import com.humanconsulting.humancore_api.domain.repositories.SprintRepository;
import com.humanconsulting.humancore_api.application.usecases.tarefa.mappers.TarefaResponseMapper;
import com.humanconsulting.humancore_api.domain.repositories.UsuarioRepository;
import com.humanconsulting.humancore_api.infrastructure.repositories.adapters.TarefaRepositoryAdapter;
import com.humanconsulting.humancore_api.infrastructure.repositories.jpa.JpaTarefaRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TarefaConfig {
    @Bean
    public AtualizarImpedimentoTarefaUseCase atualizarImpedimentoTarefaUseCase(
            TarefaRepository tarefaRepository,
            UsuarioRepository usuarioRepository,
            RabbitTemplateConfiguration rabbitMQ,
            BuscarProjetoPorIdUseCase buscarProjetoPorIdUseCase,
            BuscarSprintPorIdUseCase buscarSprintPorIdUseCase,
            UsuarioResponseMapper usuarioMapper,
            TarefaResponseMapper tarefaResponseMapper,
            EmailUpdateMapper emailUpdateMapper
    ) {
        return new AtualizarImpedimentoTarefaUseCase(
                tarefaRepository,
                usuarioRepository,
                rabbitMQ,
                buscarProjetoPorIdUseCase,
                buscarSprintPorIdUseCase,
                usuarioMapper,
                tarefaResponseMapper,
                emailUpdateMapper
        );
    }

    @Bean
    public AtualizarTarefaUseCase atualizarTarefaUseCase(
            TarefaRepository tarefaRepository,
            UsuarioRepository usuarioRepository,
            CheckpointRepository checkpointRepository,
            SalaNotifier salaNotifier,
            TarefaResponseMapper tarefaResponseMapper,
            SincronizarCheckpointsDaTarefaUseCase sincronizarCheckpointsDaTarefa
    ) {
        return new AtualizarTarefaUseCase(
                tarefaRepository,
                usuarioRepository,
                checkpointRepository,
                salaNotifier,
                tarefaResponseMapper,
                sincronizarCheckpointsDaTarefa
        );
    }

    @Bean
    public BuscarTarefaPorIdUseCase buscarTarefaPorIdUseCase(TarefaRepository tarefaRepository) {
        return new BuscarTarefaPorIdUseCase(tarefaRepository);
    }

    @Bean
    public CadastrarTarefaUseCase cadastrarTarefaUseCase(
            TarefaRepository tarefaRepository,
            SprintRepository sprintRepository,
            UsuarioRepository usuarioRepository,
            SalaNotifier salaNotifier,
            TarefaResponseMapper tarefaResponseMapper,
            SincronizarCheckpointsDaTarefaUseCase sincronizarCheckpointsDaTarefaUseCase
    ) {
        return new CadastrarTarefaUseCase(
                tarefaRepository,
                sprintRepository,
                usuarioRepository,
                salaNotifier,
                tarefaResponseMapper,
                sincronizarCheckpointsDaTarefaUseCase
        );
    }

    @Bean
    public DeletarTarefaUseCase deletarTarefaUseCase(TarefaRepository tarefaRepository) {
        return new DeletarTarefaUseCase(tarefaRepository);
    }

    @Bean
    public ListarTarefasPorSprintUseCase listarTarefasPorSprintUseCase(TarefaRepository tarefaRepository, TarefaResponseMapper tarefaResponseMapper) {
        return new ListarTarefasPorSprintUseCase(tarefaRepository, tarefaResponseMapper);
    }

    @Bean
    public ListarTarefasUseCase listarTarefasUseCase(TarefaRepository tarefaRepository, TarefaResponseMapper tarefaResponseMapper) {
        return new ListarTarefasUseCase(tarefaRepository, tarefaResponseMapper);
    }

    @Bean
    public TarefaResponseMapper tarefaResponseMapper(CheckpointRepository checkpointRepository) {
        return new TarefaResponseMapper(checkpointRepository);
    }

    @Bean
    public TarefaRepository tarefaRepository(JpaTarefaRepository jpaTarefaRepository) {
        return new TarefaRepositoryAdapter(jpaTarefaRepository);
    }

    @Bean
    public EmailUpdateMapper emailUpdateMapper() {
        return new EmailUpdateMapper();
    }
}

