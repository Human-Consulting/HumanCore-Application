package com.humanconsulting.humancore_api.infrastructure.configs.calendar;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.CalendarScopes;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;

@Component
public class GoogleCalendarConfig {

    public Calendar getCalendarService() throws IOException {
        InputStream in = getClass().getResourceAsStream("human-calendar-key.json");

        if (in == null) {
            throw new RuntimeException("Arquivo de credenciais não encontrado!");
        }

        GoogleCredential credential = GoogleCredential.fromStream(in)
                .createScoped(Collections.singleton(CalendarScopes.CALENDAR));

        return new Calendar.Builder(
                credential.getTransport(),
                credential.getJsonFactory(),
                credential)
                .setApplicationName("Human Calendar Integration")
                .build();
    }
}