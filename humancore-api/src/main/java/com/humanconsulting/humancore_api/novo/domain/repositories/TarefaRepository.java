package com.humanconsulting.humancore_api.novo.domain.repositories;

import com.humanconsulting.humancore_api.novo.domain.entities.Tarefa;
import java.util.List;

public interface TarefaRepository {
    Tarefa save(Tarefa tarefa);
    Tarefa findById(Integer id);
    List<Tarefa> findByProjetoAndSprint(Integer idProjeto, Integer idSprint);
    List<Tarefa> findBySprint_IdSprint(Integer idSprint);
    boolean existsImpedimentoBySprint(Integer idSprint);
    boolean existsImpedimentoByProjeto(Integer idProjeto);
    void toggleImpedimento(Integer idTarefa);
}

