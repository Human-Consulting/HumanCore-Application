package com.humanconsulting.humancore_api.domain.calendar;

import com.humanconsulting.humancore_api.domain.entities.Tarefa;
import com.humanconsulting.humancore_api.web.dtos.request.TarefaRequestDto;

import java.io.IOException;

public interface CalendarGateway {
    String criarEvento(TarefaRequestDto tarefaRequestDto) throws Exception;
    void atualizarEvento(String eventId, Tarefa tarefa) throws Exception;
}