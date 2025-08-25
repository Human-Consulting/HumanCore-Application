package com.humanconsulting.humancore_api.novo.infrastructure.repositories.jpa;

import com.humanconsulting.humancore_api.novo.infrastructure.entities.TarefaEntity;
import com.humanconsulting.humancore_api.novo.infrastructure.entities.UsuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface JpaUsuarioRepository extends JpaRepository<UsuarioEntity, Integer> {
    boolean existsByEmail(String email);

    Optional<UsuarioEntity> findByEmail(String email);

    @Query("SELECT u FROM UsuarioEntity u WHERE u.empresa.idEmpresa = :idEmpresa")
    List<UsuarioEntity> findByFkEmpresa(@Param("idEmpresa") Integer idEmpresa);

    @Query("SELECT u FROM UsuarioEntity u WHERE u.email = :email AND u.senha = :senha")
    Optional<UsuarioEntity> autenticar(String email, String senha);

    @Query("SELECT u.nome FROM UsuarioEntity u WHERE u.empresa.idEmpresa = :idEmpresa AND u.permissao = 'DIRETOR'")
    Optional<String> findDiretorByEmpresaId(@Param("idEmpresa") Integer idEmpresa);

    @Query("SELECT COUNT(t.idTarefa) FROM TarefaEntity t WHERE t.responsavel.idUsuario = :idUsuario")
    Integer countTarefasByUsuario(@Param("idUsuario") Integer idUsuario);

    @Query("SELECT COUNT(t.idTarefa) > 0 FROM TarefaEntity t WHERE t.responsavel.idUsuario = :idUsuario AND t.comImpedimento = true")
    Boolean hasTarefaComImpedimento(@Param("idUsuario") Integer idUsuario);

    @Query("SELECT DISTINCT s.projeto.id FROM TarefaEntity t JOIN t.sprint s WHERE t.responsavel.idUsuario = :idUsuario")
    List<Integer> findProjetosVinculados(@Param("idUsuario") Integer idUsuario);

    @Query("SELECT t FROM TarefaEntity t WHERE t.responsavel.idUsuario = :idUsuario")
    List<TarefaEntity> findTarefasVinculadas(@Param("idUsuario") Integer idUsuario);
}
