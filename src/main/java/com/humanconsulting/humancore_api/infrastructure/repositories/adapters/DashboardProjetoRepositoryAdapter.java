package com.humanconsulting.humancore_api.infrastructure.repositories.adapters;

import com.humanconsulting.humancore_api.domain.repositories.DashboardProjetoRepository;
import com.humanconsulting.humancore_api.infrastructure.mappers.InvestimentoMapper;
import com.humanconsulting.humancore_api.infrastructure.repositories.jpa.JpaDashboardProjetoRepository;
import com.humanconsulting.humancore_api.domain.entities.Investimento;

import java.util.List;

public class DashboardProjetoRepositoryAdapter implements DashboardProjetoRepository {
    private final JpaDashboardProjetoRepository jpaDashboardProjetoRepository;

    public DashboardProjetoRepositoryAdapter(JpaDashboardProjetoRepository jpaDashboardProjetoRepository) {
        this.jpaDashboardProjetoRepository = jpaDashboardProjetoRepository;
    }

    @Override
    public List<Object[]> buscarTarefasPorArea(Integer idProjeto) {
        return jpaDashboardProjetoRepository.buscarTarefasPorArea(idProjeto);
    }

    @Override
    public List<Object[]> buscarTarefasPorProjetoUsuario(Integer idProjeto) {
        return jpaDashboardProjetoRepository.buscarTarefasPorProjetoUsuario(idProjeto);
    }

    @Override
    public Double orcamentoTotal(Integer idProjeto) {
        return jpaDashboardProjetoRepository.orcamentoTotal(idProjeto);
    }

    @Override
    public Integer totalSprints(Integer idProjeto) { return jpaDashboardProjetoRepository.totalSprints(idProjeto); }

    @Override
    public boolean projetoComImpedimento(Integer idProjeto) { return jpaDashboardProjetoRepository.projetoComImpedimento(idProjeto); }

    @Override
    public List<Investimento> listarFinanceiroPorProjeto(Integer idProjeto) {
        return jpaDashboardProjetoRepository.listarFinanceiroPorProjeto(idProjeto)
                .stream()
                .map(InvestimentoMapper::toDomain)
                .toList();
    }
}

