package com.humanconsulting.humancore_api.application.usecases.sala;

import com.humanconsulting.humancore_api.application.usecases.sala.mappers.SalaResponseMapper;
import com.humanconsulting.humancore_api.domain.entities.Sala;
import com.humanconsulting.humancore_api.domain.exception.EntidadeSemRetornoException;
import com.humanconsulting.humancore_api.domain.repositories.SalaRepository;
import com.humanconsulting.humancore_api.web.dtos.response.sala.SalaResponseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ListarSalasUseCaseTest {

    private SalaRepository salaRepository;
    private SalaResponseMapper salaResponseMapper;
    private ListarSalasUseCase listarSalasUseCase;

    @BeforeEach
    void setUp() {
        salaRepository = mock(SalaRepository.class);
        salaResponseMapper = mock(SalaResponseMapper.class);
        listarSalasUseCase = new ListarSalasUseCase(salaRepository, salaResponseMapper);
    }

    @Test
    void execute_ShouldReturnSalaResponseDtos_WhenSalasExistem() {
        // Arrange
        Sala sala1 = new Sala();
        sala1.setIdSala(1);
        sala1.setNome("Sala A");

        Sala sala2 = new Sala();
        sala2.setIdSala(2);
        sala2.setNome("Sala B");

        SalaResponseDto dto1 = new SalaResponseDto();
        dto1.setIdSala(1);
        dto1.setNome("Sala A");

        SalaResponseDto dto2 = new SalaResponseDto();
        dto2.setIdSala(2);
        dto2.setNome("Sala B");

        when(salaRepository.findAll()).thenReturn(List.of(sala1, sala2));
        when(salaResponseMapper.toResponse(sala1)).thenReturn(dto1);
        when(salaResponseMapper.toResponse(sala2)).thenReturn(dto2);

        // Act
        List<SalaResponseDto> result = listarSalasUseCase.execute();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Sala A", result.get(0).getNome());
        assertEquals("Sala B", result.get(1).getNome());

        verify(salaRepository, times(1)).findAll();
        verify(salaResponseMapper, times(2)).toResponse(any(Sala.class));
    }

    @Test
    void execute_ShouldThrowException_WhenNoSalasExistem() {
        // Arrange
        when(salaRepository.findAll()).thenReturn(List.of());

        // Act & Assert
        EntidadeSemRetornoException exception = assertThrows(
                EntidadeSemRetornoException.class,
                () -> listarSalasUseCase.execute()
        );

        assertEquals("Nenhuma sala registrada", exception.getMessage());
        verify(salaRepository, times(1)).findAll();
        verify(salaResponseMapper, never()).toResponse(any());
    }
}
