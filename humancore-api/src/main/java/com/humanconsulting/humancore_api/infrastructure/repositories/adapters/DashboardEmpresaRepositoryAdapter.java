package com.humanconsulting.humancore_api.infrastructure.repositories.adapters;

import com.humanconsulting.humancore_api.domain.repositories.DashboardEmpresaRepository;
import com.humanconsulting.humancore_api.infrastructure.mappers.InvestimentoMapper;
import com.humanconsulting.humancore_api.infrastructure.repositories.jpa.JpaDashboardEmpresaRepository;
import com.humanconsulting.humancore_api.domain.entities.Investimento;

import java.util.List;

public class DashboardEmpresaRepositoryAdapter implements DashboardEmpresaRepository {
    private final JpaDashboardEmpresaRepository jpaDashboardEmpresaRepository;

    public DashboardEmpresaRepositoryAdapter(JpaDashboardEmpresaRepository jpaDashboardEmpresaRepository) {
        this.jpaDashboardEmpresaRepository = jpaDashboardEmpresaRepository;
    }

    @Override
    public List<Object[]> buscarTarefasPorArea(Integer idEmpresa) {
        return jpaDashboardEmpresaRepository.buscarTarefasPorArea(idEmpresa);
    }

    @Override
    public Double orcamentoTotal(Integer idEmpresa) {
        return jpaDashboardEmpresaRepository.orcamentoTotal(idEmpresa);
    }

    @Override
    public Integer totalProjetos(Integer idEmpresa) { return jpaDashboardEmpresaRepository.totalProjetos(idEmpresa); }

    @Override
    public boolean empresaComImpedimento(Integer idEmpresa) { return jpaDashboardEmpresaRepository.empresaComImpedimento(idEmpresa); }

    @Override
    public List<Investimento> listarFinanceiroPorEmpresa(Integer idEmpresa) {
        return jpaDashboardEmpresaRepository.listarFinanceiroPorEmpresa(idEmpresa)
                .stream()
                .map(InvestimentoMapper::toDomain)
                .toList();
    }
}

