package com.humanconsulting.humancore_api.novo.web.mappers;

import com.humanconsulting.humancore_api.novo.domain.entities.Tarefa;
import com.humanconsulting.humancore_api.novo.domain.entities.Sprint;
import com.humanconsulting.humancore_api.novo.domain.entities.Usuario;
import com.humanconsulting.humancore_api.novo.web.dtos.atualizar.tarefa.AtualizarGeralRequestDto;
import com.humanconsulting.humancore_api.novo.web.dtos.request.TarefaRequestDto;
import com.humanconsulting.humancore_api.novo.web.dtos.response.checkpoint.CheckpointResponseDto;
import com.humanconsulting.humancore_api.novo.web.dtos.response.tarefa.TarefaResponseDto;

import java.util.List;

public class TarefaMapper {
    public static Tarefa toEntity(TarefaRequestDto tarefaRequestDto, Sprint sprint, Usuario responsavel) {
        Tarefa tarefa = new Tarefa();
        tarefa.setTitulo(tarefaRequestDto.getTitulo());
        tarefa.setDescricao(tarefaRequestDto.getDescricao());
        tarefa.setDtInicio(tarefaRequestDto.getDtInicio());
        tarefa.setDtFim(tarefaRequestDto.getDtFim());
        tarefa.setComImpedimento(false);
        tarefa.setComentario(null);
        tarefa.setSprint(sprint);
        tarefa.setResponsavel(responsavel);
        return tarefa;
    }

    public static Tarefa toEntity(AtualizarGeralRequestDto atualizarTarefaRequestDto, Integer idTarefa, Sprint sprint, Usuario usuario) {
        Tarefa tarefa = new Tarefa();
        tarefa.setIdTarefa(idTarefa);
        tarefa.setTitulo(atualizarTarefaRequestDto.getTitulo());
        tarefa.setDescricao(atualizarTarefaRequestDto.getDescricao());
        tarefa.setDtInicio(atualizarTarefaRequestDto.getDtInicio());
        tarefa.setDtFim(atualizarTarefaRequestDto.getDtFim());
        tarefa.setComImpedimento(atualizarTarefaRequestDto.getComImpedimento());
        tarefa.setComentario(atualizarTarefaRequestDto.getComentario());
        tarefa.setSprint(sprint);
        tarefa.setResponsavel(usuario);
        return tarefa;
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
