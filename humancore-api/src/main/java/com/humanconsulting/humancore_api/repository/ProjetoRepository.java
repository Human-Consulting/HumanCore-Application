package com.humanconsulting.humancore_api.repository;

import com.humanconsulting.humancore_api.controller.dto.atualizar.projeto.ProjetoAtualizarRequestDto;
import com.humanconsulting.humancore_api.exception.EntidadeNaoEncontradaException;
import com.humanconsulting.humancore_api.exception.EntidadeRequisicaoFalhaException;
import com.humanconsulting.humancore_api.model.Projeto;
import com.humanconsulting.humancore_api.model.Usuario;
import com.humanconsulting.humancore_api.service.UsuarioService;
import jakarta.validation.constraints.NotBlank;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProjetoRepository {

    private final JdbcClient jdbcClient;
    private final UsuarioService usuarioService;

    public ProjetoRepository(JdbcClient jdbcClient, UsuarioService usuarioService) {
        this.jdbcClient = jdbcClient;
        this.usuarioService = usuarioService;
    }

    public Projeto insert(Projeto projeto) {
        int result = jdbcClient.sql("INSERT INTO projeto (descricao, orcamento, urlImagem, fkEmpresa, fkResponsavel) VALUES (?, ?, ?, ?, ?)")
                .param(projeto.getDescricao())
                .param(projeto.getOrcamento())
                .param(projeto.getUrlImagem())
                .param(projeto.getFkEmpresa())
                .param(projeto.getFkResponsavel())
                .update();

        if (result > 0) {
            projeto.setIdProjeto(jdbcClient.sql("SELECT LAST_INSERT_ID()")
                    .query(Integer.class).single());
            return projeto;
        } throw new EntidadeRequisicaoFalhaException("Falha na inserção de projeto");
    }

    public List<Projeto> selectAll() {
        return this.jdbcClient
                .sql("SELECT * FROM projeto")
                .query(Projeto.class)
                .list();
    }

    public void existsById(Integer id) {
        if (!this.jdbcClient
                .sql("SELECT 1 FROM projeto WHERE idProjeto = ?")
                .param(id)
                .query(Integer.class)
                .optional()
                .isPresent()) throw new EntidadeNaoEncontradaException("Projeto com o ID " + id + " não encontrada.");
    }

    public boolean existsByNome(Integer idEmpresa, String nome) {
        if (!this.jdbcClient
                .sql("SELECT 1 FROM projeto WHERE fkEmpresa = ? AND descricao = ?")
                .param(idEmpresa)
                .param(nome)
                .query(Integer.class)
                .optional()
                .isPresent()) return false;
        return true;
    }

    public Projeto selectWhereId(Integer id) {
        existsById(id);
        Projeto projeto = this.jdbcClient
                .sql("SELECT * FROM projeto WHERE idProjeto = ?")
                .param(id)
                .query(Projeto.class)
                .single();
        return projeto;
    }

    public boolean deleteWhere(Integer id) {
        existsById(id);

        return this.jdbcClient
                .sql("DELETE FROM projeto WHERE idProjeto = ?")
                .param(id)
                .update() > 0;
    }

    public Boolean validarPermissao(Integer idEditor, @NotBlank String permissaoEditor) {


        return true;
    }

    public List<Projeto> selectWhereIdEmpresa(Integer idEmpresa) {
        return this.jdbcClient
                .sql("SELECT * FROM projeto WHERE fkEmpresa = ?")
                .param(idEmpresa)
                .query(Projeto.class)
                .list();
    }

    public Projeto update(Integer idProjeto, ProjetoAtualizarRequestDto dto) {
        this.jdbcClient.sql(
                        "UPDATE projeto SET descricao = ?, orcamento = ?, urlImagem = ?, fkResponsavel = ? WHERE idProjeto = ?"
                )
                .params(dto.getDescricao(), dto.getOrcamento(), dto.getUrlImagem(), dto.getFkResponsavel(), idProjeto)
                .update();

        return this.selectWhereId(idProjeto);
    }
}
