package com.humanconsulting.humancore_api.infrastructure.repositories.adapters;

import com.humanconsulting.humancore_api.domain.entities.Checkpoint;
import com.humanconsulting.humancore_api.domain.repositories.CheckpointRepository;
import com.humanconsulting.humancore_api.infrastructure.entities.CheckpointEntity;
import com.humanconsulting.humancore_api.infrastructure.mappers.CheckpointMapper;
import com.humanconsulting.humancore_api.infrastructure.repositories.jpa.JpaCheckpointRepository;

import java.util.List;

public class CheckpointRepositoryAdapter implements CheckpointRepository {
    private final JpaCheckpointRepository jpaCheckpointRepository;

    public CheckpointRepositoryAdapter(JpaCheckpointRepository jpaCheckpointRepository) {
        this.jpaCheckpointRepository = jpaCheckpointRepository;
    }

    @Override
    public List<Checkpoint> findAllByTarefa_IdTarefa(Integer idTarefa) {
        return jpaCheckpointRepository.findAllByTarefa_IdTarefa(idTarefa).stream()
                .map(CheckpointMapper::toDomain)
                .toList();
    }

    @Override
    public List<Checkpoint> findAllByTarefa_Sprint_IdSprint(Integer idSprint) {
        return jpaCheckpointRepository.findAllByTarefa_Sprint_IdSprint(idSprint).stream()
                .map(CheckpointMapper::toDomain)
                .toList();
    }

    @Override
    public List<Checkpoint> findAllByTarefa_Sprint_Projeto_IdProjeto(Integer idProjeto) {
        return jpaCheckpointRepository.findAllByTarefa_Sprint_Projeto_IdProjeto(idProjeto).stream()
                .map(CheckpointMapper::toDomain)
                .toList();
    }

    @Override
    public List<Checkpoint> findAllByTarefa_Sprint_Projeto_Empresa_IdEmpresa(Integer idEmpresa) {
        return jpaCheckpointRepository.findAllByTarefa_Sprint_Projeto_Empresa_IdEmpresa(idEmpresa).stream()
                .map(CheckpointMapper::toDomain)
                .toList();
    }


    @Override
    public Checkpoint save(Checkpoint checkpoint) {
        CheckpointEntity entity = CheckpointMapper.toEntity(checkpoint);
        CheckpointEntity saved = jpaCheckpointRepository.save(entity);
        return CheckpointMapper.toDomain(saved);
    }

    @Override
    public void deleteById(Integer idCheckpoint) { jpaCheckpointRepository.deleteById(idCheckpoint);
    }
}
