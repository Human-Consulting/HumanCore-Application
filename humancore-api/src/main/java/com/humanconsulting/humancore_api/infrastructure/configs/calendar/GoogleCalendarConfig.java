package com.humanconsulting.humancore_api.infrastructure.configs.calendar;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.calendar.Calendar;
import org.springframework.stereotype.Component;

import java.io.InputStream;

@Component
public class GoogleCalendarConfig {

    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private static com.google.api.client.http.HttpTransport HTTP_TRANSPORT;

    public Calendar getCalendarService() throws Exception {

        InputStream in = getClass().getResourceAsStream("human-calendar-key.json");
        if (in == null) {
            throw new RuntimeException("Arquivo de credenciais não encontrado!");
        }

        Credential credential = GoogleOAuthUtil.getCredentials();

        return new Calendar.Builder(HTTP_TRANSPORT, JSON_FACTORY, credential)
                .setApplicationName("HumanConsulting")
                .build();
    }
}