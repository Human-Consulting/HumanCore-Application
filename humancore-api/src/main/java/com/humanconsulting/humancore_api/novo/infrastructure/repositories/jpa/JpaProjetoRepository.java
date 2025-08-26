package com.humanconsulting.humancore_api.novo.infrastructure.repositories.jpa;

import com.humanconsulting.humancore_api.novo.infrastructure.entities.ProjetoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JpaProjetoRepository extends JpaRepository<ProjetoEntity, Integer> {
    boolean existsByEmpresa_IdEmpresaAndDescricao(Integer idEmpresa, String descricao);

    List<ProjetoEntity> findAllByEmpresa_IdEmpresa(Integer idEmpresa);

    @Query("SELECT p.urlImagem FROM ProjetoEntity p WHERE p.id = :idProjeto")
    String findUrlImagemById(Integer idProjeto);
}
