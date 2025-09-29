package com.humanconsulting.humancore_api.infrastructure.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class InfrastructureException extends RuntimeException {
    public InfrastructureException(String message) {
        super(message);
    }
}
