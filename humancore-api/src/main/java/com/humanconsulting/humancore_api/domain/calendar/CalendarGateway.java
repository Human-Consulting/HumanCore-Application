package com.humanconsulting.humancore_api.domain.calendar;

import com.humanconsulting.humancore_api.web.dtos.request.TarefaRequestDto;

import java.io.IOException;

public interface CalendarGateway {
    String criarEvento(TarefaRequestDto tarefaRequestDto, String emailResponsavel) throws Exception;
    void atualizarEvento(String eventId, String novoTitulo, String novaDescricao) throws Exception;
}