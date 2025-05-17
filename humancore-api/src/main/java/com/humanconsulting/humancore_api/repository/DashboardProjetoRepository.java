package com.humanconsulting.humancore_api.repository;

import com.humanconsulting.humancore_api.model.Investimento;
import com.humanconsulting.humancore_api.model.Projeto;
import com.humanconsulting.humancore_api.model.Tarefa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DashboardProjetoRepository extends JpaRepository<Tarefa, Integer> {

    @Query("""
        SELECT u.area, COUNT(t)
        FROM Tarefa t
        JOIN t.responsavel u
        JOIN t.sprint s
        WHERE s.projeto.idProjeto = :idProjeto
        GROUP BY u.area
        ORDER BY COUNT(t) DESC
        """)
    List<Object[]> buscarTarefasPorArea(@Param("idProjeto") Integer idProjeto);

//    @Query("""
//        SELECT ROUND(AVG(t.progresso), 2)
//        FROM Tarefa t
//        JOIN t.sprint s
//        WHERE s.projeto.idProjeto = :idProjeto
//        """)
//    Double mediaProgresso(@Param("idProjeto") Integer idProjeto);

    @Query("""
        SELECT p.orcamento
        FROM Projeto p
        WHERE p.idProjeto = :idProjeto
        """)
    Double orcamentoTotal(@Param("idProjeto") Integer idProjeto);

    @Query("""
        SELECT COUNT(s)
        FROM Sprint s
        WHERE s.projeto.idProjeto = :idProjeto
        """)
    Integer totalSprints(@Param("idProjeto") Integer idProjeto);

    @Query("""
        SELECT CASE WHEN COUNT(t) > 0 THEN true ELSE false END
        FROM Tarefa t
        JOIN t.sprint s
        WHERE s.projeto.idProjeto = :idProjeto AND t.comImpedimento = true
        """)
    boolean projetoComImpedimento(@Param("idProjeto") Integer idProjeto);

    @Query("""
        SELECT i
        FROM Investimento i
        WHERE i.projeto.idProjeto = :idProjeto
        ORDER BY i.dtInvestimento ASC
        """)
    List<Investimento> listarFinanceiroPorEmpresa(@Param("idProjeto") Integer idProjeto);
}
