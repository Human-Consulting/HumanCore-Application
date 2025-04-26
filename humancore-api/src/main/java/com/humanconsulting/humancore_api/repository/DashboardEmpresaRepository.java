package com.humanconsulting.humancore_api.repository;

import com.humanconsulting.humancore_api.model.Financeiro;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DashboardEmpresaRepository {
    private final JdbcClient jdbcClient;

    public DashboardEmpresaRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    public List<Object[]> buscarTarefasPorArea(Integer idEmpresa) {
        return this.jdbcClient
                .sql("""
                    SELECT area, COUNT(*) AS totalTarefas
                    FROM tarefa
                    JOIN usuario ON fkResponsavel = idUsuario
                    JOIN projeto ON fkSprint IN (
                        SELECT idSprint FROM sprint WHERE fkProjeto = idProjeto
                    )
                    WHERE usuario.fkEmpresa = ?
                    GROUP BY area
                    ORDER BY totalTarefas DESC;
                    """)
                .params(idEmpresa)
                .query((rs, rowNum) -> new Object[] {
                        rs.getString("area"),
                        rs.getInt("totalTarefas")
                })
                .list();
    }

    public double mediaProgresso(Integer idEmpresa) {
        return this.jdbcClient
                .sql("SELECT ROUND(AVG(progresso), 2) AS mediaProgresso " +
                     "FROM tarefa " +
                     "JOIN sprint ON fkSprint = idSprint " +
                     "JOIN projeto ON fkProjeto = idProjeto " +
                     "WHERE fkEmpresa = ?;")
                .param(idEmpresa)
                .query(Double.class)
                .optional()
                .orElse(0.0);
    }

    public double orcamentoTotal(Integer idEmpresa) {
        return this.jdbcClient
                .sql("SELECT SUM(orcamento) AS totalOrcamento " +
                     "FROM projeto " +
                     "WHERE fkEmpresa = ?;")
                .param(idEmpresa)
                .query(Double.class)
                .optional()
                .orElse(0.0);
    }

    public Integer totalProjetos(Integer idEmpresa) {
        return this.jdbcClient
                .sql("SELECT COUNT(*) AS totalSprints " +
                     "FROM projeto " +
                     "WHERE fkEmpresa = ?;")
                .param(idEmpresa)
                .query(Integer.class)
                .optional()
                .orElse(0);
    }

    public boolean empresaComImpedimento(Integer idEmpresa) {
        return this.jdbcClient.sql(
                        "SELECT EXISTS (" +
                            "SELECT 1 FROM tarefa " +
                            "JOIN sprint ON fkSprint = idSprint " +
                            "JOIN projeto ON fkProjeto = idProjeto " +
                            "WHERE fkEmpresa = ? " +
                            "AND comImpedimento = true" +
                        ") AS empresaComImpedimento;"
                ).param(idEmpresa)
                .query(Boolean.class)
                .single();
    }

    public List<Financeiro> listarFinanceiroPorEmpresa(Integer idEmpresa) {
        return this.jdbcClient.sql(
                """
                SELECT * FROM financeiro
                JOIN projeto ON idProjeto = fkProjeto
                WHERE fkEmpresa = ?
                ORDER BY dtInvestimento ASC;"""
        )
                .param(idEmpresa)
                .query(Financeiro.class)
                .list();
    }
}
