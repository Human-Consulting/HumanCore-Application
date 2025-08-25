package com.humanconsulting.humancore_api.novo.domain.repositories;

import com.humanconsulting.humancore_api.novo.domain.entities.Investimento;
import java.util.List;

public interface InvestimentoRepository {
    List<Investimento> findAllByProjeto_IdProjeto(Integer idProjeto);

    Investimento save(Investimento investimento);
    Investimento findById(Integer id);
    List<Investimento> findAll();
    void deleteById(Integer id);
}
