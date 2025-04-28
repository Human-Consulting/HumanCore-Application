package com.humanconsulting.humancore_api.repository;

import com.humanconsulting.humancore_api.model.Investimento;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DashboardProjetoRepository {
    private final JdbcClient jdbcClient;

    public DashboardProjetoRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    public List<Object[]> buscarTarefasPorArea(Integer idProjeto) {
        return this.jdbcClient
                .sql("""
                    SELECT area, COUNT(*) AS totalTarefas
                    FROM tarefa
                    JOIN usuario ON fkResponsavel = idUsuario
                    JOIN sprint ON fkSprint = idSprint
                    WHERE fkProjeto = ?
                    GROUP BY area
                    ORDER BY totalTarefas DESC;
                    """)
                .params(idProjeto)
                .query((rs, rowNum) -> new Object[] {
                        rs.getString("area"),
                        rs.getInt("totalTarefas")
                })
                .list();
    }

    public double mediaProgresso(Integer idProjeto) {
        return this.jdbcClient
                .sql("SELECT ROUND(AVG(progresso), 2) AS mediaProgresso " +
                     "FROM tarefa " +
                     "JOIN sprint ON fkSprint = idSprint " +
                     "WHERE fkProjeto = ?;")
                .param(idProjeto)
                .query(Double.class)
                .optional()
                .orElse(0.0);
    }

    public double orcamentoTotal(Integer idProjeto) {
        return this.jdbcClient
                .sql("SELECT orcamento " +
                     "FROM projeto " +
                     "WHERE idProjeto = ?;")
                .param(idProjeto)
                .query(Double.class)
                .optional()
                .orElse(0.0);
    }

    public Integer totalSprints(Integer idProjeto) {
        return this.jdbcClient
                .sql("SELECT COUNT(*) AS totalSprints " +
                     "FROM sprint " +
                     "WHERE fkProjeto = ?;")
                .param(idProjeto)
                .query(Integer.class)
                .optional()
                .orElse(0);
    }

    public boolean projetoComImpedimento(Integer idProjeto) {
        return this.jdbcClient.sql(
                        "SELECT EXISTS (" +
                            "SELECT 1 FROM tarefa " +
                            "JOIN sprint ON fkSprint = idSprint " +
                            "WHERE fkProjeto = ? " +
                            "AND comImpedimento = true" +
                        ") AS projetoComImpedimento;"
                ).param(idProjeto)
                .query(Boolean.class)
                .single();
    }

    public List<Investimento> listarFinanceiroPorEmpresa(Integer idProjeto) {
        return this.jdbcClient.sql(
                """
                SELECT * FROM investimento
                WHERE fkProjeto = ?
                ORDER BY dtInvestimento ASC;"""
        )
                .param(idProjeto)
                .query(Investimento.class)
                .list();
    }
}
