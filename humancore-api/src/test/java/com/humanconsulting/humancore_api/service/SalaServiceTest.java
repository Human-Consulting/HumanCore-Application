package com.humanconsulting.humancore_api.service;

import com.humanconsulting.humancore_api.velho.controller.dto.response.sala.SalaResponseDto;
import com.humanconsulting.humancore_api.velho.mapper.SalaMapper;
import com.humanconsulting.humancore_api.velho.model.Sala;
import com.humanconsulting.humancore_api.velho.repository.SalaRepository;
import com.humanconsulting.humancore_api.velho.repository.UsuarioRepository;
import com.humanconsulting.humancore_api.velho.service.SalaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;

class SalaServiceTest {
    private SalaService salaService;
    private SalaRepository salaRepository;
    private UsuarioRepository usuarioRepository;

    @BeforeEach
    void setUp() {
        salaRepository = mock(SalaRepository.class);
        usuarioRepository = mock(UsuarioRepository.class);
        salaService = new SalaService();
        salaService.usuarioRepository = usuarioRepository;
        salaService.salaRepository = salaRepository;
    }

    @Test
    void cadastrar() {
    }

    @Test
    void buscarPorId() {
    }

    @Test
    void listar() {
    }

    @Test
    void deletar() {
    }

    @Test
    void atualizar() {
    }

    @Test
    void passarParaResponse_deveRetornarDtoCorretamente() {
        // Arrange
        Sala sala = new Sala();
        SalaResponseDto dtoEsperado = new SalaResponseDto();

        try (MockedStatic<SalaMapper> mapperMock = mockStatic(SalaMapper.class)) {
            mapperMock.when(() -> SalaMapper.toDto(sala)).thenReturn(dtoEsperado);

            // Act
            SalaResponseDto dto = salaService.passarParaResponse(sala);

            // Assert
            assertSame(dtoEsperado, dto);
        }
    }

    @Test
    void passarParaResponse_deveRetornarNuloQuandoSalaForNula() {
        // Arrange
        try (MockedStatic<SalaMapper> mapperMock = mockStatic(SalaMapper.class)) {
            mapperMock.when(() -> SalaMapper.toDto(null)).thenReturn(null);

            // Act
            SalaResponseDto dto = salaService.passarParaResponse(null);

            // Assert
            assertNull(dto);
        }
    }
}