package com.humanconsulting.humancore_api.repository;

import com.humanconsulting.humancore_api.exception.EntidadeConflitanteException;
import com.humanconsulting.humancore_api.exception.EntidadeNaoEncontradaException;
import com.humanconsulting.humancore_api.exception.EntidadeRequisicaoFalhaException;
import com.humanconsulting.humancore_api.model.Sprint;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class SprintRepository {

    private final JdbcClient jdbcClient;

    public SprintRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    public Sprint insert(Sprint sprint) {
        int result = jdbcClient.sql("INSERT INTO sprint (descricao, dtInicio, dtFim, progresso, comImpedimento, fkProjeto) VALUES (?, ?, ?, ?, ?, ?)")
                .param(sprint.getDescricao())
                .param(sprint.getDtInicio())
                .param(sprint.getDtFim())
                .param(sprint.getProgresso())
                .param(sprint.getComImpedimento())
                .param(sprint.getFkProjeto())
                .update();

        if (result > 0) {;
            sprint.setIdSprint(jdbcClient.sql("SELECT LAST_INSERT_ID()")
                    .query(Integer.class).single());
            return sprint;
        } throw new EntidadeRequisicaoFalhaException("Falha na inserção de sprint");
    }

    public List<Sprint> selectAll() {
        return this.jdbcClient
                .sql("SELECT * FROM sprint")
                .query(Sprint.class)
                .list();
    }

    public void existsById(Integer id) {
        if (!this.jdbcClient
                .sql("SELECT 1 FROM sprint WHERE idSprint = ?")
                .param(id)
                .query(Integer.class)
                .optional()
                .isPresent()) throw new EntidadeNaoEncontradaException("Sprint com o ID " + id + " não encontrada.");
    }

    public Sprint selectWhereId(Integer id) {
        existsById(id);
        Sprint sprint = this.jdbcClient
                .sql("SELECT * FROM sprint WHERE idSprint = ?")
                .param(id)
                .query(Sprint.class)
                .single();
        return sprint;
    }

    public boolean deleteWhere(Integer id) {
        existsById(id);

        return this.jdbcClient
                .sql("DELETE FROM sprint WHERE idSprint = ?")
                .param(id)
                .update() > 0;
    }
}
