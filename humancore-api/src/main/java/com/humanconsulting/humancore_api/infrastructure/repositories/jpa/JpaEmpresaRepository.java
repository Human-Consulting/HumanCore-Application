package com.humanconsulting.humancore_api.infrastructure.repositories.jpa;

import com.humanconsulting.humancore_api.infrastructure.entities.EmpresaEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaEmpresaRepository extends JpaRepository<EmpresaEntity, Integer> {
    boolean existsByCnpj(String cnpj);

    Page<EmpresaEntity> findAll(Pageable pageable);

    @Query("SELECT e.urlImagem FROM EmpresaEntity e WHERE e.idEmpresa = :idEmpresa")
    String findUrlImagemById(Integer idEmpresa);
}
