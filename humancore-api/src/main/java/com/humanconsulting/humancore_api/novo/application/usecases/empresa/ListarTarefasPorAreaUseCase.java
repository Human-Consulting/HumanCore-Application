package com.humanconsulting.humancore_api.novo.application.usecases.empresa;

import com.humanconsulting.humancore_api.novo.domain.entities.Area;

import java.util.List;
import java.util.stream.Collectors;

public class ListarTarefasPorAreaUseCase {
    private final DashboardEmpresaRepository dashRepository;

    public ListarTarefasPorAreaUseCase(DashboardEmpresaRepository dashRepository) {
        this.dashRepository = dashRepository;
    }

    public List<Area> execute(Integer idEmpresa) {
        List<Object[]> resultadoBruto = dashRepository.buscarTarefasPorArea(idEmpresa);
        return resultadoBruto.stream()
                .map(obj -> new Area(
                        (String) obj[0],
                        ((Number) obj[1]).intValue()
                ))
                .collect(Collectors.toList());
    }
}

