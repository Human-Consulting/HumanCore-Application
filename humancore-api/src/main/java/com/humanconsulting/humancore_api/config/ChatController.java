package com.humanconsulting.humancore_api.config;
import com.humanconsulting.humancore_api.controller.dto.request.MensagemRequestDto;
import com.humanconsulting.humancore_api.controller.dto.response.chat.ChatMensagemUnificadaDto;
import com.humanconsulting.humancore_api.observer.SalaNotifier;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {

    @Autowired private SalaNotifier salaNotifier;

    @MessageMapping("/chat/{roomId}")
    @SendTo("/topic/{roomId}")
    public ChatMensagemUnificadaDto sendMessage(@DestinationVariable String roomId, MensagemRequestDto mensagem) {
        System.out.println("Mensagem recebida na sala " + roomId + ": " + mensagem.getConteudo());
        return salaNotifier.enviarMensagem(mensagem);
    }
}
