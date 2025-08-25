package com.humanconsulting.humancore_api.velho.repository;

import com.humanconsulting.humancore_api.velho.model.Investimento;
import com.humanconsulting.humancore_api.velho.model.Projeto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DashboardEmpresaRepository extends JpaRepository<Projeto, Integer> {

    @Query("""
        SELECT u.area, COUNT(t)
        FROM Tarefa t
        JOIN t.responsavel u
        JOIN t.sprint s
        JOIN s.projeto p
        WHERE u.empresa.idEmpresa = :idEmpresa
        GROUP BY u.area
        ORDER BY COUNT(t) DESC
        """)
    List<Object[]> buscarTarefasPorArea(@Param("idEmpresa") Integer idEmpresa);

//    @Query("""
//        SELECT ROUND(AVG(t.progresso), 2)
//        FROM TarefaEntity t
//        JOIN t.sprint s
//        JOIN s.projeto p
//        WHERE p.empresa.idEmpresa = :idEmpresa
//        """)
//    Double mediaProgresso(@Param("idEmpresa") Integer idEmpresa);

    @Query("""
        SELECT SUM(p.orcamento)
        FROM Projeto p
        WHERE p.empresa.idEmpresa = :idEmpresa
        """)
    Double orcamentoTotal(@Param("idEmpresa") Integer idEmpresa);

    @Query("""
        SELECT COUNT(p)
        FROM Projeto p
        WHERE p.empresa.idEmpresa = :idEmpresa
        """)
    Integer totalProjetos(@Param("idEmpresa") Integer idEmpresa);

    @Query("""
        SELECT CASE WHEN COUNT(t) > 0 THEN true ELSE false END
        FROM Tarefa t
        JOIN t.sprint s
        JOIN s.projeto p
        WHERE p.empresa.idEmpresa = :idEmpresa AND t.comImpedimento = true
        """)
    boolean empresaComImpedimento(@Param("idEmpresa") Integer idEmpresa);

    @Query("""
        SELECT i
        FROM Investimento i
        WHERE i.projeto.empresa.idEmpresa = :idEmpresa
        ORDER BY i.dtInvestimento ASC
        """)
    List<Investimento> listarFinanceiroPorEmpresa(@Param("idEmpresa") Integer idEmpresa);
}
