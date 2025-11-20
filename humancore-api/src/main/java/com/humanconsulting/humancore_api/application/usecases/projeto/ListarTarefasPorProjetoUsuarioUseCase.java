package com.humanconsulting.humancore_api.application.usecases.projeto;

import com.humanconsulting.humancore_api.domain.entities.TarefaUsuario;
import com.humanconsulting.humancore_api.domain.repositories.DashboardProjetoRepository;

import java.util.List;
import java.util.stream.Collectors;

public class ListarTarefasPorProjetoUsuarioUseCase {
    private final DashboardProjetoRepository dashboardProjetoRepository;

    public ListarTarefasPorProjetoUsuarioUseCase(DashboardProjetoRepository dashboardProjetoRepository) {
        this.dashboardProjetoRepository = dashboardProjetoRepository;
    }

    public List<TarefaUsuario> execute(Integer idProjeto) {
        List<Object[]> resultadoBruto = dashboardProjetoRepository.buscarTarefasPorProjetoUsuario(idProjeto);
        return resultadoBruto.stream()
                .map(obj -> new TarefaUsuario(
                        (String) obj[0],
                        ((Number) obj[1]).intValue()
                ))
                .collect(Collectors.toList());
    }
}

