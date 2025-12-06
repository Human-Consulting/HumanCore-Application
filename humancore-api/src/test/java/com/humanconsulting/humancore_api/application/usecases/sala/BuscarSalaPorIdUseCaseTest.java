package com.humanconsulting.humancore_api.application.usecases.sala;

import com.humanconsulting.humancore_api.domain.entities.Sala;
import com.humanconsulting.humancore_api.domain.exception.EntidadeNaoEncontradaException;
import com.humanconsulting.humancore_api.domain.repositories.SalaRepository;
import com.humanconsulting.humancore_api.web.dtos.response.sala.SalaResponseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BuscarSalaPorIdUseCaseTest {

    private SalaRepository salaRepository;
    private BuscarSalaPorIdUseCase buscarSalaPorIdUseCase;

    @BeforeEach
    void setUp() {
        salaRepository = mock(SalaRepository.class);
        buscarSalaPorIdUseCase = new BuscarSalaPorIdUseCase(salaRepository);
    }

    @Test
    void execute_ShouldReturnSalaResponseDto_WhenSalaExists() {
        // Arrange
        Integer idSala = 1;
        Sala sala = new Sala();
        sala.setIdSala(idSala);
        sala.setNome("Sala Teste");
        sala.setUrlImagem("img.png");

        when(salaRepository.findById(idSala)).thenReturn(Optional.of(sala));

        // Act
        SalaResponseDto result = buscarSalaPorIdUseCase.execute(idSala);

        // Assert
        assertNotNull(result);
        assertEquals(idSala, result.getIdSala());
        assertEquals("Sala Teste", result.getNome());
        verify(salaRepository, times(1)).findById(idSala);
    }

    @Test
    void execute_ShouldThrowException_WhenSalaNotFound() {
        // Arrange
        Integer idSala = 99;
        when(salaRepository.findById(idSala)).thenReturn(Optional.empty());

        // Act & Assert
        EntidadeNaoEncontradaException exception = assertThrows(
                EntidadeNaoEncontradaException.class,
                () -> buscarSalaPorIdUseCase.execute(idSala)
        );

        assertEquals("SalaEntity não encontrada.", exception.getMessage());
        verify(salaRepository, times(1)).findById(idSala);
    }
}
