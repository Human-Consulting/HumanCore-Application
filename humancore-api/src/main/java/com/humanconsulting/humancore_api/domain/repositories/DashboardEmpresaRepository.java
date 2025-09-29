package com.humanconsulting.humancore_api.domain.repositories;

import com.humanconsulting.humancore_api.domain.entities.Investimento;
import com.humanconsulting.humancore_api.domain.entities.Tarefa;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DashboardEmpresaRepository {
    List<Object[]> buscarTarefasPorArea(Integer idEmpresa);
    Double orcamentoTotal(Integer idEmpresa);
    Integer totalProjetos(@Param("idEmpresa") Integer idEmpresa);
    boolean empresaComImpedimento(Integer idEmpresa);
    List<Investimento> listarFinanceiroPorEmpresa(Integer idEmpresa);
}

