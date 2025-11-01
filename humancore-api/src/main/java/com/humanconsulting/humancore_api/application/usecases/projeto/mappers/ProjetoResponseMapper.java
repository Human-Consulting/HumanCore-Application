package com.humanconsulting.humancore_api.application.usecases.projeto.mappers;

import com.humanconsulting.humancore_api.domain.entities.Checkpoint;
import com.humanconsulting.humancore_api.domain.entities.Projeto;
import com.humanconsulting.humancore_api.domain.repositories.CheckpointRepository;
import com.humanconsulting.humancore_api.domain.repositories.SprintRepository;
import com.humanconsulting.humancore_api.domain.repositories.TarefaRepository;
import com.humanconsulting.humancore_api.domain.utils.ProgressoCalculator;
import com.humanconsulting.humancore_api.web.dtos.response.projeto.ProjetoResponseDto;
import com.humanconsulting.humancore_api.web.mappers.ProjetoMapper;

import java.util.List;

public class ProjetoResponseMapper {
    private final TarefaRepository tarefaRepository;
    private final CheckpointRepository checkpointRepository;

    public ProjetoResponseMapper(TarefaRepository tarefaRepository, CheckpointRepository checkpointRepository) {
        this.tarefaRepository = tarefaRepository;
        this.checkpointRepository = checkpointRepository;
    }

    public ProjetoResponseDto toResponse(Projeto projeto) {
        boolean comImpedimento = tarefaRepository.existsImpedimentoByProjeto(projeto.getIdProjeto());
        List<Checkpoint> checkpoints = checkpointRepository.findAllByTarefa_Sprint_Projeto_IdProjeto(projeto.getIdProjeto());
        Double progresso = ProgressoCalculator.execute(checkpoints);
        return ProjetoMapper.toDto(projeto, progresso, comImpedimento);
    }

    public ProjetoResponseDto toResponseKpi(Projeto projeto) {
        boolean comImpedimento = tarefaRepository.existsImpedimentoByProjeto(projeto.getIdProjeto());
        List<Checkpoint> checkpoints = checkpointRepository.findAllByTarefa_Sprint_Projeto_IdProjeto(projeto.getIdProjeto());
        Double progresso = ProgressoCalculator.execute(checkpoints);
        return ProjetoMapper.toDto(projeto, progresso, comImpedimento);
    }

    public ProjetoResponseDto toResponseMenuRapido(Projeto projeto) {
        boolean comImpedimento = tarefaRepository.existsImpedimentoByProjeto(projeto.getIdProjeto());
        List<Checkpoint> checkpoints = checkpointRepository.findAllByTarefa_Sprint_Projeto_IdProjeto(projeto.getIdProjeto());
        Double progresso = ProgressoCalculator.execute(checkpoints);
        return ProjetoMapper.toDto(projeto, progresso, comImpedimento);
    }
}

