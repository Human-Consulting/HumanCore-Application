package com.humanconsulting.humancore_api.velho.service;

import com.humanconsulting.humancore_api.velho.controller.dto.response.chat.ChatMensagemUnificadaDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.corundumstudio.socketio.SocketIOServer;

@Service
public class WebSocketMensagemPublisher {

    @Autowired
    private SocketIOServer socketServer;

    public void enviarParaSala(Integer idSala, ChatMensagemUnificadaDto msg) {
        System.out.println("Enviando para sala: " + idSala + " mensagem: " + msg.getConteudo());
        socketServer.getRoomOperations("sala_" + idSala).sendEvent("novaMensagem", msg);
    }
}
