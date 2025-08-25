package com.humanconsulting.humancore_api.novo.domain.repositories;

import com.humanconsulting.humancore_api.novo.domain.entities.Empresa;

import java.util.List;

public interface EmpresaRepository {
    boolean existsByCnpj(String cnpj);
    String findUrlImagemById(Integer idEmpresa);

    Empresa save(Empresa empresa);
    Empresa findById(Integer id);
    List<Empresa> findAll();
    void deleteById(Integer id);
}
