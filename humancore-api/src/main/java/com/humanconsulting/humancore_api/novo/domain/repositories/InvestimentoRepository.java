package com.humanconsulting.humancore_api.novo.domain.repositories;

import com.humanconsulting.humancore_api.novo.domain.entities.Investimento;
import java.util.List;
import java.util.Optional;

public interface InvestimentoRepository {
    List<Investimento> findAllByProjeto_IdProjeto(Integer idProjeto);

    Investimento save(Investimento investimento);
    Optional<Investimento> findById(Integer id);
    List<Investimento> findAll();
    void deleteById(Integer id);
}
