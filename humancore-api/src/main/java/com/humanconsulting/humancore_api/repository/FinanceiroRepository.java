package com.humanconsulting.humancore_api.repository;

import com.humanconsulting.humancore_api.exception.EntidadeNaoEncontradaException;
import com.humanconsulting.humancore_api.exception.EntidadeRequisicaoFalhaException;
import com.humanconsulting.humancore_api.model.Financeiro;
import com.humanconsulting.humancore_api.model.Usuario;
import com.humanconsulting.humancore_api.service.UsuarioService;
import jakarta.validation.constraints.NotBlank;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class FinanceiroRepository {

    private final JdbcClient jdbcClient;
    private final UsuarioService usuarioService;

    public FinanceiroRepository(JdbcClient jdbcClient, UsuarioService usuarioService) {
        this.jdbcClient = jdbcClient;
        this.usuarioService = usuarioService;
    }

    public Financeiro insert(Financeiro financeiro) {
        int result = jdbcClient.sql("INSERT INTO financeiro (valor, dtInvestimento, fkProjeto) VALUES (?, ?, ?)")
                .param(financeiro.getValor())
                .param(financeiro.getDtInvestimento())
                .param(financeiro.getFkProjeto())
                .update();

        if (result > 0) {
            financeiro.setIdFinanceiro(jdbcClient.sql("SELECT LAST_INSERT_ID()")
                    .query(Integer.class).single());
            return financeiro;
        } throw new EntidadeRequisicaoFalhaException("Falha na inserção de financeiro");
    }

    public List<Financeiro> selectAll() {
        return this.jdbcClient
                .sql("SELECT * FROM financeiro")
                .query(Financeiro.class)
                .list();
    }

    public void existsById(Integer id) {
        if (!this.jdbcClient
                .sql("SELECT 1 FROM financeiro WHERE idFinanceiro = ?")
                .param(id)
                .query(Integer.class)
                .optional()
                .isPresent()) throw new EntidadeNaoEncontradaException("Financeiro com o ID " + id + " não encontrada.");
    }

    public Financeiro selectWhereId(Integer id) {
        existsById(id);
        Financeiro financeiro = this.jdbcClient
                .sql("SELECT * FROM financeiro WHERE idFinanceiro = ?")
                .param(id)
                .query(Financeiro.class)
                .single();
        return financeiro;
    }

    public boolean deleteWhere(Integer id) {
        existsById(id);

        return this.jdbcClient
                .sql("DELETE FROM financeiro WHERE idFinanceiro = ?")
                .param(id)
                .update() > 0;
    }

    public Boolean validarPermissao(Integer idEditor, @NotBlank String permissaoEditor) {

        return true;
    }
}
