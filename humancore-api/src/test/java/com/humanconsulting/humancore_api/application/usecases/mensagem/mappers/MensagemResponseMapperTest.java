package com.humanconsulting.humancore_api.application.usecases.mensagem.mappers;

import com.humanconsulting.humancore_api.domain.entities.Mensagem;
import com.humanconsulting.humancore_api.domain.entities.MensagemInfo;
import com.humanconsulting.humancore_api.domain.entities.Sala;
import com.humanconsulting.humancore_api.domain.entities.Usuario;
import com.humanconsulting.humancore_api.web.dtos.response.chat.ChatMensagemUnificadaDto;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class MensagemResponseMapperTest {

    private final MensagemResponseMapper mapper = new MensagemResponseMapper();

    @Test
    void deveMapearMensagemParaChatMensagemUnificadaDto() {
        // Arrange
        Mensagem mensagem = new Mensagem();
        mensagem.setIdMensagem(1);
        mensagem.setConteudo("Olá mundo");
        mensagem.setUsuario(mock(Usuario.class));
        mensagem.setSala(mock(Sala.class));

        // Act
        ChatMensagemUnificadaDto result = mapper.toResponse(mensagem);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getIdMensagem());
        assertEquals("Olá mundo", result.getConteudo());
    }

    @Test
    void deveMapearMensagemInfoParaChatMensagemUnificadaDto() {
        // Arrange
        MensagemInfo mensagemInfo = new MensagemInfo();
        mensagemInfo.setIdMensagemInfo(2);
        mensagemInfo.setConteudo("Mensagem info");
        mensagemInfo.setSala(mock(Sala.class));

        // Act
        ChatMensagemUnificadaDto result = mapper.toResponse(mensagemInfo);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.getIdMensagem());
        assertEquals("Mensagem info", result.getConteudo());
    }
}
