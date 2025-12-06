package com.humanconsulting.humancore_api.application.usecases.mensagem;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.humanconsulting.humancore_api.domain.entities.Mensagem;
import com.humanconsulting.humancore_api.domain.entities.Sala;
import com.humanconsulting.humancore_api.domain.entities.Usuario;
import com.humanconsulting.humancore_api.domain.exception.EntidadeSemRetornoException;
import com.humanconsulting.humancore_api.domain.repositories.MensagemRepository;
import com.humanconsulting.humancore_api.web.dtos.response.chat.ChatMensagemUnificadaDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

public class ListarMensagensUseCaseTest {

    @Mock
    private MensagemRepository mensagemRepository;

    @InjectMocks
    private ListarMensagensUseCase listarMensagensUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testExecute_SuccessfulListing() {
        // Arrange
        Sala sala = new Sala();
        sala.setIdSala(1); // Define um ID válido para a sala

        Usuario usuario = new Usuario();
        usuario.setIdUsuario(1); // Define um ID válido para o usuário

        Mensagem mensagem1 = new Mensagem();
        mensagem1.setSala(sala);
        mensagem1.setUsuario(usuario); // Associa o usuário à mensagem

        Mensagem mensagem2 = new Mensagem();
        mensagem2.setSala(sala);
        mensagem2.setUsuario(usuario); // Associa o mesmo usuário à segunda mensagem

        List<Mensagem> mensagens = Arrays.asList(mensagem1, mensagem2);

        when(mensagemRepository.findAll()).thenReturn(mensagens);

        // Act
        List<ChatMensagemUnificadaDto> result = listarMensagensUseCase.execute();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(mensagemRepository, times(1)).findAll();
    }

    @Test
    void testExecute_NoMessagesFound() {
        // Arrange
        when(mensagemRepository.findAll()).thenReturn(List.of());

        // Act & Assert
        EntidadeSemRetornoException exception = assertThrows(
                EntidadeSemRetornoException.class,
                () -> listarMensagensUseCase.execute()
        );

        assertEquals("Nenhuma mensagem encontrada para a sala.", exception.getMessage());
        verify(mensagemRepository, times(1)).findAll();
    }
}