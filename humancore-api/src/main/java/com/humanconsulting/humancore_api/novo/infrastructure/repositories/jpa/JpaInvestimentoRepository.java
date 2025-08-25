package com.humanconsulting.humancore_api.novo.infrastructure.repositories.jpa;

import com.humanconsulting.humancore_api.velho.model.Investimento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JpaInvestimentoRepository extends JpaRepository<Investimento, Integer> {

    List<Investimento> findAllByProjeto_IdProjeto(Integer idProjeto);
}
