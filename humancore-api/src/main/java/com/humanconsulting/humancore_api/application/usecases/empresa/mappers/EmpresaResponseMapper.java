package com.humanconsulting.humancore_api.application.usecases.empresa.mappers;

import com.humanconsulting.humancore_api.domain.entities.Checkpoint;
import com.humanconsulting.humancore_api.domain.entities.Empresa;
import com.humanconsulting.humancore_api.domain.repositories.CheckpointRepository;
import com.humanconsulting.humancore_api.domain.repositories.DashboardEmpresaRepository;
import com.humanconsulting.humancore_api.domain.repositories.UsuarioRepository;
import com.humanconsulting.humancore_api.domain.utils.ProgressoCalculator;
import com.humanconsulting.humancore_api.web.dtos.response.empresa.EmpresaResponseDto;
import com.humanconsulting.humancore_api.web.mappers.EmpresaMapper;
import java.util.List;
import java.util.Optional;

public class EmpresaResponseMapper {
    private final UsuarioRepository usuarioRepository;
    private final DashboardEmpresaRepository dashRepository;
    private final CheckpointRepository checkpointRepository;

    public EmpresaResponseMapper(UsuarioRepository usuarioRepository,
                                DashboardEmpresaRepository dashRepository,
                                CheckpointRepository checkpointRepository) {
        this.usuarioRepository = usuarioRepository;
        this.dashRepository = dashRepository;
        this.checkpointRepository = checkpointRepository;
    }

    public EmpresaResponseDto toResponse(Empresa empresa) {
        Optional<String> nomeDiretor = usuarioRepository.findDiretorByEmpresaId(empresa.getIdEmpresa());
        Boolean comImpedimento = dashRepository.empresaComImpedimento(empresa.getIdEmpresa());
        Double orcamento = dashRepository.orcamentoTotal(empresa.getIdEmpresa());
        List<Checkpoint> checkpoints = checkpointRepository.findAllByTarefa_Sprint_Projeto_Empresa_IdEmpresa(empresa.getIdEmpresa());
        Double progresso = ProgressoCalculator.execute(checkpoints);
        return EmpresaMapper.toDto(empresa, nomeDiretor.orElse(null), comImpedimento, progresso, orcamento);
    }
}

