package com.humanconsulting.humancore_api.repository;

import com.humanconsulting.humancore_api.controller.dto.atualizar.entrega.AtualizarGeralRequestDto;
import com.humanconsulting.humancore_api.controller.dto.atualizar.usuario.UsuarioAtualizarDto;
import com.humanconsulting.humancore_api.controller.dto.request.LoginRequestDto;
import com.humanconsulting.humancore_api.exception.EntidadeConflitanteException;
import com.humanconsulting.humancore_api.exception.EntidadeNaoEncontradaException;
import com.humanconsulting.humancore_api.exception.EntidadeRequisicaoFalhaException;
import com.humanconsulting.humancore_api.model.Entrega;
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
        int result = jdbcClient.sql("INSERT INTO usuario (nome, email, senha, cargo, area, permissao, fkEmpresa) VALUES (?, ?, ?, ?, ?, ?, ?)")
                .param(usuario.getNome())
                .param(usuario.getEmail())
                .param(usuario.getSenha())
                .param(usuario.getCargo())
                .param(usuario.getArea())
                .param(usuario.getPermissao())
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
        if (this.jdbcClient
                .sql("SELECT 1 FROM usuario WHERE idUsuario = ?")
                .param(id)
                .query(Integer.class)
                .optional()
                .isEmpty()) throw new EntidadeNaoEncontradaException("Usuario com o ID " + id + " não encontrada.");
    }

    public boolean existsByEmail(String email) {
        if (this.jdbcClient
                .sql("SELECT 1 FROM usuario WHERE email = ?")
                .param(email)
                .query(Integer.class)
                .optional()
                .isPresent()) return true;
        return false;
    }

    public Usuario selectWhereId(Integer id) {
        existsById(id);
        return this.jdbcClient
                .sql("SELECT * FROM usuario WHERE idUsuario = ?")
                .param(id)
                .query(Usuario.class)
                .single();
    }

    public List<Usuario> selectWhereIdEmpresa(Integer idEmpresa) {
        return this.jdbcClient
                .sql("SELECT * FROM usuario WHERE fkEmpresa = ?")
                .param(idEmpresa)
                .query(Usuario.class)
                .list();
    }

    public boolean deleteWhere(Integer id) {
        existsById(id);

        return this.jdbcClient
                .sql("DELETE FROM usuario WHERE idUsuario = ?")
                .param(id)
                .update() > 0;
    }

    public Usuario update(Integer idUsuario, UsuarioAtualizarDto dto) {
        this.jdbcClient.sql(
                        "UPDATE usuario SET nome = ?, " +
                                "email = ?, " +
                                "senha = ?, " +
                                "cargo = ?, " +
                                "area = ?, " +
                                "permissao = ? " +
                                "WHERE idUsuario = ?"
                )
                .params(dto.getNome(), dto.getEmail(), dto.getSenha(), dto.getCargo(), dto.getArea(), dto.getPermissao(), idUsuario)
                .update();

        return this.selectWhereId(idUsuario);
    }

    public Usuario antenticar(LoginRequestDto usuarioAutenticar) {
        return this.jdbcClient
                .sql("SELECT * FROM usuario WHERE email = ? AND senha = ?")
                .params(usuarioAutenticar.getEmail(), usuarioAutenticar.getSenha())
                .query(Usuario.class)
                .optional()
                .orElse(null);
    }
}
