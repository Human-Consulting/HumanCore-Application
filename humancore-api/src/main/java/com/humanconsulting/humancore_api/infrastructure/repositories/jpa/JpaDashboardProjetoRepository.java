package com.humanconsulting.humancore_api.infrastructure.repositories.jpa;

import com.humanconsulting.humancore_api.infrastructure.entities.InvestimentoEntity;
import com.humanconsulting.humancore_api.infrastructure.entities.ProjetoEntity;
import com.humanconsulting.humancore_api.domain.entities.Investimento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JpaDashboardProjetoRepository extends JpaRepository<ProjetoEntity, Integer> {
    @Query("""
        SELECT u.area, COUNT(t)
        FROM TarefaEntity t
        JOIN t.responsavel u
        JOIN t.sprint s
        JOIN s.projeto p
        WHERE p.idProjeto = :idProjeto
        GROUP BY u.area
        ORDER BY COUNT(t) DESC
        """)
    List<Object[]> buscarTarefasPorArea(@Param("idProjeto") Integer idProjeto);

    @Query("""
        SELECT SUM(p.orcamento)
        FROM ProjetoEntity p
        WHERE p.idProjeto = :idProjeto
        """)
    Double orcamentoTotal(@Param("idProjeto") Integer idProjeto);

//    @Query("""
//        SELECT ROUND(AVG(t.progresso), 2)
//        FROM TarefaEntity t
//        JOIN t.sprint s
//        WHERE s.projeto.idProjeto = :idProjeto
//        """)
//    Double mediaProgresso(@Param("idProjeto") Integer idProjeto);

    @Query("""
        SELECT COUNT(s)
        FROM SprintEntity s
        WHERE s.projeto.idProjeto = :idProjeto
        """)
    Integer totalSprints(@Param("idProjeto") Integer idProjeto);

    @Query("""
        SELECT CASE WHEN COUNT(t) > 0 THEN true ELSE false END
        FROM TarefaEntity t
        JOIN t.sprint s
        WHERE s.projeto.idProjeto = :idProjeto AND t.comImpedimento = true
        """)
    boolean projetoComImpedimento(@Param("idProjeto") Integer idProjeto);

    @Query("""
        SELECT i
        FROM InvestimentoEntity i
        WHERE i.projeto.idProjeto = :idProjeto
        ORDER BY i.dtInvestimento ASC
        """)
    List<InvestimentoEntity> listarFinanceiroPorEmpresa(@Param("idProjeto") Integer idProjeto);
}

