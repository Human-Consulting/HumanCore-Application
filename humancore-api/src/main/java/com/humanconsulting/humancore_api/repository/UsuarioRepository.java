package com.humanconsulting.humancore_api.repository;

import com.humanconsulting.humancore_api.exception.EntidadeConflitanteException;
import com.humanconsulting.humancore_api.exception.EntidadeNaoEncontradaException;
import com.humanconsulting.humancore_api.exception.EntidadeRequisicaoFalhaException;
import com.humanconsulting.humancore_api.model.Usuario;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UsuarioRepository {

    private final JdbcClient jdbcClient;

    public UsuarioRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    public Usuario insert(Usuario usuario) {
        int result = jdbcClient.sql("INSERT INTO usuario (nome, email, senha, cargo, area, fkEmpresa) VALUES (?, ?, ?, ?, ?, ?)")
                .param(usuario.getNome())
                .param(usuario.getEmail())
                .param(usuario.getSenha())
                .param(usuario.getCargo())
                .param(usuario.getArea())
                .param(usuario.getFkEmpresa())
                .update();

        if (result > 0) {;
            usuario.setIdUsuario(jdbcClient.sql("SELECT LAST_INSERT_ID()")
                    .query(Integer.class).single());
            return usuario;
        } throw new EntidadeRequisicaoFalhaException("Falha na inserção de usuario");
    }

    public List<Usuario> selectAll() {
        return this.jdbcClient
                .sql("SELECT * FROM usuario")
                .query(Usuario.class)
                .list();
    }

    public void existsById(Integer id) {
        if (!this.jdbcClient
                .sql("SELECT 1 FROM usuario WHERE idUsuario = ?")
                .param(id)
                .query(Integer.class)
                .optional()
                .isPresent()) throw new EntidadeNaoEncontradaException("Usuario com o ID " + id + " não encontrada.");
    }

    public void existsByEmail(String email) {
        if (this.jdbcClient
                .sql("SELECT 1 FROM usuario WHERE email = ?")
                .param(email)
                .query(Integer.class)
                .optional()
                .isPresent()) throw new EntidadeConflitanteException(email + " já registrado.");
    }

    public Usuario selectWhereId(Integer id) {
        existsById(id);
        Usuario usuario = this.jdbcClient
                .sql("SELECT * FROM usuario WHERE idUsuario = ?")
                .param(id)
                .query(Usuario.class)
                .single();
        return usuario;
    }

    public boolean deleteWhere(Integer id) {
        existsById(id);

        return this.jdbcClient
                .sql("DELETE FROM usuario WHERE idUsuario = ?")
                .param(id)
                .update() > 0;
    }
}
