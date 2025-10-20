package com.humanconsulting.humancore_api.domain.repositories;

import com.humanconsulting.humancore_api.domain.entities.Projeto;
import java.util.List;
import java.util.Optional;

public interface ProjetoRepository {
    boolean existsByEmpresa_IdEmpresaAndDescricao(Integer idEmpresa, String descricao);
    List<Projeto> findAllByEmpresa_IdEmpresa(Integer idEmpresa);
    String findUrlImagemById(Integer idProjeto);

    Projeto save(Projeto projeto);
    Optional<Projeto> findById(Integer id);
    List<Projeto> findAll();
    void deleteById(Integer id);
}
