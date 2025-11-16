package com.humanconsulting.humancore_api.infrastructure.repositories.jpa;

import com.humanconsulting.humancore_api.infrastructure.entities.TarefaEntity;
import com.humanconsulting.humancore_api.infrastructure.entities.UsuarioEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    Page<UsuarioEntity> findByFkEmpresa(@Param("idEmpresa") Integer idEmpresa, Pageable pageable);

    @Query("SELECT u FROM UsuarioEntity u WHERE u.empresa.idEmpresa = :idEmpresa OR u.permissao LIKE '%CONSULTOR%'")
    Page<UsuarioEntity> findByFkEmpresaOrUsuarioPermissaoLikeConsultor(@Param("idEmpresa") Integer idEmpresa, Pageable pageable);

    @Query("SELECT u FROM UsuarioEntity u WHERE u.empresa.idEmpresa = :idEmpresa AND (:nome IS NULL OR LOWER(u.nome) LIKE LOWER(CONCAT('%', :nome, '%')))")
    Page<UsuarioEntity> findByFkEmpresa_IdEmpresaAndNomeContainingIgnoreCase(Integer idEmpresa, String nome, Pageable pageable);

    @Query("SELECT u FROM UsuarioEntity u WHERE (u.empresa.idEmpresa = :idEmpresa OR u.permissao LIKE '%CONSULTOR%') AND (:nome IS NULL OR LOWER(u.nome) LIKE LOWER(CONCAT('%', :nome, '%')))")
    Page<UsuarioEntity> findByFkEmpresa_IdEmpresaOrUsuarioPermissaoLikeConsultorAndNomeContainingIgnoreCase(Integer idEmpresa, String nome, Pageable pageable);

    @Query("SELECT u FROM UsuarioEntity u WHERE u.email = :email AND u.senha = :senha")
    Optional<UsuarioEntity> autenticar(String email, String senha);

    @Query("SELECT u FROM UsuarioEntity u WHERE u.empresa.idEmpresa = :idEmpresa AND u.permissao LIKE '%DIRETOR%'")
    Optional<UsuarioEntity> findDiretorByEmpresaId(@Param("idEmpresa") Integer idEmpresa);

    @Query("SELECT COUNT(t.idTarefa) FROM TarefaEntity t WHERE t.responsavel.idUsuario = :idUsuario")
    Integer countTarefasByUsuario(@Param("idUsuario") Integer idUsuario);

    @Query("SELECT COUNT(t.idTarefa) > 0 FROM TarefaEntity t WHERE t.responsavel.idUsuario = :idUsuario AND t.comImpedimento = true")
    Boolean hasTarefaComImpedimento(@Param("idUsuario") Integer idUsuario);

    @Query("SELECT DISTINCT s.projeto.id FROM TarefaEntity t JOIN t.sprint s WHERE t.responsavel.idUsuario = :idUsuario")
    List<Integer> findProjetosVinculados(@Param("idUsuario") Integer idUsuario);

    @Query("SELECT t FROM TarefaEntity t WHERE t.responsavel.idUsuario = :idUsuario")
    List<TarefaEntity> findTarefasVinculadas(@Param("idUsuario") Integer idUsuario);

    @Query("SELECT u FROM UsuarioEntity u WHERE u.empresa.idEmpresa = :idEmpresa AND u.permissao NOT LIKE '%FUNC%'")
    Page<UsuarioEntity> findByFkEmpresaAndPermissaoNot(@Param("idEmpresa") Integer idEmpresa, Pageable pageable);
}
