package com.humanconsulting.humancore_api.application.usecases.projeto;

import com.humanconsulting.humancore_api.application.usecases.projeto.mappers.ProjetoResponseMapper;
import com.humanconsulting.humancore_api.domain.entities.Projeto;
import com.humanconsulting.humancore_api.web.dtos.response.projeto.DashboardProjetoResponseDto;

public class CriarDashboardProjetoUseCase {
    private final ProjetoResponseMapper projetoResponseMapper;
    private final BuscarProjetoPorIdUseCase buscarProjetoPorIdUseCase;

    public CriarDashboardProjetoUseCase(ProjetoResponseMapper projetoResponseMapper, BuscarProjetoPorIdUseCase buscarProjetoPorIdUseCase) {
        this.projetoResponseMapper = projetoResponseMapper;
        this.buscarProjetoPorIdUseCase = buscarProjetoPorIdUseCase;
    }

    public DashboardProjetoResponseDto execute(Integer idProjeto) {
        Projeto projeto = buscarProjetoPorIdUseCase.execute(idProjeto);
        return projetoResponseMapper.toResponseDashboard(projeto);
    }
}

