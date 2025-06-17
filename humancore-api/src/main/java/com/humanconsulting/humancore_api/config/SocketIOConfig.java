package com.humanconsulting.humancore_api.config;

import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOServer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.humanconsulting.humancore_api.observer.ChatSocketListener;
import jakarta.annotation.PreDestroy;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;

@SpringBootConfiguration
public class SocketIOConfig {

    private SocketIOServer server;

    @Bean
    public SocketIOServer socketIOServer(ChatSocketListener chatSocketListener) {

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        CustomJacksonJsonSupport jsonSupport = new CustomJacksonJsonSupport(objectMapper);

        Configuration config = new Configuration();
        config.setHostname("localhost");
        config.setPort(8081);
        config.setOrigin("*");
        config.setJsonSupport(jsonSupport);

        server = new SocketIOServer(config);
        server.addListeners(chatSocketListener);
        server.start();

        System.out.println("Socket.IO server iniciado na porta 8081");

        return server;
    }

    @PreDestroy
    public void stopServer() {
        if (server != null) {
            System.out.println("Parando Socket.IO server...");
            server.stop();
        }
    }
}
