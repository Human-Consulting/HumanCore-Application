package com.humanconsulting.humancore_api.velho.repository;

import com.humanconsulting.humancore_api.velho.model.Projeto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjetoRepository extends JpaRepository<Projeto, Integer> {
    boolean existsByEmpresa_IdEmpresaAndDescricao(Integer idEmpresa, String descricao);

    List<Projeto> findAllByEmpresa_IdEmpresa(Integer idEmpresa);

    @Query("SELECT p.urlImagem FROM Projeto p WHERE p.id = :idProjeto")
    String findUrlImagemById(Integer idProjeto);
}
