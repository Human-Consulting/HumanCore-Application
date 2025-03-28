package com.humanconsulting.humancore_api.repository;

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
        int result = jdbcClient.sql("INSERT INTO projeto (descricao, progresso, orcamento, com_impedimento, fkEmpresa, fkResponsavel) VALUES (?, ?, ?, ?, ?, ?)")
                .param(projeto.getDescricao())
                .param(projeto.getProgresso())
                .param(projeto.getOrcamento())
                .param(projeto.getCom_impedimento())
                .param(projeto.getFk_empresa())
                .param(projeto.getFk_responsavel())
                .update();

        if (result > 0) {;
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
        Usuario usuario = usuarioService.buscarPorId(idEditor);

        return usuario.getPermissao().equals(permissaoEditor);
    }
}
