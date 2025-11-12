package com.humanconsulting.humancore_api.domain.repositories;

import com.humanconsulting.humancore_api.domain.entities.Tarefa;
import java.util.List;
import java.util.Optional;

public interface TarefaRepository {
    Tarefa save(Tarefa tarefa);
    Optional<Tarefa> findById(Integer id);
    List<Tarefa> findAll();
    List<Tarefa> findByProjetoAndSprint(Integer idProjeto, Integer idSprint);
    List<Tarefa> findBySprint_IdSprint(Integer idSprint);
    boolean existsImpedimentoBySprint(Integer idSprint);
    boolean existsImpedimentoByProjeto(Integer idProjeto);
    void toggleImpedimento(Integer idTarefa);
    void deleteById(Integer id);
}

