package com.humanconsulting.humancore_api.novo.infrastructure.repositories.jpa;

import com.humanconsulting.humancore_api.novo.infrastructure.entities.InvestimentoEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JpaInvestimentoRepository extends JpaRepository<InvestimentoEntity, Integer> {
    Page<InvestimentoEntity> findAllByProjeto_IdProjeto(Integer idProjeto, Pageable pageable);
}
