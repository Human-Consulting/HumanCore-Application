package com.humanconsulting.humancore_api.novo.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class EntidadeConflitanteException extends RuntimeException {
    public EntidadeConflitanteException(String message) {
        super(message);
    }
}
