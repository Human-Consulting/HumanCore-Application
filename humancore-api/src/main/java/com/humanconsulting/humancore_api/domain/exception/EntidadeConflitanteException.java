package com.humanconsulting.humancore_api.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class EntidadeConflitanteException extends RuntimeException {
    public EntidadeConflitanteException(String message) {
        super(message);
    }
}
