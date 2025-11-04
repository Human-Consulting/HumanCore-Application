package com.humanconsulting.humancore_api.infrastructure.configs.calendar;

import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventDateTime;
import com.humanconsulting.humancore_api.domain.calendar.CalendarGateway;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Date;

@Service
public class GoogleCalendarService implements CalendarGateway {

    private final GoogleCalendarConfig googleCalendarConfig;

    public GoogleCalendarService(GoogleCalendarConfig googleCalendarConfig) {
        this.googleCalendarConfig = googleCalendarConfig;
    }

    @Override
    public void criarEvento(String titulo, String descricao) throws IOException {
        var service = googleCalendarConfig.getCalendarService();

        Event event = new Event()
                .setSummary(titulo)
                .setDescription(descricao);

        Date startDate = new Date(System.currentTimeMillis() + 3600000);
        Date endDate = new Date(System.currentTimeMillis() + 7200000);

        event.setStart(new EventDateTime().setDateTime(new com.google.api.client.util.DateTime(startDate)));
        event.setEnd(new EventDateTime().setDateTime(new com.google.api.client.util.DateTime(endDate)));

        var createdEvent = service.events().insert("primary", event).execute();

        System.out.println("✅ Evento criado: " + createdEvent.getHtmlLink());
    }
}