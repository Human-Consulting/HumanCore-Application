package com.humanconsulting.humancore_api.novo.application.usecases.empresa;

import com.humanconsulting.humancore_api.novo.application.usecases.empresa.mappers.DashboardEmpresaMapper;
import com.humanconsulting.humancore_api.novo.domain.entities.Empresa;
import com.humanconsulting.humancore_api.novo.domain.repositories.CheckpointRepository;
import com.humanconsulting.humancore_api.novo.domain.repositories.DashboardEmpresaRepository;
import com.humanconsulting.humancore_api.novo.domain.repositories.UsuarioRepository;
import com.humanconsulting.humancore_api.novo.web.dtos.response.empresa.DashboardEmpresaResponseDto;

public class CriarDashboardEmpresaUseCase {
    private final UsuarioRepository usuarioRepository;
    private final DashboardEmpresaRepository dashRepository;
    private final CheckpointRepository checkpointRepository;
    private final ListarTarefasPorAreaUseCase listarTarefasPorAreaUseCase;
    private final ListarFinanceiroPorEmpresaUseCase listarFinanceiroPorEmpresaUseCase;
    private final DashboardEmpresaMapper dashboardEmpresaMapper;

    public CriarDashboardEmpresaUseCase(
            UsuarioRepository usuarioRepository,
            DashboardEmpresaRepository dashRepository,
            CheckpointRepository checkpointRepository,
            ListarTarefasPorAreaUseCase listarTarefasPorAreaUseCase,
            ListarFinanceiroPorEmpresaUseCase listarFinanceiroPorEmpresaUseCase,
            DashboardEmpresaMapper dashboardEmpresaMapper) {
        this.usuarioRepository = usuarioRepository;
        this.dashRepository = dashRepository;
        this.checkpointRepository = checkpointRepository;
        this.listarTarefasPorAreaUseCase = listarTarefasPorAreaUseCase;
        this.listarFinanceiroPorEmpresaUseCase = listarFinanceiroPorEmpresaUseCase;
        this.dashboardEmpresaMapper = dashboardEmpresaMapper;
    }

    public DashboardEmpresaResponseDto execute(Empresa empresa) {
        String nomeDiretor = usuarioRepository.findDiretorByEmpresaId(empresa.getIdEmpresa()).get();
        var areas = listarTarefasPorAreaUseCase.execute(empresa.getIdEmpresa());
        Double orcamento = dashRepository.orcamentoTotal(empresa.getIdEmpresa());
        Integer projetos = dashRepository.totalProjetos(empresa.getIdEmpresa());
        Boolean comImpedimento = dashRepository.empresaComImpedimento(empresa.getIdEmpresa());
        var allResponse = listarFinanceiroPorEmpresaUseCase.execute(empresa.getIdEmpresa());
        var checkpoints = checkpointRepository.findAllByTarefa_Sprint_Projeto_Empresa_IdEmpresa(empresa.getIdEmpresa());
        return dashboardEmpresaMapper.toDashboard(empresa, nomeDiretor, checkpoints, areas, orcamento, projetos, comImpedimento, allResponse);
    }
}

