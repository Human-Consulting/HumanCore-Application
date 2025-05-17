package com.humanconsulting.humancore_api.mapper;

import com.humanconsulting.humancore_api.controller.dto.atualizar.sprint.SprintAtualizarRequestDto;
import com.humanconsulting.humancore_api.controller.dto.request.SprintRequestDto;
import com.humanconsulting.humancore_api.controller.dto.response.tarefa.TarefaResponseDto;
import com.humanconsulting.humancore_api.controller.dto.response.sprint.SprintResponseDto;
import com.humanconsulting.humancore_api.model.Projeto;
import com.humanconsulting.humancore_api.model.Sprint;

import java.util.List;

public class SprintMapper {
    public static Sprint toEntity(SprintRequestDto sprintRequestDto, Projeto projeto) {
        return Sprint.builder()
                .titulo(sprintRequestDto.getTitulo())
                .descricao(sprintRequestDto.getDescricao())
                .dtInicio(sprintRequestDto.getDtInicio())
                .dtFim(sprintRequestDto.getDtFim())
                .projeto(projeto)
                .build();
    }

    public static Sprint toEntity(SprintAtualizarRequestDto sprintAtualizarRequestDto, Integer idSprint, Projeto projeto) {
        return Sprint.builder()
                .idSprint(idSprint)
                .titulo(sprintAtualizarRequestDto.getTitulo())
                .descricao(sprintAtualizarRequestDto.getDescricao())
                .dtInicio(sprintAtualizarRequestDto.getDtInicio())
                .dtFim(sprintAtualizarRequestDto.getDtFim())
                .projeto(projeto)
                .build();
    }

    public static SprintResponseDto toDto(Sprint sprint, double progresso, boolean comImpedimento, List<TarefaResponseDto> entregas) {
        return SprintResponseDto.builder()
                .idSprint(sprint.getIdSprint())
                .titulo(sprint.getTitulo())
                .descricao(sprint.getDescricao())
                .dtInicio(sprint.getDtInicio())
                .dtFim(sprint.getDtFim())
                .progresso(progresso)
                .comImpedimento(comImpedimento)
                .projeto(sprint.getProjeto())
                .tarefas(entregas)
                .build();
    }
}
