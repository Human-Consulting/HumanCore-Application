package com.humanconsulting.humancore_api.velho.repository;

import com.humanconsulting.humancore_api.velho.model.Empresa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface EmpresaRepository extends JpaRepository<Empresa, Integer> {
    boolean existsByCnpj(String cnpj);

    @Query("SELECT e.urlImagem FROM Empresa e WHERE e.id = :idEmpresa")
    String findUrlImagemById(Integer idEmpresa);
}
