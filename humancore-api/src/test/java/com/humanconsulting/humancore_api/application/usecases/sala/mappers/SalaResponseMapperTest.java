package com.humanconsulting.humancore_api.application.usecases.sala.mappers;

import com.humanconsulting.humancore_api.domain.entities.Sala;
import com.humanconsulting.humancore_api.web.dtos.response.sala.SalaResponseDto;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SalaResponseMapperTest {

    private final SalaResponseMapper mapper = new SalaResponseMapper();

    @Test
    void testToResponse_Feliz() {
        // Cenário feliz: sala preenchida corretamente
        Sala sala = new Sala();
        sala.setIdSala(1);
        sala.setNome("Sala de Reunião");

        SalaResponseDto response = mapper.toResponse(sala);

        assertNotNull(response);
        assertEquals(1, response.getIdSala());
        assertEquals("Sala de Reunião", response.getNome());
    }

    @Test
    void testToResponse_Triste_SalaNula() {
        assertThrows(NullPointerException.class, () -> {
            mapper.toResponse(null);
        });
    }

    @Test
    void testToResponse_Triste_SalaSemNome() {
        Sala sala = new Sala();
        sala.setIdSala(2);

        SalaResponseDto response = mapper.toResponse(sala);

        assertNotNull(response);
        assertEquals(2, response.getIdSala());
        assertNull(response.getNome()); // se o mapper não preencher nome nulo
    }
}
