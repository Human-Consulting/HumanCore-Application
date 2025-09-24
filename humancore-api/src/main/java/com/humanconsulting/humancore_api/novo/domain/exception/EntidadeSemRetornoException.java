package com.humanconsulting.humancore_api.novo.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NO_CONTENT)
public class EntidadeSemRetornoException extends RuntimeException {
    public EntidadeSemRetornoException(String message) {
        super(message);
    }
}
