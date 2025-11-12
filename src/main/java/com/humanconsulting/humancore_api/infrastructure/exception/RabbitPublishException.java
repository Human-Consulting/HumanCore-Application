package com.humanconsulting.humancore_api.infrastructure.exception;

public class RabbitPublishException extends RuntimeException {
    public RabbitPublishException(String message) {
        super(message);
    }
}