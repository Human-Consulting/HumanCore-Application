package com.humanconsulting.humancore_api.domain.repositories;

import com.humanconsulting.humancore_api.domain.entities.Investimento;

import java.util.List;

public interface DashboardProjetoRepository {
    List<Object[]> buscarTarefasPorArea(Integer idProjeto);

//    Double mediaProgresso(@Param("idProjeto") Integer idProjeto);

    Double orcamentoTotal(Integer idProjeto);

    Integer totalSprints(Integer idProjeto);

    boolean projetoComImpedimento(Integer idProjeto);

    List<Investimento> listarFinanceiroPorProjeto(Integer idProjeto);

}

