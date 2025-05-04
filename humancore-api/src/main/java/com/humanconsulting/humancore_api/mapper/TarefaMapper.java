package com.humanconsulting.humancore_api.mapper;

import com.humanconsulting.humancore_api.controller.dto.atualizar.tarefa.AtualizarGeralRequestDto;
import com.humanconsulting.humancore_api.controller.dto.request.TarefaRequestDto;
import com.humanconsulting.humancore_api.controller.dto.response.TarefaResponseDto;
import com.humanconsulting.humancore_api.model.Tarefa;

public class TarefaMapper {
    public static Tarefa toEntity(TarefaRequestDto tarefaRequestDto) {
        return Tarefa.builder()
                .descricao(tarefaRequestDto.getDescricao())
                .dtInicio(tarefaRequestDto.getDtInicio())
                .dtFim(tarefaRequestDto.getDtFim())
                .sprint(tarefaRequestDto.getSprint())
                .responsavel(tarefaRequestDto.getResponsavel())
                .build();
    }

    public static Tarefa toEntity(AtualizarGeralRequestDto atualizarTarefaRequestDto) {
        return Tarefa.builder()
                .descricao(atualizarTarefaRequestDto.getDescricao())
                .dtInicio(atualizarTarefaRequestDto.getDtInicio())
                .dtFim(atualizarTarefaRequestDto.getDtFim())
                .progresso(atualizarTarefaRequestDto.getProgresso())
                .responsavel(atualizarTarefaRequestDto.getResponsavel())
                .build();
    }

    public static TarefaResponseDto toDto(Tarefa tarefa) {
        return TarefaResponseDto.builder()
                .idTarefa(tarefa.getIdTarefa())
                .descricao(tarefa.getDescricao())
                .dtInicio(tarefa.getDtInicio())
                .dtFim(tarefa.getDtFim())
                .progresso(tarefa.getProgresso())
                .comImpedimento(tarefa.getComImpedimento())
                .sprint(tarefa.getSprint())
                .responsavel(tarefa.getResponsavel())
                .build();
    }
}
