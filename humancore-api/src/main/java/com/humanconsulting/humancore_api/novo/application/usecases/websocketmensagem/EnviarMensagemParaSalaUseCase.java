package com.humanconsulting.humancore_api.novo.application.usecases.websocketmensagem;

import com.corundumstudio.socketio.SocketIOServer;
import com.humanconsulting.humancore_api.novo.web.dtos.response.chat.ChatMensagemUnificadaDto;

public class EnviarMensagemParaSalaUseCase {

    private SocketIOServer webSocketServer;

    public void execute(Integer idSala, ChatMensagemUnificadaDto msg) {
        System.out.println("Enviando para sala: " + idSala + " mensagem: " + msg.getConteudo());
        webSocketServer.sendToRoom("sala_" + idSala, "novaMensagem", msg);
    }
}

