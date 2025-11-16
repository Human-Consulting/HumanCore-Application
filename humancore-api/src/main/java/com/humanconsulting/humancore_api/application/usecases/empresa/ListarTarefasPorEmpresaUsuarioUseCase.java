package com.humanconsulting.humancore_api.application.usecases.empresa;

import com.humanconsulting.humancore_api.domain.entities.TarefaUsuario;
import com.humanconsulting.humancore_api.domain.repositories.DashboardEmpresaRepository;

import java.util.List;
import java.util.stream.Collectors;

public class ListarTarefasPorEmpresaUsuarioUseCase {
    private final DashboardEmpresaRepository dashboardEmpresaRepository;

    public ListarTarefasPorEmpresaUsuarioUseCase(DashboardEmpresaRepository dashboardEmpresaRepository) {
        this.dashboardEmpresaRepository = dashboardEmpresaRepository;
    }

    public List<TarefaUsuario> execute(Integer idEmpresa) {
        List<Object[]> resultadoBruto = dashboardEmpresaRepository.buscarTarefasPorEmpresaUsuario(idEmpresa);
        return resultadoBruto.stream()
                .map(obj -> new TarefaUsuario(
                        (String) obj[0],
                        ((Number) obj[1]).intValue()
                ))
                .collect(Collectors.toList());
    }
}

