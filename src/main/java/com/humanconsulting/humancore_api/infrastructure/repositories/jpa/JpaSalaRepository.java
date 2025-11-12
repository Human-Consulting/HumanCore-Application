package com.humanconsulting.humancore_api.infrastructure.repositories.jpa;

import com.humanconsulting.humancore_api.infrastructure.entities.EmpresaEntity;
import com.humanconsulting.humancore_api.infrastructure.entities.ProjetoEntity;
import com.humanconsulting.humancore_api.infrastructure.entities.SalaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface JpaSalaRepository extends JpaRepository<SalaEntity, Integer> {

    @Query("SELECT s FROM SalaEntity s LEFT JOIN FETCH s.usuarios WHERE s.projeto = :projeto")
    SalaEntity findByProjetoComUsuarios(@Param("projeto") ProjetoEntity projeto);


    SalaEntity findByEmpresa(EmpresaEntity empresa);

    @Query("""
    SELECT DISTINCT s FROM SalaEntity s
    JOIN s.usuarios u
    LEFT JOIN FETCH s.usuarios su
    LEFT JOIN FETCH s.projeto p
    WHERE u.idUsuario = :idUsuario
""")
    List<SalaEntity> findSalasComUsuariosPorUsuario(@Param("idUsuario") Integer idUsuario);

    @Query("SELECT s FROM SalaEntity s LEFT JOIN FETCH s.usuarios WHERE s.idSala = :idSala")
    Optional<SalaEntity> buscarComUsuarios(@Param("idSala") Integer idSala);
}
