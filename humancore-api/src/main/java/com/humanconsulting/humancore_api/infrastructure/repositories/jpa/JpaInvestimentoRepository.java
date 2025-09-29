package com.humanconsulting.humancore_api.infrastructure.repositories.jpa;

import com.humanconsulting.humancore_api.infrastructure.entities.InvestimentoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JpaInvestimentoRepository extends JpaRepository<InvestimentoEntity, Integer> {
    List<InvestimentoEntity> findAllByProjeto_IdProjeto(Integer idProjeto);
}
