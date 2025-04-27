package com.humanconsulting.humancore_api.repository;

import com.humanconsulting.humancore_api.controller.dto.atualizar.usuario.UsuarioAtualizarDto;
import com.humanconsulting.humancore_api.controller.dto.request.LoginRequestDto;
import com.humanconsulting.humancore_api.exception.EntidadeNaoEncontradaException;
import com.humanconsulting.humancore_api.exception.EntidadeRequisicaoFalhaException;
import com.humanconsulting.humancore_api.model.Tarefa;
import com.humanconsulting.humancore_api.model.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class UsuarioRepository {

    @Autowired private JdbcClient jdbcClient;

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

    public Optional<Usuario> selectWhereEmail(String email) {
        if (!existsByEmail(email)) return Optional.empty();
        return Optional.of(this.jdbcClient
                .sql("SELECT * FROM usuario WHERE email = ?")
                .param(email)
                .query(Usuario.class)
                .single());
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

    public String getDiretor(Integer idEmpresa) {
        return this.jdbcClient
                .sql("SELECT nome FROM usuario " +
                     "WHERE fkEmpresa = ? " +
                     "AND permissao = 'DIRETOR'")
                .param(idEmpresa)
                .query(String.class)
                .optional().orElse("Diretor não registrado.");
    }

    public Integer getTotalTarefas(Integer idUsuario) {
        return this.jdbcClient
                .sql("SELECT COUNT(idTarefa) FROM tarefa WHERE fkResponsavel = ?")
                .param(idUsuario)
                .query(Integer.class)
                .single();
    }

    public Boolean isComImpedimento(Integer idUsuario) {
        Integer qtdComImpedimento = this.jdbcClient
                .sql("SELECT COUNT(idTarefa) FROM tarefa WHERE fkResponsavel = ? AND comImpedimento = true")
                .param(idUsuario)
                .query(Integer.class)
                .single();

        return qtdComImpedimento > 0;
    }

    public List<Integer> getProjetosVinculados(Integer idUsuario) {
        return this.jdbcClient
                .sql("""
                        SELECT DISTINCT(fkProjeto) FROM tarefa 
                        JOIN sprint ON idSprint = fkSprint 
                        WHERE fkResponsavel = ?""")
                .param(idUsuario)
                .query(Integer.class)
                .list();
    }

    public List<Tarefa> getTarefasVInculadas(Integer idUsuario) {
        return this.jdbcClient
                .sql("""
                        SELECT * FROM tarefa
                        WHERE fkResponsavel = ?""")
                .param(idUsuario)
                .query(Tarefa.class)
                .list();
    }
}
