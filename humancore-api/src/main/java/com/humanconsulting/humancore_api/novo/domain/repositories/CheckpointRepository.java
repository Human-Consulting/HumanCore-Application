package com.humanconsulting.humancore_api.novo.domain.repositories;

import com.humanconsulting.humancore_api.novo.domain.entities.Checkpoint;
import java.util.List;

public interface CheckpointRepository {
    List<Checkpoint> findAllByTarefa_IdTarefa(Integer idTarefa);
    List<Checkpoint> findAllByTarefa_Sprint_IdSprint(Integer idSprint);
    List<Checkpoint> findAllByTarefa_Sprint_Projeto_IdProjeto(Integer idProjeto);
    List<Checkpoint> findAllByTarefa_Sprint_Projeto_Empresa_IdEmpresa(Integer idEmpresa);
    Checkpoint save(Checkpoint checkpoint);
    Checkpoint delete(Checkpoint checkpoint);
}

