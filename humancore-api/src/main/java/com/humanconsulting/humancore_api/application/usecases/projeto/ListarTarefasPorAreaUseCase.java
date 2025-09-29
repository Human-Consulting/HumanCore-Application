package com.humanconsulting.humancore_api.application.usecases.projeto;

import com.humanconsulting.humancore_api.domain.entities.Area;
import com.humanconsulting.humancore_api.domain.repositories.DashboardProjetoRepository;

import java.util.List;
import java.util.stream.Collectors;

public class ListarTarefasPorAreaUseCase {
    private final DashboardProjetoRepository dashboardProjetoRepository;

    public ListarTarefasPorAreaUseCase(DashboardProjetoRepository dashboardProjetoRepository) {
        this.dashboardProjetoRepository = dashboardProjetoRepository;
    }

    public List<Area> execute(Integer idProjeto) {
        List<Object[]> resultadoBruto = dashboardProjetoRepository.buscarTarefasPorArea(idProjeto);
        return resultadoBruto.stream()
                .map(obj -> new Area(
                        (String) obj[0],
                        ((Number) obj[1]).intValue()
                ))
                .collect(Collectors.toList());
    }
}

