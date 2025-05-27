package com.humanconsulting.humancore_api.repository;

import com.humanconsulting.humancore_api.model.Tarefa;
import com.humanconsulting.humancore_api.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    boolean existsByEmail(String email);

    Optional<Usuario> findByEmail(String email);

    @Query("SELECT u FROM Usuario u WHERE u.empresa.idEmpresa = :idEmpresa")
    List<Usuario> findByFkEmpresa(@Param("idEmpresa") Integer idEmpresa);

    @Query("SELECT u FROM Usuario u WHERE u.email = :email AND u.senha = :senha")
    Optional<Usuario> autenticar(String email, String senha);

    @Query("SELECT u.nome FROM Usuario u WHERE u.empresa.idEmpresa = :idEmpresa AND u.permissao = 'DIRETOR'")
    Optional<String> findDiretorByEmpresaId(@Param("idEmpresa") Integer idEmpresa);

    @Query("SELECT COUNT(t.idTarefa) FROM Tarefa t WHERE t.responsavel.idUsuario = :idUsuario")
    Integer countTarefasByUsuario(@Param("idUsuario") Integer idUsuario);

    @Query("SELECT COUNT(t.idTarefa) > 0 FROM Tarefa t WHERE t.responsavel.idUsuario = :idUsuario AND t.comImpedimento = true")
    Boolean hasTarefaComImpedimento(@Param("idUsuario") Integer idUsuario);

    @Query("SELECT DISTINCT s.projeto.id FROM Tarefa t JOIN t.sprint s WHERE t.responsavel.idUsuario = :idUsuario")
    List<Integer> findProjetosVinculados(@Param("idUsuario") Integer idUsuario);

    @Query("SELECT t FROM Tarefa t WHERE t.responsavel.idUsuario = :idUsuario")
    List<Tarefa> findTarefasVinculadas(@Param("idUsuario") Integer idUsuario);
}
