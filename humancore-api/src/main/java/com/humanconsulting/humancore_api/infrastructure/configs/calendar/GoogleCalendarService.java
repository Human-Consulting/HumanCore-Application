package com.humanconsulting.humancore_api.infrastructure.configs.calendar;

import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventDateTime;
import com.humanconsulting.humancore_api.domain.calendar.CalendarGateway;
import com.humanconsulting.humancore_api.domain.entities.Tarefa;
import com.humanconsulting.humancore_api.web.dtos.request.TarefaRequestDto;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@Service
public class GoogleCalendarService implements CalendarGateway {

    private final GoogleCalendarConfig googleCalendarConfig;

    public GoogleCalendarService(GoogleCalendarConfig googleCalendarConfig) {
        this.googleCalendarConfig = googleCalendarConfig;
    }

    @Override
    public String criarEvento(TarefaRequestDto tarefaRequestDto) throws Exception {
        var service = googleCalendarConfig.getCalendarService();

        Event event = new Event()
                .setSummary(tarefaRequestDto.getTitulo())
                .setDescription(tarefaRequestDto.getDescricao());

        LocalDate inicio = tarefaRequestDto.getDtInicio();
        LocalDate fim = tarefaRequestDto.getDtFim();

        LocalDateTime inicioDateTime = inicio.atStartOfDay();
        LocalDateTime fimDateTime = fim.atStartOfDay().plusDays(1);

        ZonedDateTime inicioZoned = inicioDateTime.atZone(ZoneId.systemDefault());
        ZonedDateTime fimZoned = fimDateTime.atZone(ZoneId.systemDefault());

        EventDateTime start = new EventDateTime()
                .setDateTime(new DateTime(inicioZoned.toInstant().toEpochMilli()));

        EventDateTime end = new EventDateTime()
                .setDateTime(new DateTime(fimZoned.toInstant().toEpochMilli()));

        event.setStart(start);
        event.setEnd(end);

        var createdEvent = service.events().insert("primary", event).execute();

        System.out.println("✅ Evento criado: " + createdEvent.getHtmlLink());

        return createdEvent.getId();
    }

    public void atualizarEvento(String eventId, Tarefa tarefa) throws Exception {
        var service = googleCalendarConfig.getCalendarService();

        Event event = service.events().get("primary", eventId).execute();

        LocalDate inicio = tarefa.getDtInicio();
        LocalDate fim = tarefa.getDtFim();

        LocalDateTime inicioDateTime = inicio.atStartOfDay();
        LocalDateTime fimDateTime = fim.atStartOfDay().plusDays(1);

        ZonedDateTime inicioZoned = inicioDateTime.atZone(ZoneId.systemDefault());
        ZonedDateTime fimZoned = fimDateTime.atZone(ZoneId.systemDefault());

        EventDateTime start = new EventDateTime()
                .setDateTime(new DateTime(inicioZoned.toInstant().toEpochMilli()));

        EventDateTime end = new EventDateTime()
                .setDateTime(new DateTime(fimZoned.toInstant().toEpochMilli()));

        event.setSummary(tarefa.getTitulo());
        event.setDescription(tarefa.getDescricao());
        event.setStart(start);
        event.setEnd(end);

        Event updatedEvent = service.events().update("primary", event.getId(), event).execute();

        System.out.println("✅ Evento atualizado: " + updatedEvent.getHtmlLink());
    }
}