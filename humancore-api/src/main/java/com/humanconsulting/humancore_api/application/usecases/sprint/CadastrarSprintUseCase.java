package com.humanconsulting.humancore_api.application.usecases.sprint;

import com.humanconsulting.humancore_api.application.usecases.sprint.mappers.SprintResponseMapper;
import com.humanconsulting.humancore_api.domain.entities.Sprint;
import com.humanconsulting.humancore_api.domain.exception.EntidadeConflitanteException;
import com.humanconsulting.humancore_api.domain.repositories.ProjetoRepository;
import com.humanconsulting.humancore_api.domain.repositories.SprintRepository;
import com.humanconsulting.humancore_api.domain.security.ValidarPermissao;
import com.humanconsulting.humancore_api.web.dtos.request.SprintRequestDto;
import com.humanconsulting.humancore_api.web.dtos.response.sprint.SprintResponseDto;
import com.humanconsulting.humancore_api.web.mappers.SprintMapper;

public class CadastrarSprintUseCase {
    private final ProjetoRepository projetoRepository;
    private final SprintRepository sprintRepository;
    private final SprintResponseMapper sprintResponseMapper;

    public CadastrarSprintUseCase(ProjetoRepository projetoRepository, SprintRepository sprintRepository, SprintResponseMapper sprintResponseMapper) {
        this.projetoRepository = projetoRepository;
        this.sprintRepository = sprintRepository;
        this.sprintResponseMapper = sprintResponseMapper;
    }

    public SprintResponseDto execute(SprintRequestDto sprintRequestDto) {
        ValidarPermissao.execute(sprintRequestDto.getPermissaoEditor(), "ADICIONAR_SPRINT");
        if (sprintRequestDto.getDtInicio().isAfter(sprintRequestDto.getDtFim()) || sprintRequestDto.getDtInicio().isEqual(sprintRequestDto.getDtFim())) {
            throw new EntidadeConflitanteException("Datas de início e fim conflitantes.");
        }
        Sprint sprint = sprintRepository.save(SprintMapper.toEntity(sprintRequestDto, projetoRepository.findById(sprintRequestDto.getFkProjeto()).get()));
        return sprintResponseMapper.toResponse(sprint, sprint.getIdSprint());
    }
}

