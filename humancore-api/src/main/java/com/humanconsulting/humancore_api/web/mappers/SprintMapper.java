package com.humanconsulting.humancore_api.web.mappers;

import com.humanconsulting.humancore_api.domain.entities.Projeto;
import com.humanconsulting.humancore_api.domain.entities.Sprint;
import com.humanconsulting.humancore_api.web.dtos.atualizar.sprint.SprintAtualizarRequestDto;
import com.humanconsulting.humancore_api.web.dtos.request.SprintRequestDto;
import com.humanconsulting.humancore_api.web.dtos.response.projeto.ProjetoResponseLoginDto;
import com.humanconsulting.humancore_api.web.dtos.response.sprint.SprintResponseDto;
import com.humanconsulting.humancore_api.web.dtos.response.sprint.SprintResponseLoginDto;
import com.humanconsulting.humancore_api.web.dtos.response.tarefa.TarefaResponseDto;

import java.util.List;

public class SprintMapper {
    public static Sprint toEntity(SprintRequestDto sprintRequestDto, Projeto projeto) {
        Sprint sprint = new Sprint();
        sprint.setTitulo(sprintRequestDto.getTitulo());
        sprint.setDescricao(sprintRequestDto.getDescricao());
        sprint.setDtInicio(sprintRequestDto.getDtInicio());
        sprint.setDtFim(sprintRequestDto.getDtFim());
        sprint.setProjeto(projeto);
        return sprint;
    }

    public static Sprint toEntity(SprintAtualizarRequestDto sprintAtualizarRequestDto, Integer idSprint, Projeto projeto) {
        Sprint sprint = new Sprint();
        sprint.setIdSprint(idSprint);
        sprint.setTitulo(sprintAtualizarRequestDto.getTitulo());
        sprint.setDescricao(sprintAtualizarRequestDto.getDescricao());
        sprint.setDtInicio(sprintAtualizarRequestDto.getDtInicio());
        sprint.setDtFim(sprintAtualizarRequestDto.getDtFim());
        sprint.setProjeto(projeto);
        return sprint;
    }

    public static SprintResponseDto toDto(Sprint sprint, double progresso, boolean comImpedimento, List<TarefaResponseDto> tarefas) {
        return SprintResponseDto.builder()
                .idSprint(sprint.getIdSprint())
                .titulo(sprint.getTitulo())
                .descricao(sprint.getDescricao())
                .dtInicio(sprint.getDtInicio())
                .dtFim(sprint.getDtFim())
                .progresso(progresso)
                .comImpedimento(comImpedimento)
                .tarefas(tarefas)
                .build();
    }

    public static Sprint toEntity(SprintResponseDto dto) {
        Sprint sprint = new Sprint();
        sprint.setIdSprint(dto.getIdSprint());
        sprint.setTitulo(dto.getTitulo());
        sprint.setDescricao(dto.getDescricao());
        sprint.setDtInicio(dto.getDtInicio());
        sprint.setDtFim(dto.getDtFim());
        return sprint;
    }

    public static SprintResponseLoginDto toResponseLoginDto(Sprint sprint, ProjetoResponseLoginDto projeto) {
        return SprintResponseLoginDto.builder()
                .idSprint(sprint.getIdSprint())
                .titulo(sprint.getTitulo())
                .projeto(projeto)
                .build();
    }
}
