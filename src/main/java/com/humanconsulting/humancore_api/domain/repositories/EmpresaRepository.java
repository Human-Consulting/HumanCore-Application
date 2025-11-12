package com.humanconsulting.humancore_api.domain.repositories;

import com.humanconsulting.humancore_api.domain.entities.Empresa;
import com.humanconsulting.humancore_api.domain.utils.PageResult;

import java.util.List;
import java.util.Optional;

public interface EmpresaRepository {
    boolean existsByCnpj(String cnpj);
    String findUrlImagemById(Integer idEmpresa);

    Empresa save(Empresa empresa);
    Optional<Empresa> findById(Integer id);
    PageResult<Empresa> findAll(int page, int size);
    List<Empresa> findAll();
    void deleteById(Integer id);

    PageResult<Empresa> findAllByNomeContainingIgnoreCase(int page, int size, String nome);
}
