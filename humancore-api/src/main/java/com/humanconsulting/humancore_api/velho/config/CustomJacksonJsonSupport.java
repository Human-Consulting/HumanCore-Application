package com.humanconsulting.humancore_api.velho.config;

import com.corundumstudio.socketio.AckCallback;
import com.corundumstudio.socketio.protocol.AckArgs;
import com.corundumstudio.socketio.protocol.JsonSupport;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.ByteBufOutputStream;

import java.io.IOException;
import java.util.List;

public class CustomJacksonJsonSupport implements JsonSupport {

    private final ObjectMapper objectMapper;

    public CustomJacksonJsonSupport(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public <T> T readValue(String s, Class<T> aClass) throws IOException {
        return objectMapper.readValue(s, aClass);
    }

    public String toJson(Object o) throws IOException {
        return objectMapper.writeValueAsString(o);
    }

    @Override
    public AckArgs readAckArgs(ByteBufInputStream byteBufInputStream, AckCallback<?> ackCallback) throws IOException {
        return null;
    }

    @Override
    public <T> T readValue(String s, ByteBufInputStream byteBufInputStream, Class<T> aClass) throws IOException {
        return null;
    }

    @Override
    public void writeValue(ByteBufOutputStream byteBufOutputStream, Object o) throws IOException {

    }

    @Override
    public void addEventMapping(String s, String s1, Class<?>... classes) {

    }

    @Override
    public void removeEventMapping(String s, String s1) {

    }

    @Override
    public List<byte[]> getArrays() {
        return List.of();
    }
}
