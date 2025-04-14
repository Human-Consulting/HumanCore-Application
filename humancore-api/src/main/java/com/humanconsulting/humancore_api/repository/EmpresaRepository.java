package com.humanconsulting.humancore_api.repository;

import com.humanconsulting.humancore_api.exception.EntidadeConflitanteException;
import com.humanconsulting.humancore_api.exception.EntidadeNaoEncontradaException;
import com.humanconsulting.humancore_api.exception.EntidadeRequisicaoFalhaException;
import com.humanconsulting.humancore_api.model.Empresa;
import com.humanconsulting.humancore_api.service.UsuarioService;
import jakarta.validation.constraints.NotBlank;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class EmpresaRepository {

    private final JdbcClient jdbcClient;

    public EmpresaRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    public Empresa insert(Empresa empresa) {
        int result = jdbcClient.sql("INSERT INTO empresa (cnpj, nome) VALUES (?, ?)")
                .param(empresa.getCnpj())
                .param(empresa.getNome())
                .update();

        if (result > 0) {;
            empresa.setIdEmpresa(jdbcClient.sql("SELECT LAST_INSERT_ID()")
                    .query(Integer.class).single());
            return empresa;
        } throw new EntidadeRequisicaoFalhaException("Falha na inserção de empresa");
    }

    public List<Empresa> selectAll() {
        return this.jdbcClient
                .sql("SELECT * FROM empresa")
                .query(Empresa.class)
                .list();
    }

    public void existsById(Integer id) {
        if (!this.jdbcClient
                .sql("SELECT 1 FROM empresa WHERE idEmpresa = ?")
                .param(id)
                .query(Integer.class)
                .optional()
                .isPresent()) throw new EntidadeNaoEncontradaException("Empresa com o ID " + id + " não encontrada.");
    }

    public void existsByCnpj(String cnpj) {
        if (this.jdbcClient
                .sql("SELECT 1 FROM empresa WHERE cnpj = ?")
                .param(cnpj)
                .query(Integer.class)
                .optional()
                .isPresent()) throw new EntidadeConflitanteException(cnpj + " já registrado.");
    }

    public Empresa selectWhereId(Integer id) {
        existsById(id);
        Empresa empresa = this.jdbcClient
                .sql("SELECT * FROM empresa WHERE idEmpresa = ?")
                .param(id)
                .query(Empresa.class)
                .single();
        return empresa;
    }

    public boolean deleteWhere(Integer idEmpresa) {
        existsById(idEmpresa);

        return this.jdbcClient
                .sql("DELETE FROM empresa WHERE idEmpresa = ?")
                .param(idEmpresa)
                .update() > 0;
    }
}
