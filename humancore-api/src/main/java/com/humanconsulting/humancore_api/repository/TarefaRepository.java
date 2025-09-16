package com.humanconsulting.humancore_api.repository;

import com.humanconsulting.humancore_api.model.Tarefa;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TarefaRepository extends JpaRepository<Tarefa, Integer> {
    @Query("SELECT t FROM Tarefa t WHERE t.sprint.projeto.idProjeto = :idProjeto AND t.sprint.idSprint = :idSprint ORDER BY t.dtFim")
    List<Tarefa> findByProjetoAndSprintOrderByDtFimAsc(@Param("idProjeto") Integer idProjeto, @Param("idSprint") Integer idSprint);

    List<Tarefa> findBySprint_IdSprintOrderByDtFimAsc(Integer idSprint);

//    @Query("SELECT COALESCE(AVG(t.progresso), 0) FROM Tarefa t WHERE t.sprint.idSprint = :idSprint")
//    Double mediaProgressoSprint(@Param("idSprint") Integer idSprint);

    @Query("SELECT CASE WHEN COUNT(t) > 0 THEN true ELSE false END FROM Tarefa t WHERE t.comImpedimento = true AND t.sprint.idSprint = :idSprint")
    boolean existsImpedimentoBySprint(@Param("idSprint") Integer idSprint);

    @Query("SELECT CASE WHEN COUNT(t) > 0 THEN true ELSE false END FROM Tarefa t WHERE t.comImpedimento = true AND t.sprint.projeto.idProjeto = :idProjeto")
    boolean existsImpedimentoByProjeto(@Param("idProjeto") Integer idProjeto);

    @Transactional
    @Modifying
    @Query("UPDATE Tarefa t SET t.comImpedimento = NOT t.comImpedimento WHERE t.idTarefa = :idTarefa")
    void toggleImpedimento(@Param("idTarefa") Integer idTarefa);
}
