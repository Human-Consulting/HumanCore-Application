package com.humanconsulting.humancore_api.novo.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class EntidadeSemPermissaoException extends RuntimeException {
    public EntidadeSemPermissaoException(String message) {
        super(message);
    }
}
