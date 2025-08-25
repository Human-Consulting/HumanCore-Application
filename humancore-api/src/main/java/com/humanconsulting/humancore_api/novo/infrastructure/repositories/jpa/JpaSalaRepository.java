package com.humanconsulting.humancore_api.novo.infrastructure.repositories.jpa;

import com.humanconsulting.humancore_api.velho.model.Empresa;
import com.humanconsulting.humancore_api.velho.model.Projeto;
import com.humanconsulting.humancore_api.velho.model.Sala;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface JpaSalaRepository extends JpaRepository<Sala, Integer> {

    Sala findByProjeto(Projeto projeto);

    Sala findByEmpresa(Empresa empresa);

    @Query("""
    SELECT DISTINCT s FROM SalaEntity s
    JOIN s.usuarios u
    LEFT JOIN FETCH s.usuarios su
    LEFT JOIN FETCH s.projeto p
    WHERE u.idUsuario = :idUsuario
""")
    List<Sala> findSalasComUsuariosPorUsuario(@Param("idUsuario") Integer idUsuario);

    @Query("SELECT s FROM SalaEntity s LEFT JOIN FETCH s.usuarios WHERE s.idSala = :idSala")
    Optional<Sala> buscarComUsuarios(@Param("idSala") Integer idSala);
}
