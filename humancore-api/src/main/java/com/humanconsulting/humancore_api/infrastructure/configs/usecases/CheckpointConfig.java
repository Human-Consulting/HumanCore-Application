package com.humanconsulting.humancore_api.infrastructure.configs.usecases;

import com.humanconsulting.humancore_api.application.usecases.checkpoint.SincronizarCheckpointsDaTarefaUseCase;
import com.humanconsulting.humancore_api.domain.repositories.CheckpointRepository;
import com.humanconsulting.humancore_api.domain.repositories.TarefaRepository;
import com.humanconsulting.humancore_api.infrastructure.repositories.adapters.CheckpointRepositoryAdapter;
import com.humanconsulting.humancore_api.infrastructure.repositories.jpa.JpaCheckpointRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CheckpointConfig {
    @Bean
    public SincronizarCheckpointsDaTarefaUseCase sincronizarCheckpointsDaTarefaUseCase(CheckpointRepository checkpointRepository, TarefaRepository tarefaRepository) {
        return new SincronizarCheckpointsDaTarefaUseCase(checkpointRepository, tarefaRepository);
    }

    @Bean
    public CheckpointRepository checkpointRepository(JpaCheckpointRepository jpaCheckpointRepository) {
        return new CheckpointRepositoryAdapter(jpaCheckpointRepository);
    }
}

