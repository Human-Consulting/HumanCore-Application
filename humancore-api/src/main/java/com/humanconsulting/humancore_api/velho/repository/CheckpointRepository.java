package com.humanconsulting.humancore_api.velho.repository;

import com.humanconsulting.humancore_api.velho.model.Checkpoint;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CheckpointRepository extends JpaRepository<Checkpoint, Integer> {
    //? Todos os projetos por TarefaEntity
    List<Checkpoint> findAllByTarefa_IdTarefa(Integer idTarefa);

    //? Todos os projetos por SprintEntity
    List<Checkpoint> findAllByTarefa_Sprint_IdSprint(Integer idSprint);

    //? Todos os projetos por ProjetoEntity
    List<Checkpoint> findAllByTarefa_Sprint_Projeto_IdProjeto(Integer idProjeto);

    //? Todos os projetos por EmpresaEntity
    List<Checkpoint> findAllByTarefa_Sprint_Projeto_Empresa_IdEmpresa(Integer idEmpresa);
}
