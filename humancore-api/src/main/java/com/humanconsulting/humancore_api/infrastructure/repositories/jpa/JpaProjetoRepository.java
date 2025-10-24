package com.humanconsulting.humancore_api.infrastructure.repositories.jpa;

import com.humanconsulting.humancore_api.infrastructure.entities.ProjetoEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JpaProjetoRepository extends JpaRepository<ProjetoEntity, Integer> {
    boolean existsByEmpresa_IdEmpresaAndDescricao(Integer idEmpresa, String descricao);

    Page<ProjetoEntity> findAllByEmpresa_IdEmpresa(Integer idEmpresa, Pageable pageable);

    @Query("SELECT p.urlImagem FROM ProjetoEntity p WHERE p.id = :idProjeto")
    String findUrlImagemById(Integer idProjeto);
}
