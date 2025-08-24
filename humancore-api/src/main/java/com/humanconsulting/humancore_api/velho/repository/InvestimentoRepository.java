package com.humanconsulting.humancore_api.velho.repository;

import com.humanconsulting.humancore_api.velho.model.Investimento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InvestimentoRepository extends JpaRepository<Investimento, Integer> {

    List<Investimento> findAllByProjeto_IdProjeto(Integer idProjeto);
}
