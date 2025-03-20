package com.humanconsulting.humancore_api.repository;

import com.humanconsulting.humancore_api.exception.EntidadeNaoEncontradaException;
import com.humanconsulting.humancore_api.exception.EntidadeRequisicaoFalhaException;
import com.humanconsulting.humancore_api.model.Entrega;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class EntregaRepository {

    private final JdbcClient jdbcClient;

    public EntregaRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    public Entrega insert(Entrega entrega) {
        int result = jdbcClient.sql("INSERT INTO entrega (descricao, dtInicio, dtFim, progresso, comImpedimento, fkSprint, fkResponsavel) VALUES (?, ?, ?, ?, ?, ?, ?)")
                .param(entrega.getDescricao())
                .param(entrega.getDtInicio())
                .param(entrega.getDtFim())
                .param(entrega.getProgresso())
                .param(entrega.getComImpedimento())
                .param(entrega.getFkSprint())
                .param(entrega.getFkResponsavel())
                .update();

        if (result > 0) {;
            entrega.setIdSprint(jdbcClient.sql("SELECT LAST_INSERT_ID()")
                    .query(Integer.class).single());
            return entrega;
        } throw new EntidadeRequisicaoFalhaException("Falha na inserção de entrega");
    }

    public List<Entrega> selectAll() {
        return this.jdbcClient
                .sql("SELECT * FROM entrega")
                .query(Entrega.class)
                .list();
    }

    public void existsById(Integer id) {
        if (!this.jdbcClient
                .sql("SELECT 1 FROM entrega WHERE idEntrega = ?")
                .param(id)
                .query(Integer.class)
                .optional()
                .isPresent()) throw new EntidadeNaoEncontradaException("Entrega com o ID " + id + " não encontrada.");
    }

    public Entrega selectWhereId(Integer id) {
        existsById(id);
        Entrega entrega = this.jdbcClient
                .sql("SELECT * FROM entrega WHERE idEntrega = ?")
                .param(id)
                .query(Entrega.class)
                .single();
        return entrega;
    }

    public boolean deleteWhere(Integer id) {
        existsById(id);

        return this.jdbcClient
                .sql("DELETE FROM entrega WHERE idEntrega = ?")
                .param(id)
                .update() > 0;
    }
}
