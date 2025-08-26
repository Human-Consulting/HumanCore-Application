package com.humanconsulting.humancore_api.novo.infrastructure.repositories.jpa;

import com.humanconsulting.humancore_api.novo.infrastructure.entities.CheckpointEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

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
