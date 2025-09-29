package com.humanconsulting.humancore_api.novo.domain.repositories;

import com.humanconsulting.humancore_api.novo.domain.entities.Projeto;
import java.util.List;
import java.util.Optional;

public interface ProjetoRepository {
    boolean existsByEmpresa_IdEmpresaAndDescricao(Integer idEmpresa, String descricao);
    PageResult<Projeto> findAllByEmpresa_IdEmpresa(Integer idEmpresa, int page, int size);
    String findUrlImagemById(Integer idProjeto);

    Projeto save(Projeto projeto);
    Optional<Projeto> findById(Integer id);
    List<Projeto> findAll();
    void deleteById(Integer id);
}