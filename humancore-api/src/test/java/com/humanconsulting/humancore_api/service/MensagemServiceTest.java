package com.humanconsulting.humancore_api.service;

import com.humanconsulting.humancore_api.velho.controller.dto.response.mensagem.MensagemResponseDto;
import com.humanconsulting.humancore_api.velho.mapper.MensagemMapper;
import com.humanconsulting.humancore_api.velho.model.Mensagem;
import com.humanconsulting.humancore_api.velho.repository.MensagemRepository;
import com.humanconsulting.humancore_api.velho.repository.SalaRepository;
import com.humanconsulting.humancore_api.velho.repository.UsuarioRepository;
import com.humanconsulting.humancore_api.velho.service.MensagemService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;

class MensagemServiceTest {
    private MensagemService mensagemService;
    private MensagemRepository mensagemRepository;
    private UsuarioRepository usuarioRepository;
    private SalaRepository salaRepository;

    @BeforeEach
    void setUp() {
        mensagemRepository = mock(MensagemRepository.class);
        usuarioRepository = mock(UsuarioRepository.class);
        salaRepository = mock(SalaRepository.class);
        mensagemService = new MensagemService();
        mensagemService.mensagemRepository = mensagemRepository;
        mensagemService.usuarioRepository = usuarioRepository;
        mensagemService.salaRepository = salaRepository;
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
        Mensagem mensagem = new Mensagem();
        MensagemResponseDto dtoEsperado = new MensagemResponseDto();

        try (MockedStatic<MensagemMapper> mapperMock = mockStatic(MensagemMapper.class)) {
            mapperMock.when(() -> MensagemMapper.toDto(mensagem)).thenReturn(dtoEsperado);

            // Act
            MensagemResponseDto dto = mensagemService.passarParaResponse(mensagem);

            // Assert
            assertSame(dtoEsperado, dto);
        }
    }

    @Test
    void passarParaResponse_deveRetornarNuloQuandoMensagemForNula() {
        // Arrange
        try (MockedStatic<MensagemMapper> mapperMock = mockStatic(MensagemMapper.class)) {
            mapperMock.when(() -> MensagemMapper.toDto(null)).thenReturn(null);

            // Act
            MensagemResponseDto dto = mensagemService.passarParaResponse(null);

            // Assert
            assertNull(dto);
        }
    }
}