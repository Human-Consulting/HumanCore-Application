package com.humanconsulting.humancore_api.novo.domain.repositories;

import com.humanconsulting.humancore_api.novo.domain.entities.Investimento;
import java.util.List;

public interface DashboardProjetoRepository {
    List<Object[]> buscarTarefasPorArea(Integer idProjeto);

//    Double mediaProgresso(Integer idProjeto);

    Double orcamentoTotal(Integer idProjeto);

    Integer totalSprints(Integer idProjeto);

    boolean projetoComImpedimento(Integer idProjeto);

    List<Investimento> listarFinanceiroPorEmpresa(Integer idProjeto);
}