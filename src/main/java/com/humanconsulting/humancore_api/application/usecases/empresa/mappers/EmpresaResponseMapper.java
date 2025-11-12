package com.humanconsulting.humancore_api.application.usecases.empresa.mappers;

import com.humanconsulting.humancore_api.application.usecases.empresa.ListarFinanceiroPorEmpresaUseCase;
import com.humanconsulting.humancore_api.application.usecases.empresa.ListarTarefasPorAreaUseCase;
import com.humanconsulting.humancore_api.application.usecases.empresa.ListarTarefasPorEmpresaUsuarioUseCase;
import com.humanconsulting.humancore_api.domain.entities.*;
import com.humanconsulting.humancore_api.domain.repositories.CheckpointRepository;
import com.humanconsulting.humancore_api.domain.repositories.DashboardEmpresaRepository;
import com.humanconsulting.humancore_api.domain.repositories.UsuarioRepository;
import com.humanconsulting.humancore_api.domain.utils.ProgressoCalculator;
import com.humanconsulting.humancore_api.web.dtos.response.empresa.DashboardEmpresaResponseDto;
import com.humanconsulting.humancore_api.web.dtos.response.empresa.EmpresaResponseDto;
import com.humanconsulting.humancore_api.web.dtos.response.usuario.UsuarioSprintResponseDto;
import com.humanconsulting.humancore_api.web.mappers.EmpresaMapper;
import com.humanconsulting.humancore_api.web.mappers.UsuarioMapper;

import java.util.List;
import java.util.Optional;

public class EmpresaResponseMapper {
    private final UsuarioRepository usuarioRepository;
    private final DashboardEmpresaRepository dashRepository;
    private final CheckpointRepository checkpointRepository;
    private final ListarTarefasPorAreaUseCase listarTarefasPorAreaUseCase;
    private final ListarTarefasPorEmpresaUsuarioUseCase listarTarefasPorEmpresaUsuarioUseCase;
    private final ListarFinanceiroPorEmpresaUseCase listarFinanceiroPorEmpresaUseCase;

    public EmpresaResponseMapper(UsuarioRepository usuarioRepository,
                                DashboardEmpresaRepository dashRepository,
                                CheckpointRepository checkpointRepository,
                                 ListarTarefasPorAreaUseCase listarTarefasPorAreaUseCase,
                                 ListarFinanceiroPorEmpresaUseCase listarFinanceiroPorEmpresaUseCase,
                                 ListarTarefasPorEmpresaUsuarioUseCase listarTarefasPorEmpresaUsuarioUseCase) {
        this.usuarioRepository = usuarioRepository;
        this.dashRepository = dashRepository;
        this.checkpointRepository = checkpointRepository;
        this.listarTarefasPorAreaUseCase = listarTarefasPorAreaUseCase;
        this.listarFinanceiroPorEmpresaUseCase = listarFinanceiroPorEmpresaUseCase;
        this.listarTarefasPorEmpresaUsuarioUseCase = listarTarefasPorEmpresaUsuarioUseCase;
    }

    public EmpresaResponseDto toResponse(Empresa empresa) {
        Usuario diretor = usuarioRepository.findDiretorByEmpresaId(empresa.getIdEmpresa());
        Boolean comImpedimento = dashRepository.empresaComImpedimento(empresa.getIdEmpresa());
        Double orcamento = dashRepository.orcamentoTotal(empresa.getIdEmpresa());
        List<Checkpoint> checkpoints = checkpointRepository.findAllByTarefa_Sprint_Projeto_Empresa_IdEmpresa(empresa.getIdEmpresa());
        Double progresso = ProgressoCalculator.execute(checkpoints);
        UsuarioSprintResponseDto responsavel = UsuarioMapper.toUsuarioSprintDto(diretor);
        return EmpresaMapper.toDto(empresa, responsavel, comImpedimento, progresso, orcamento);
    }

    public EmpresaResponseDto toResponseMenuRapido(Empresa empresa) {
        Usuario diretor = usuarioRepository.findDiretorByEmpresaId(empresa.getIdEmpresa());
        Boolean comImpedimento = dashRepository.empresaComImpedimento(empresa.getIdEmpresa());
        Double orcamento = dashRepository.orcamentoTotal(empresa.getIdEmpresa());
        List<Checkpoint> checkpoints = checkpointRepository.findAllByTarefa_Sprint_Projeto_Empresa_IdEmpresa(empresa.getIdEmpresa());
        Double progresso = ProgressoCalculator.execute(checkpoints);
        UsuarioSprintResponseDto responsavel = UsuarioMapper.toUsuarioSprintDto(diretor);
        return EmpresaMapper.toDto(empresa, responsavel, comImpedimento, progresso, orcamento);
    }

    public EmpresaResponseDto toResponseKpi(Empresa empresa) {
        Usuario diretor = usuarioRepository.findDiretorByEmpresaId(empresa.getIdEmpresa());
        Boolean comImpedimento = dashRepository.empresaComImpedimento(empresa.getIdEmpresa());
        Double orcamento = dashRepository.orcamentoTotal(empresa.getIdEmpresa());
        List<Checkpoint> checkpoints = checkpointRepository.findAllByTarefa_Sprint_Projeto_Empresa_IdEmpresa(empresa.getIdEmpresa());
        Double progresso = ProgressoCalculator.execute(checkpoints);
        UsuarioSprintResponseDto responsavel = UsuarioMapper.toUsuarioSprintDto(diretor);
        return EmpresaMapper.toDto(empresa, responsavel, comImpedimento, progresso, orcamento);
    }

    public DashboardEmpresaResponseDto toDashboardResponse(Empresa empresa) {
        Usuario diretor = usuarioRepository.findDiretorByEmpresaId(empresa.getIdEmpresa());
        List<Area> areas = listarTarefasPorAreaUseCase.execute(empresa.getIdEmpresa());
        List<TarefaUsuario> usuarios = listarTarefasPorEmpresaUsuarioUseCase.execute(empresa.getIdEmpresa());
        Double orcamento = dashRepository.orcamentoTotal(empresa.getIdEmpresa());
        Integer projetos = dashRepository.totalProjetos(empresa.getIdEmpresa());
        Boolean comImpedimento = dashRepository.empresaComImpedimento(empresa.getIdEmpresa());
        var allResponse = listarFinanceiroPorEmpresaUseCase.execute(empresa.getIdEmpresa());
        var checkpoints = checkpointRepository.findAllByTarefa_Sprint_Projeto_Empresa_IdEmpresa(empresa.getIdEmpresa());
        Double progresso = ProgressoCalculator.execute(checkpoints);
        UsuarioSprintResponseDto responsavel = UsuarioMapper.toUsuarioSprintDto(diretor);
        return EmpresaMapper.toDashboard(empresa, responsavel, progresso, areas, usuarios, orcamento, projetos, comImpedimento, allResponse);
    }
}

