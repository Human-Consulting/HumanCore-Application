package com.humanconsulting.humancore_api.novo.application.usecases.sprint;

import com.humanconsulting.humancore_api.novo.application.usecases.sprint.mappers.SprintResponseMapper;
import com.humanconsulting.humancore_api.novo.domain.repositories.ProjetoRepository;
import com.humanconsulting.humancore_api.novo.domain.repositories.SprintRepository;
import com.humanconsulting.humancore_api.novo.web.dtos.request.SprintRequestDto;
import com.humanconsulting.humancore_api.novo.web.dtos.response.sprint.SprintResponseDto;
import com.humanconsulting.humancore_api.novo.web.mappers.SprintMapper;

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
        PermissaoValidator.validarPermissao(sprintRequestDto.getPermissaoEditor(), "ADICIONAR_SPRINT");
        if (sprintRequestDto.getDtInicio().isAfter(sprintRequestDto.getDtFim()) || sprintRequestDto.getDtInicio().isEqual(sprintRequestDto.getDtFim())) {
            throw new EntidadeConflitanteException("Datas de início e fim conflitantes.");
        }
        Sprint sprint = sprintRepository.save(SprintMapper.toEntity(sprintRequestDto, projetoRepository.findById(sprintRequestDto.getFkProjeto()).get()));
        return sprintResponseMapper.toResponse(sprint, sprint.getIdSprint());
    }
}

