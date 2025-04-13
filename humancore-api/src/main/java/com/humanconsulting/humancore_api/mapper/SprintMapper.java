package com.humanconsulting.humancore_api.mapper;

import com.humanconsulting.humancore_api.controller.dto.request.SprintRequestDto;
import com.humanconsulting.humancore_api.controller.dto.response.EntregaResponseDto;
import com.humanconsulting.humancore_api.controller.dto.response.SprintResponseDto;
import com.humanconsulting.humancore_api.model.Sprint;

import java.util.List;

public class SprintMapper {
    public static Sprint toEntity(SprintRequestDto sprintRequestDto) {
        return new Sprint(null, sprintRequestDto.getDescricao(), sprintRequestDto.getDtInicio(), sprintRequestDto.getDtFim(), sprintRequestDto.getFkProjeto());
    }

    public static SprintResponseDto toDto(Sprint sprint, double progresso, boolean comImpedimento, List<EntregaResponseDto> entregas) {
        return new SprintResponseDto(sprint.getIdSprint(), sprint.getDescricao(), sprint.getDtInicio(), sprint.getDtFim(), progresso, comImpedimento, sprint.getFkProjeto(), entregas);
    }
}
