package com.humanconsulting.humancore_api.novo.application.usecases.projeto;

import com.humanconsulting.humancore_api.novo.domain.entities.Area;
import com.humanconsulting.humancore_api.novo.domain.entities.Checkpoint;
import com.humanconsulting.humancore_api.novo.domain.entities.Projeto;
import com.humanconsulting.humancore_api.novo.domain.repositories.CheckpointRepository;
import com.humanconsulting.humancore_api.novo.domain.repositories.UsuarioRepository;
import com.humanconsulting.humancore_api.novo.web.dtos.response.investimento.InvestimentoResponseDto;
import com.humanconsulting.humancore_api.novo.web.dtos.response.projeto.DashboardProjetoResponseDto;
import com.humanconsulting.humancore_api.novo.web.mappers.ProjetoMapper;

import java.util.List;
import java.util.Optional;

public class CriarDashboardProjetoUseCase {
    private final UsuarioRepository usuarioRepository;
    private final DashboardProjetoRepository dashboardProjetoRepository;
    private final CheckpointRepository checkpointRepository;
    private final ListarTarefasPorAreaUseCase listarTarefasPorAreaUseCase;
    private final ListarFinanceiroPorProjetoUseCase listarFinanceiroPorProjetoUseCase;

    public CriarDashboardProjetoUseCase(UsuarioRepository usuarioRepository, DashboardProjetoRepository dashboardProjetoRepository, CheckpointRepository checkpointRepository, ListarTarefasPorAreaUseCase listarTarefasPorAreaUseCase, ListarFinanceiroPorProjetoUseCase listarFinanceiroPorProjetoUseCase) {
        this.usuarioRepository = usuarioRepository;
        this.dashboardProjetoRepository = dashboardProjetoRepository;
        this.checkpointRepository = checkpointRepository;
        this.listarTarefasPorAreaUseCase = listarTarefasPorAreaUseCase;
        this.listarFinanceiroPorProjetoUseCase = listarFinanceiroPorProjetoUseCase;
    }

    public DashboardProjetoResponseDto execute(Projeto projeto) {
        Optional<com.humanconsulting.humancore_api.velho.model.Usuario> usuario = usuarioRepository.findById(projeto.getResponsavel().getIdUsuario());
        String nomeDiretor = usuario.get().getNome();
        List<Area> areas = listarTarefasPorAreaUseCase.execute(projeto.getIdProjeto());
        Double orcamento = dashboardProjetoRepository.orcamentoTotal(projeto.getIdProjeto());
        Integer projetos = dashboardProjetoRepository.totalSprints(projeto.getIdProjeto());
        Boolean comImpedimento = dashboardProjetoRepository.projetoComImpedimento(projeto.getIdProjeto());
        List<InvestimentoResponseDto> allResponse = listarFinanceiroPorProjetoUseCase.execute(projeto.getIdProjeto());
        List<Checkpoint> checkpoints = checkpointRepository.findAllByTarefa_Sprint_Projeto_IdProjeto(projeto.getIdProjeto());
        Double progresso = ProgressoCalculator.calularProgresso(checkpoints);
        return ProjetoMapper.toDashboard(projeto, nomeDiretor, progresso, areas, orcamento, projetos, comImpedimento, allResponse);
    }
}

