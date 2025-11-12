package com.humanconsulting.humancore_api.web.mappers;

import com.humanconsulting.humancore_api.domain.entities.Tarefa;
import com.humanconsulting.humancore_api.domain.entities.Sprint;
import com.humanconsulting.humancore_api.domain.entities.Usuario;
import com.humanconsulting.humancore_api.web.dtos.atualizar.tarefa.AtualizarGeralRequestDto;
import com.humanconsulting.humancore_api.web.dtos.request.TarefaRequestDto;
import com.humanconsulting.humancore_api.web.dtos.response.checkpoint.CheckpointResponseDto;
import com.humanconsulting.humancore_api.web.dtos.response.sprint.SprintResponseLoginDto;
import com.humanconsulting.humancore_api.web.dtos.response.tarefa.TarefaLoginResponseDto;
import com.humanconsulting.humancore_api.web.dtos.response.tarefa.TarefaResponseDto;

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
                .responsavel(tarefa.getResponsavel() != null ? UsuarioMapper.toUsuarioSprintDto(tarefa.getResponsavel()) : null)
                .checkpoints(checkpoints)
                .build();
    }

    public static TarefaLoginResponseDto toDtoLogin(Tarefa tarefa, List<CheckpointResponseDto> checkpoints, Double progresso, SprintResponseLoginDto sprint) {
        return TarefaLoginResponseDto.builder()
                .titulo(tarefa.getTitulo())
                .dtFim(tarefa.getDtFim())
                .progresso(progresso)
                .comImpedimento(tarefa.getComImpedimento())
                .sprint(sprint)
                .checkpoints(checkpoints)
                .build();
    }
}
