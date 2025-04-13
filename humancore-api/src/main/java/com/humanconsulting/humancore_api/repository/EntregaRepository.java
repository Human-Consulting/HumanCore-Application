package com.humanconsulting.humancore_api.repository;

import com.humanconsulting.humancore_api.controller.dto.atualizar.entrega.AtualizarGeralRequestDto;
import com.humanconsulting.humancore_api.exception.EntidadeNaoEncontradaException;
import com.humanconsulting.humancore_api.exception.EntidadeRequisicaoFalhaException;
import com.humanconsulting.humancore_api.model.Entrega;
import com.humanconsulting.humancore_api.model.Sprint;
import com.humanconsulting.humancore_api.model.Usuario;
import com.humanconsulting.humancore_api.service.UsuarioService;
import jakarta.validation.constraints.NotBlank;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class EntregaRepository {

    private final JdbcClient jdbcClient;
    private final UsuarioService usuarioService;

    public EntregaRepository(JdbcClient jdbcClient, UsuarioService usuarioService) {
        this.jdbcClient = jdbcClient;
        this.usuarioService = usuarioService;
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
            entrega.setIdEntrega(jdbcClient.sql("SELECT LAST_INSERT_ID()")
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

    public List<Entrega> selectWhereIdProjeto(Integer idProjeto, Integer idSprint) {
        return this.jdbcClient
                .sql("SELECT entrega.* FROM entrega " +
                     "JOIN sprint ON fkSprint = idSprint " +
                     "WHERE fkProjeto = ? " +
                     "AND idSprint = ?")
                .params(idProjeto, idSprint)
                .query(Entrega.class)
                .list();
    }

    public void existsById(Integer id) {
        if (this.jdbcClient
                .sql("SELECT 1 FROM entrega WHERE idEntrega = ?")
                .param(id)
                .query(Integer.class)
                .optional()
                .isEmpty()) throw new EntidadeNaoEncontradaException("Entrega com o ID " + id + " não encontrada.");
    }

    public Entrega selectWhereId(Integer id) {
        existsById(id);
        return this.jdbcClient
                .sql("SELECT * FROM entrega WHERE idEntrega = ?")
                .param(id)
                .query(Entrega.class)
                .single();
    }

    public boolean deleteWhere(Integer id) {
        existsById(id);

        return this.jdbcClient
                .sql("DELETE FROM entrega WHERE idEntrega = ?")
                .param(id)
                .update() > 0;
    }

    public Entrega update(Integer idEntrega, AtualizarGeralRequestDto dto) {
        this.jdbcClient.sql(
                        "UPDATE entrega SET descricao = ?, " +
                                "dtInicio = ?, " +
                                "dtFim = ?, " +
                                "progresso = ?, " +
                                "fkResponsavel = ? " +
                                "WHERE idEntrega = ?"
                )
                .params(dto.getDescricao(), dto.getDtInicio(), dto.getDtFim(), dto.getProgresso(), dto.getFkResponsavel(), idEntrega)
                .update();

        return this.selectWhereId(idEntrega);
    }

    public Entrega updateFinalizar(Integer idEntrega) {
        this.jdbcClient.sql(
                        "UPDATE entrega SET progresso = 100 WHERE idEntrega = ?"
                )
                .param(idEntrega)
                .update();

        return this.selectWhereId(idEntrega);
    }

    public Boolean validarPermissao(Integer idEditor, @NotBlank String permissaoEditor) {

        return true;
    }

    public double mediaProgressoSprint(Integer idProjeto) {
        return this.jdbcClient
                .sql(
                        "SELECT ROUND(AVG(progresso), 2) AS progressoSprint " +
                        "FROM entrega " +
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
                            "FROM entrega " +
                            "JOIN sprint ON fkSprint = idSprint " +
                            "WHERE fkProjeto = ? " +
                            "GROUP BY idSprint" +
                        ") AS progressoPorSprint")
                .param(idProjeto)
                .query(Double.class)
                .optional()
                .orElse(0.0);
    }

    public boolean projetoComImpedimento(Integer idProjeto) {
        return this.jdbcClient.sql(
                "SELECT EXISTS (" +
                        "SELECT 1 FROM entrega " +
                        "JOIN sprint ON fkSprint = idSprint " +
                        "WHERE comImpedimento = 1 " +
                        "AND fkProjeto = ? " +
                        ") AS projetoComImpedimento"
                ).param(idProjeto)
                .query(Boolean.class)
                .single();
    }

    public Entrega updateImpedimento(Integer idEntrega) {
        this.jdbcClient.sql(
                        "UPDATE entrega SET comImpedimento = NOT comImpedimento WHERE idEntrega = ?"
                )
                .param(idEntrega)
                .update();

        return this.selectWhereId(idEntrega);
    }
}
