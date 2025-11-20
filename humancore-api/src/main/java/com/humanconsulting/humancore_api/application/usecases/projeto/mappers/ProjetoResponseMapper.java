package com.humanconsulting.humancore_api.application.usecases.projeto.mappers;

import com.humanconsulting.humancore_api.application.usecases.projeto.ListarFinanceiroPorProjetoUseCase;
import com.humanconsulting.humancore_api.application.usecases.projeto.ListarTarefasPorAreaUseCase;
import com.humanconsulting.humancore_api.application.usecases.projeto.ListarTarefasPorProjetoUsuarioUseCase;
import com.humanconsulting.humancore_api.domain.entities.Area;
import com.humanconsulting.humancore_api.domain.entities.Checkpoint;
import com.humanconsulting.humancore_api.domain.entities.Projeto;
import com.humanconsulting.humancore_api.domain.entities.TarefaUsuario;
import com.humanconsulting.humancore_api.domain.repositories.CheckpointRepository;
import com.humanconsulting.humancore_api.domain.repositories.DashboardProjetoRepository;
import com.humanconsulting.humancore_api.domain.repositories.TarefaRepository;
import com.humanconsulting.humancore_api.domain.repositories.UsuarioRepository;
import com.humanconsulting.humancore_api.domain.utils.ProgressoCalculator;
import com.humanconsulting.humancore_api.web.dtos.response.investimento.InvestimentoResponseDto;
import com.humanconsulting.humancore_api.web.dtos.response.projeto.DashboardProjetoResponseDto;
import com.humanconsulting.humancore_api.web.dtos.response.projeto.ProjetoResponseDto;
import com.humanconsulting.humancore_api.web.dtos.response.usuario.UsuarioSprintResponseDto;
import com.humanconsulting.humancore_api.web.mappers.ProjetoMapper;
import com.humanconsulting.humancore_api.web.mappers.UsuarioMapper;

import java.util.List;

public class ProjetoResponseMapper {
    private final TarefaRepository tarefaRepository;
    private final CheckpointRepository checkpointRepository;
    private final UsuarioRepository usuarioRepository;
    private final ListarTarefasPorAreaUseCase listarTarefasPorAreaUseCase;
    private final ListarTarefasPorProjetoUsuarioUseCase listarTarefasPorProjetoUsuarioUseCase;
    private final DashboardProjetoRepository dashboardProjetoRepository;
    private final ListarFinanceiroPorProjetoUseCase listarFinanceiroPorProjetoUseCase;

    public ProjetoResponseMapper(TarefaRepository tarefaRepository, CheckpointRepository checkpointRepository, UsuarioRepository usuarioRepository, ListarTarefasPorAreaUseCase listarTarefasPorAreaUseCase, DashboardProjetoRepository dashboardProjetoRepository, ListarFinanceiroPorProjetoUseCase listarFinanceiroPorProjetoUseCase, ListarTarefasPorProjetoUsuarioUseCase listarTarefasPorProjetoUsuarioUseCase) {
        this.tarefaRepository = tarefaRepository;
        this.checkpointRepository = checkpointRepository;
        this.usuarioRepository = usuarioRepository;
        this.listarTarefasPorAreaUseCase = listarTarefasPorAreaUseCase;
        this.dashboardProjetoRepository = dashboardProjetoRepository;
        this.listarFinanceiroPorProjetoUseCase = listarFinanceiroPorProjetoUseCase;
        this.listarTarefasPorProjetoUsuarioUseCase = listarTarefasPorProjetoUsuarioUseCase;
    }

    public ProjetoResponseDto toResponse(Projeto projeto) {
        boolean comImpedimento = tarefaRepository.existsImpedimentoByProjeto(projeto.getIdProjeto());
        List<Checkpoint> checkpoints = checkpointRepository.findAllByTarefa_Sprint_Projeto_IdProjeto(projeto.getIdProjeto());
        Double progresso = ProgressoCalculator.execute(checkpoints);
        UsuarioSprintResponseDto usuario = UsuarioMapper.toUsuarioSprintDto(projeto.getResponsavel());
        return ProjetoMapper.toDto(projeto, progresso, comImpedimento, usuario);
    }

    public ProjetoResponseDto toResponseKpi(Projeto projeto) {
        boolean comImpedimento = tarefaRepository.existsImpedimentoByProjeto(projeto.getIdProjeto());
        List<Checkpoint> checkpoints = checkpointRepository.findAllByTarefa_Sprint_Projeto_IdProjeto(projeto.getIdProjeto());
        Double progresso = ProgressoCalculator.execute(checkpoints);
        UsuarioSprintResponseDto usuario = UsuarioMapper.toUsuarioSprintDto(projeto.getResponsavel());
        return ProjetoMapper.toDto(projeto, progresso, comImpedimento, usuario);
    }

    public ProjetoResponseDto toResponseMenuRapido(Projeto projeto) {
        boolean comImpedimento = tarefaRepository.existsImpedimentoByProjeto(projeto.getIdProjeto());
        List<Checkpoint> checkpoints = checkpointRepository.findAllByTarefa_Sprint_Projeto_IdProjeto(projeto.getIdProjeto());
        Double progresso = ProgressoCalculator.execute(checkpoints);
        UsuarioSprintResponseDto usuario = UsuarioMapper.toUsuarioSprintDto(projeto.getResponsavel());
        return ProjetoMapper.toDto(projeto, progresso, comImpedimento, usuario);
    }

    public DashboardProjetoResponseDto toResponseDashboard(Projeto projeto) {
        List<Area> areas = listarTarefasPorAreaUseCase.execute(projeto.getIdProjeto());

        List<TarefaUsuario> usuarios = listarTarefasPorProjetoUsuarioUseCase.execute(projeto.getIdProjeto());

        Double orcamento = dashboardProjetoRepository.orcamentoTotal(projeto.getIdProjeto());

        Integer projetos = dashboardProjetoRepository.totalSprints(projeto.getIdProjeto());

        Boolean comImpedimento = dashboardProjetoRepository.projetoComImpedimento(projeto.getIdProjeto());

        List<InvestimentoResponseDto> allResponse = listarFinanceiroPorProjetoUseCase.execute(projeto.getIdProjeto());
        UsuarioSprintResponseDto responsavel = UsuarioMapper.toUsuarioSprintDto(projeto.getResponsavel());
        List<Checkpoint> checkpoints = checkpointRepository.findAllByTarefa_Sprint_Projeto_IdProjeto(projeto.getIdProjeto());
        Double progresso = ProgressoCalculator.execute(checkpoints);
        return ProjetoMapper.toDashboard(projeto, responsavel, progresso, areas, usuarios, orcamento, projetos, comImpedimento, allResponse);
    }
}

