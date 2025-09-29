package com.humanconsulting.humancore_api.infrastructure.repositories.jpa;

import com.humanconsulting.humancore_api.infrastructure.entities.TarefaEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JpaTarefaRepository extends JpaRepository<TarefaEntity, Integer> {
    @Query("SELECT t FROM TarefaEntity t WHERE t.sprint.projeto.idProjeto = :idProjeto AND t.sprint.idSprint = :idSprint")
    List<TarefaEntity> findByProjetoAndSprint(@Param("idProjeto") Integer idProjeto, @Param("idSprint") Integer idSprint);

    List<TarefaEntity> findBySprint_IdSprint(Integer idSprint);

//    @Query("SELECT COALESCE(AVG(t.progresso), 0) FROM TarefaEntity t WHERE t.sprint.idSprint = :idSprint")
//    Double mediaProgressoSprint(@Param("idSprint") Integer idSprint);

    @Query("SELECT CASE WHEN COUNT(t) > 0 THEN true ELSE false END FROM TarefaEntity t WHERE t.comImpedimento = true AND t.sprint.idSprint = :idSprint")
    boolean existsImpedimentoBySprint(@Param("idSprint") Integer idSprint);

    @Query("SELECT CASE WHEN COUNT(t) > 0 THEN true ELSE false END FROM TarefaEntity t WHERE t.comImpedimento = true AND t.sprint.projeto.idProjeto = :idProjeto")
    boolean existsImpedimentoByProjeto(@Param("idProjeto") Integer idProjeto);

    @Transactional
    @Modifying
    @Query("UPDATE TarefaEntity t SET t.comImpedimento = NOT t.comImpedimento WHERE t.idTarefa = :idTarefa")
    void toggleImpedimento(@Param("idTarefa") Integer idTarefa);
}
