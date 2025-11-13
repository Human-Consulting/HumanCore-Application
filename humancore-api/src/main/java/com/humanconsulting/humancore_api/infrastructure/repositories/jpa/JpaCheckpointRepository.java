package com.humanconsulting.humancore_api.infrastructure.repositories.jpa;

import com.humanconsulting.humancore_api.infrastructure.entities.CheckpointEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JpaCheckpointRepository extends JpaRepository<CheckpointEntity, Integer> {
    //? Todos os projetos por TarefaEntity
    List<CheckpointEntity> findAllByTarefa_IdTarefa(Integer idTarefa);

    //? Todos os projetos por SprintEntity
    List<CheckpointEntity> findAllByTarefa_Sprint_IdSprint(Integer idSprint);

    //? Todos os projetos por ProjetoEntity
    List<CheckpointEntity> findAllByTarefa_Sprint_Projeto_IdProjeto(Integer idProjeto);

    //? Todos os projetos por EmpresaEntity
    List<CheckpointEntity> findAllByTarefa_Sprint_Projeto_Empresa_IdEmpresa(Integer idEmpresa);
}
