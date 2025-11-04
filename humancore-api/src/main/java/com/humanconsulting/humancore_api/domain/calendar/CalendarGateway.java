package com.humanconsulting.humancore_api.domain.calendar;

import java.io.IOException;

public interface CalendarGateway {
    void criarEvento(String titulo, String descricao) throws IOException;
}