package com.humanconsulting.humancore_api.novo.domain.repositories;

import com.humanconsulting.humancore_api.novo.domain.entities.Investimento;
import java.util.List;

public interface DashboardEmpresaRepository {

    List<Object[]> buscarTarefasPorArea(Integer idEmpresa);

//    Double mediaProgresso(Integer idEmpresa);

    Double orcamentoTotal(Integer idEmpresa);

    Integer totalProjetos(Integer idEmpresa);

    boolean empresaComImpedimento(Integer idEmpresa);

    List<Investimento> listarFinanceiroPorEmpresa(Integer idEmpresa);
}