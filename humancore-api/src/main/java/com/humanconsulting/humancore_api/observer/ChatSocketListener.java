package com.humanconsulting.humancore_api.observer;

import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.annotation.OnConnect;
import com.corundumstudio.socketio.annotation.OnDisconnect;
import com.corundumstudio.socketio.annotation.OnEvent;
import org.springframework.stereotype.Component;

@Component
public class ChatSocketListener {

    @OnConnect
    public void onConnect(SocketIOClient client) {
        System.out.println("Cliente conectado: " + client.getSessionId());
    }

    @OnDisconnect
    public void onDisconnect(SocketIOClient client) {
        System.out.println("Cliente desconectado: " + client.getSessionId());
    }

    @OnEvent("entrarSala")
    public void onJoinRoom(SocketIOClient client, String sala) {
        client.joinRoom(sala);
        System.out.println("Entrou na sala: " + sala);
    }

    @OnEvent("sairSala")
    public void onLeaveRoom(SocketIOClient client, String sala) {
        client.leaveRoom(sala);
        System.out.println("Saiu da sala: " + sala);
    }
}
