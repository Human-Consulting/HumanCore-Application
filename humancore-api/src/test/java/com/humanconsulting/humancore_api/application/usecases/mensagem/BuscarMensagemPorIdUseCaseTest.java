package com.humanconsulting.humancore_api.application.usecases.mensagem;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import com.humanconsulting.humancore_api.domain.entities.Mensagem;
import com.humanconsulting.humancore_api.domain.entities.Sala;
import com.humanconsulting.humancore_api.domain.entities.Usuario;
import com.humanconsulting.humancore_api.domain.repositories.MensagemRepository;
import com.humanconsulting.humancore_api.domain.exception.EntidadeNaoEncontradaException;
import com.humanconsulting.humancore_api.web.dtos.response.mensagem.MensagemResponseDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

@ExtendWith(MockitoExtension.class)
public class BuscarMensagemPorIdUseCaseTest {

    @Mock
    private MensagemRepository mensagemRepository;

    @InjectMocks
    private BuscarMensagemPorIdUseCase buscarMensagemPorIdUseCase;

    @Test
    void deveBuscarMensagemPorIdComSucesso() {
        // Arrange
        Integer idMensagem = 1;

        Mensagem mensagem = new Mensagem();
        mensagem.setIdMensagem(idMensagem);
        mensagem.setConteudo("Conteúdo da mensagem");
        mensagem.setUsuario(mock(Usuario.class));
        mensagem.setSala(mock(Sala.class));

        when(mensagemRepository.findById(idMensagem)).thenReturn(List.of(mensagem));

        // Act
        MensagemResponseDto resultado = buscarMensagemPorIdUseCase.execute(idMensagem);

        // Assert
        assertEquals(mensagem.getIdMensagem(), resultado.getIdMensagem());
        assertEquals(mensagem.getConteudo(), resultado.getConteudo());
        verify(mensagemRepository, times(1)).findById(idMensagem);
    }

    @Test
    void deveLancarExcecaoQuandoMensagemNaoEncontrada() {
        // Arrange
        Integer idMensagem = 999;

        when(mensagemRepository.findById(idMensagem)).thenReturn(List.of());

        // Act & Assert
        assertThrows(EntidadeNaoEncontradaException.class, () -> {
            buscarMensagemPorIdUseCase.execute(idMensagem);
        });

        verify(mensagemRepository, times(1)).findById(idMensagem);
    }
}

