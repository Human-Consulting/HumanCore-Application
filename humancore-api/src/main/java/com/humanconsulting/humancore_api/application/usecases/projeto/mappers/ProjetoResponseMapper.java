package com.humanconsulting.humancore_api.application.usecases.projeto.mappers;

import com.humanconsulting.humancore_api.domain.entities.Checkpoint;
import com.humanconsulting.humancore_api.domain.entities.Projeto;
import com.humanconsulting.humancore_api.domain.entities.Sprint;
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
    private final SprintRepository sprintRepository;

    public ProjetoResponseMapper(TarefaRepository tarefaRepository, CheckpointRepository checkpointRepository, SprintRepository sprintRepository) {
        this.tarefaRepository = tarefaRepository;
        this.checkpointRepository = checkpointRepository;
        this.sprintRepository = sprintRepository;
    }

    public ProjetoResponseDto toResponse(Projeto projeto, Integer fkResponsavel, Integer idProjeto) {
        List<Sprint> sprints = sprintRepository.findAll();
        boolean comImpedimento = tarefaRepository.existsImpedimentoByProjeto(idProjeto);
        List<Checkpoint> checkpoints = checkpointRepository.findAllByTarefa_Sprint_Projeto_IdProjeto(idProjeto);
        Double progresso = ProgressoCalculator.execute(checkpoints);
        return ProjetoMapper.toDto(projeto, progresso, comImpedimento);
    }
}

