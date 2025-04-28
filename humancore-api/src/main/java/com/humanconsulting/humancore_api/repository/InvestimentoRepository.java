package com.humanconsulting.humancore_api.repository;

import com.humanconsulting.humancore_api.controller.dto.atualizar.investimento.AtualizarInvestimentoRequestDto;
import com.humanconsulting.humancore_api.exception.EntidadeNaoEncontradaException;
import com.humanconsulting.humancore_api.exception.EntidadeRequisicaoFalhaException;
import com.humanconsulting.humancore_api.model.Investimento;
import com.humanconsulting.humancore_api.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class InvestimentoRepository {

    private final JdbcClient jdbcClient;
    private final UsuarioService usuarioService;

    public InvestimentoRepository(JdbcClient jdbcClient, UsuarioService usuarioService) {
        this.jdbcClient = jdbcClient;
        this.usuarioService = usuarioService;
    }

    public Investimento insert(Investimento investimento) {
        int result = jdbcClient.sql("INSERT INTO investimento (valor, dtInvestimento, fkProjeto) VALUES (?, ?, ?)")
                .param(investimento.getValor())
                .param(investimento.getDtInvestimento())
                .param(investimento.getFkProjeto())
                .update();

        if (result > 0) {
            investimento.setIdInvestimento(jdbcClient.sql("SELECT LAST_INSERT_ID()")
                    .query(Integer.class).single());
            return investimento;
        } throw new EntidadeRequisicaoFalhaException("Falha na inserção de investimento");
    }

    public List<Investimento> selectAll() {
        return this.jdbcClient
                .sql("SELECT * FROM investimento")
                .query(Investimento.class)
                .list();
    }

    public void existsById(Integer id) {
        if (!this.jdbcClient
                .sql("SELECT 1 FROM investimento WHERE idInvestimento = ?")
                .param(id)
                .query(Integer.class)
                .optional()
                .isPresent()) throw new EntidadeNaoEncontradaException("Investimento com o ID " + id + " não encontrada.");
    }

    public Investimento selectWhereId(Integer id) {
        existsById(id);
        Investimento investimento = this.jdbcClient
                .sql("SELECT * FROM investimento WHERE idInvestimento = ?")
                .param(id)
                .query(Investimento.class)
                .single();
        return investimento;
    }

    public boolean deleteWhere(Integer id) {
        existsById(id);

        return this.jdbcClient
                .sql("DELETE FROM investimento WHERE idInvestimento = ?")
                .param(id)
                .update() > 0;
    }

    public List<Investimento> selectAllWhereIdProjeto(Integer idProjeto) {
        return this.jdbcClient
                .sql("SELECT * FROM investimento WHERE fkProjeto = ?")
                .param(idProjeto)
                .query(Investimento.class)
                .list();
    }

    public Investimento update(Integer idInvestimento, @Valid AtualizarInvestimentoRequestDto request) {
        this.jdbcClient.sql(
                        """
                                UPDATE investimento SET valor = ?, 
                                dtInvestimento = ? 
                                WHERE idInvestimento = ?"""
                )
                .params(request.getValor(), request.getDtInvestimento(), idInvestimento)
                .update();

        return this.selectWhereId(idInvestimento);
    }
}
