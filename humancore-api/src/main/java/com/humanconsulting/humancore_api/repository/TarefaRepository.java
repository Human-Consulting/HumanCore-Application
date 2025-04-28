package com.humanconsulting.humancore_api.repository;

import com.humanconsulting.humancore_api.controller.dto.atualizar.tarefa.AtualizarGeralRequestDto;
import com.humanconsulting.humancore_api.exception.EntidadeNaoEncontradaException;
import com.humanconsulting.humancore_api.exception.EntidadeRequisicaoFalhaException;
import com.humanconsulting.humancore_api.model.Tarefa;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TarefaRepository {

    private final JdbcClient jdbcClient;

    public TarefaRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    public Tarefa insert(Tarefa tarefa) {
        int result = jdbcClient.sql("INSERT INTO tarefa (descricao, dtInicio, dtFim, progresso, comImpedimento, fkSprint, fkResponsavel) VALUES (?, ?, ?, ?, ?, ?, ?)")
                .param(tarefa.getDescricao())
                .param(tarefa.getDtInicio())
                .param(tarefa.getDtFim())
                .param(tarefa.getProgresso())
                .param(tarefa.getComImpedimento())
                .param(tarefa.getFkSprint())
                .param(tarefa.getFkResponsavel())
                .update();

        if (result > 0) {
            ;
            tarefa.setIdTarefa(jdbcClient.sql("SELECT LAST_INSERT_ID()")
                    .query(Integer.class).single());
            return tarefa;
        }
        throw new EntidadeRequisicaoFalhaException("Falha na inserção de tarefa");
    }

    public List<Tarefa> selectAll() {
        return this.jdbcClient
                .sql("SELECT * FROM tarefa")
                .query(Tarefa.class)
                .list();
    }

    public List<Tarefa> selectWhereIdProjeto(Integer idProjeto, Integer idSprint) {
        return this.jdbcClient
                .sql("SELECT tarefa.* FROM tarefa " +
                        "JOIN sprint ON fkSprint = idSprint " +
                        "WHERE fkProjeto = ? " +
                        "AND idSprint = ?")
                .params(idProjeto, idSprint)
                .query(Tarefa.class)
                .list();
    }

    public List<Tarefa> selectWhereIdSprint(Integer idSprint) {
        return this.jdbcClient
                .sql("SELECT * FROM tarefa WHERE fkSprint = ?")
                .params(idSprint)
                .query(Tarefa.class)
                .list();
    }

    public void existsById(Integer id) {
        if (this.jdbcClient
                .sql("SELECT 1 FROM tarefa WHERE idTarefa = ?")
                .param(id)
                .query(Integer.class)
                .optional()
                .isEmpty()) throw new EntidadeNaoEncontradaException("Tarefa com o ID " + id + " não encontrada.");
    }

    public Tarefa selectWhereId(Integer id) {
        existsById(id);
        return this.jdbcClient
                .sql("SELECT * FROM tarefa WHERE idTarefa = ?")
                .param(id)
                .query(Tarefa.class)
                .single();
    }

    public boolean deleteWhere(Integer id) {
        existsById(id);

        return this.jdbcClient
                .sql("DELETE FROM tarefa WHERE idTarefa = ?")
                .param(id)
                .update() > 0;
    }

    public Tarefa update(Integer idTarefa, AtualizarGeralRequestDto dto) {
        this.jdbcClient.sql(
                        "UPDATE tarefa SET descricao = ?, " +
                                "dtInicio = ?, " +
                                "dtFim = ?, " +
                                "progresso = ?, " +
                                "fkResponsavel = ? " +
                                "WHERE idTarefa = ?"
                )
                .params(dto.getDescricao(), dto.getDtInicio(), dto.getDtFim(), dto.getProgresso(), dto.getFkResponsavel(), idTarefa)
                .update();

        return this.selectWhereId(idTarefa);
    }

    public double mediaProgressoSprint(Integer idProjeto) {
        return this.jdbcClient
                .sql(
                        "SELECT ROUND(AVG(progresso), 2) AS progressoSprint " +
                                "FROM tarefa " +
                                "JOIN sprint ON fkSprint = idSprint " +
                                "WHERE idSprint = ? " +
                                "GROUP BY idSprint;")
                .param(idProjeto)
                .query(Double.class)
                .optional()
                .orElse(0.0);
    }

    public double mediaProgressoProjeto(Integer idProjeto) {
        return this.jdbcClient
                .sql(
                        "SELECT ROUND(AVG(progressoSprint), 2) " +
                                "FROM (" +
                                "SELECT idSprint, ROUND(AVG(progresso), 2) AS progressoSprint " +
                                "FROM tarefa " +
                                "JOIN sprint ON fkSprint = idSprint " +
                                "WHERE fkProjeto = ? " +
                                "GROUP BY idSprint" +
                                ") AS progressoPorSprint")
                .param(idProjeto)
                .query(Double.class)
                .optional()
                .orElse(0.0);
    }

    public boolean sprintComImpedimento(Integer idSprint) {
        return this.jdbcClient.sql(
                        "SELECT EXISTS (" +
                                "SELECT 1 FROM tarefa " +
                                "WHERE comImpedimento = 1 " +
                                "AND fkSprint = ? " +
                                ") AS sprintComImpedimento"
                ).param(idSprint)
                .query(Boolean.class)
                .single();
    }

    public boolean projetoComImpedimento(Integer idProjeto) {
        return this.jdbcClient.sql(
                        "SELECT EXISTS (" +
                                "SELECT 1 FROM tarefa " +
                                "JOIN sprint ON fkSprint = idSprint " +
                                "WHERE comImpedimento = 1 " +
                                "AND fkProjeto = ? " +
                                ") AS projetoComImpedimento"
                ).param(idProjeto)
                .query(Boolean.class)
                .single();
    }

    public Tarefa updateImpedimento(Integer idTarefa) {
        this.jdbcClient.sql(
                        "UPDATE tarefa SET comImpedimento = NOT comImpedimento WHERE idTarefa = ?"
                )
                .param(idTarefa)
                .update();

        return this.selectWhereId(idTarefa);
    }
}
