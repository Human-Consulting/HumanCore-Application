package com.humanconsulting.humancore_api.infrastructure.exception;

public class RabbitUnavailableException extends RuntimeException {
    public RabbitUnavailableException(String message) {
        super(message);
    }
}