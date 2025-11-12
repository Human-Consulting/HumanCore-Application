package com.humanconsulting.humancore_api.domain.repositories;

import com.humanconsulting.humancore_api.domain.entities.Investimento;
import com.humanconsulting.humancore_api.domain.utils.PageResult;

import java.util.List;
import java.util.Optional;

public interface InvestimentoRepository {
    PageResult<Investimento> findAllByProjeto_IdProjeto(Integer idProjeto, int page, int size);

    Investimento save(Investimento investimento);
    Optional<Investimento> findById(Integer id);
    List<Investimento> findAll();
    void deleteById(Integer id);
}
