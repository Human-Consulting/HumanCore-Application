package com.humanconsulting.humancore_api.mapper;

import com.humanconsulting.humancore_api.controller.dto.request.TarefaRequestDto;
import com.humanconsulting.humancore_api.controller.dto.response.TarefaResponseDto;
import com.humanconsulting.humancore_api.model.Tarefa;

public class TarefaMapper {
    public static Tarefa toEntity(TarefaRequestDto tarefaRequestDto) {
        return new Tarefa(tarefaRequestDto.getDescricao(), tarefaRequestDto.getDtInicio(), tarefaRequestDto.getDtFim(), tarefaRequestDto.getFkSprint(), tarefaRequestDto.getFkResponsavel());
    }

    public static TarefaResponseDto toDto(Tarefa tarefa, String nomeResponsavel, String areaResponsavel) {
        return new TarefaResponseDto(tarefa.getIdTarefa(), tarefa.getDescricao(), tarefa.getDtInicio(), tarefa.getDtFim(), tarefa.getProgresso(), tarefa.getComImpedimento(), tarefa.getFkSprint(), tarefa.getFkResponsavel(), nomeResponsavel, areaResponsavel);
    }
}
