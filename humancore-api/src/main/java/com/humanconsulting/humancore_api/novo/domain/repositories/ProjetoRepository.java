package com.humanconsulting.humancore_api.novo.domain.repositories;

import com.humanconsulting.humancore_api.novo.domain.entities.Projeto;
import java.util.List;

public interface ProjetoRepository {
    boolean existsByEmpresa_IdEmpresaAndDescricao(Integer idEmpresa, String descricao);
    List<Projeto> findAllByEmpresa_IdEmpresa(Integer idEmpresa);
    String findUrlImagemById(Integer idProjeto);

    Projeto save(Projeto projeto);
    Projeto findById(Integer id);
    List<Projeto> findAll();
    void deleteById(Integer id);
}
