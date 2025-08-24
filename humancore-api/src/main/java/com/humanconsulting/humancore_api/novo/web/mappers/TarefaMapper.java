package com.humanconsulting.humancore_api.novo.web.mappers;

import com.humanconsulting.humancore_api.velho.controller.dto.atualizar.tarefa.AtualizarGeralRequestDto;
import com.humanconsulting.humancore_api.velho.controller.dto.request.TarefaRequestDto;
import com.humanconsulting.humancore_api.velho.controller.dto.response.checkpoint.CheckpointResponseDto;
import com.humanconsulting.humancore_api.velho.controller.dto.response.tarefa.TarefaResponseDto;
import com.humanconsulting.humancore_api.velho.model.Sprint;
import com.humanconsulting.humancore_api.velho.model.Tarefa;
import com.humanconsulting.humancore_api.velho.model.Usuario;

import java.util.List;

public class TarefaMapper {
    public static Tarefa toEntity(TarefaRequestDto tarefaRequestDto, Sprint sprint, Usuario responsavel) {
        return Tarefa.builder()
                .titulo(tarefaRequestDto.getTitulo())
                .descricao(tarefaRequestDto.getDescricao())
                .dtInicio(tarefaRequestDto.getDtInicio())
                .dtFim(tarefaRequestDto.getDtFim())
                .comImpedimento(false)
                .comentario(null)
                .sprint(sprint)
                .responsavel(responsavel)
                .build();
    }

    public static Tarefa toEntity(AtualizarGeralRequestDto atualizarTarefaRequestDto, Integer idTarefa, Sprint sprint, Usuario usuario) {
        return Tarefa.builder()
                .idTarefa(idTarefa)
                .titulo(atualizarTarefaRequestDto.getTitulo())
                .descricao(atualizarTarefaRequestDto.getDescricao())
                .dtInicio(atualizarTarefaRequestDto.getDtInicio())
                .dtFim(atualizarTarefaRequestDto.getDtFim())
                .comImpedimento(atualizarTarefaRequestDto.getComImpedimento())
                .comentario(atualizarTarefaRequestDto.getComentario())
                .sprint(sprint)
                .responsavel(usuario)
                .build();
    }

    public static TarefaResponseDto toDto(Tarefa tarefa, List<CheckpointResponseDto> checkpoints, Double progresso) {
        return TarefaResponseDto.builder()
                .idTarefa(tarefa.getIdTarefa())
                .titulo(tarefa.getTitulo())
                .descricao(tarefa.getDescricao())
                .dtInicio(tarefa.getDtInicio())
                .dtFim(tarefa.getDtFim())
                .progresso(progresso)
                .comImpedimento(tarefa.getComImpedimento())
                .comentario(tarefa.getComentario())
                .sprint(tarefa.getSprint())
                .responsavel(tarefa.getResponsavel())
                .checkpoints(checkpoints)
                .build();
    }
}
