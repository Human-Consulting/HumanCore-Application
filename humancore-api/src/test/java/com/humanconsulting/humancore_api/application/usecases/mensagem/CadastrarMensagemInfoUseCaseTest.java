package com.humanconsulting.humancore_api.application.usecases.mensagem;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import com.humanconsulting.humancore_api.domain.entities.MensagemInfo;
import com.humanconsulting.humancore_api.domain.entities.Sala;
import com.humanconsulting.humancore_api.domain.repositories.MensagemInfoRepository;
import com.humanconsulting.humancore_api.domain.repositories.SalaRepository;
import com.humanconsulting.humancore_api.web.dtos.request.MensagemInfoRequestDto;
import com.humanconsulting.humancore_api.web.dtos.response.chat.ChatMensagemUnificadaDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class CadastrarMensagemInfoUseCaseTest {

    @Mock
    private MensagemInfoRepository mensagemInfoRepository;

    @Mock
    private SalaRepository salaRepository;

    @InjectMocks
    private CadastrarMensagemInfoUseCase cadastrarMensagemInfoUseCase;

    @Test
    void deveCadastrarMensagemInfoComSucesso() {
        // Arrange
        Integer fkSala = 1;
        String conteudo = "Nova mensagem de informação";

        MensagemInfoRequestDto requestDto = new MensagemInfoRequestDto();
        requestDto.setFkSala(fkSala);
        requestDto.setConteudo(conteudo);

        // Simula a sala encontrada
        Sala sala = new Sala();
        sala.setIdSala(fkSala);

        when(salaRepository.findById(fkSala)).thenReturn(Optional.of(sala));

        // Simula a mensagem salva
        MensagemInfo mensagemInfo = new MensagemInfo();
        mensagemInfo.setIdMensagemInfo(1);
        mensagemInfo.setConteudo(conteudo);
        mensagemInfo.setSala(sala);

        when(mensagemInfoRepository.save(any(MensagemInfo.class))).thenReturn(mensagemInfo);

        // Act
        ChatMensagemUnificadaDto resultado = cadastrarMensagemInfoUseCase.execute(requestDto);

        // Assert
        assertEquals(mensagemInfo.getIdMensagemInfo(), resultado.getIdMensagem());
        assertEquals(mensagemInfo.getConteudo(), resultado.getConteudo());
        verify(salaRepository, times(1)).findById(fkSala);
        verify(mensagemInfoRepository, times(1)).save(any(MensagemInfo.class));
    }

    @Test
    void deveLancarExcecaoQuandoSalaNaoEncontrada() {
        // Arrange
        Integer fkSalaInvalida = 999;

        MensagemInfoRequestDto requestDto = new MensagemInfoRequestDto();
        requestDto.setFkSala(fkSalaInvalida);
        requestDto.setConteudo("Mensagem inválida");

        when(salaRepository.findById(fkSalaInvalida)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(java.util.NoSuchElementException.class, () -> {
            cadastrarMensagemInfoUseCase.execute(requestDto);
        });

        verify(salaRepository, times(1)).findById(fkSalaInvalida);
        verify(mensagemInfoRepository, never()).save(any());
    }
}
