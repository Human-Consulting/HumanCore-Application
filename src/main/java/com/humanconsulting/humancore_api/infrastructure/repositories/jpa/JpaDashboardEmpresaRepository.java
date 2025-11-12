package com.humanconsulting.humancore_api.infrastructure.repositories.jpa;

import com.humanconsulting.humancore_api.infrastructure.entities.InvestimentoEntity;
import com.humanconsulting.humancore_api.infrastructure.entities.ProjetoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JpaDashboardEmpresaRepository extends JpaRepository<ProjetoEntity, Integer> {
    @Query("""
        SELECT u.area, COUNT(t)
        FROM TarefaEntity t
        JOIN t.responsavel u
        JOIN t.sprint s
        JOIN s.projeto p
        WHERE u.empresa.idEmpresa = :idEmpresa
        GROUP BY u.area
        ORDER BY COUNT(t) DESC
        """)
    List<Object[]> buscarTarefasPorArea(@Param("idEmpresa") Integer idEmpresa);

    @Query("""
        SELECT u.nome, COUNT(t)
        FROM TarefaEntity t
        JOIN t.responsavel u
        JOIN t.sprint s
        JOIN s.projeto p
        WHERE u.empresa.idEmpresa = :idEmpresa
        GROUP BY u.nome
        ORDER BY COUNT(t) DESC
        LIMIT 5
        """)
    List<Object[]> buscarTarefasPorEmpresaUsuario(@Param("idEmpresa") Integer idEmpresa);

    @Query("""
        SELECT SUM(p.orcamento)
        FROM ProjetoEntity p
        WHERE p.empresa.idEmpresa = :idEmpresa
        """)
    Double orcamentoTotal(@Param("idEmpresa") Integer idEmpresa);

    @Query("""
        SELECT COUNT(p)
        FROM ProjetoEntity p
        WHERE p.empresa.idEmpresa = :idEmpresa
        """)
    Integer totalProjetos(@Param("idEmpresa") Integer idEmpresa);

    @Query("""
        SELECT CASE WHEN COUNT(t) > 0 THEN true ELSE false END
        FROM TarefaEntity t
        JOIN t.sprint s
        JOIN s.projeto p
        WHERE p.empresa.idEmpresa = :idEmpresa AND t.comImpedimento = true
        """)
    Boolean empresaComImpedimento(@Param("idEmpresa") Integer idEmpresa);

    @Query("""
        SELECT i
        FROM InvestimentoEntity i
        WHERE i.projeto.empresa.idEmpresa = :idEmpresa
        ORDER BY i.dtInvestimento ASC
        """)
    List<InvestimentoEntity> listarFinanceiroPorEmpresa(@Param("idEmpresa") Integer idEmpresa);
}

