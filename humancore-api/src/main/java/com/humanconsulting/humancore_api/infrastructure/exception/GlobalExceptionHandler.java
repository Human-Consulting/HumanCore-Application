package com.humanconsulting.humancore_api.infrastructure.exception;

import com.humanconsulting.humancore_api.application.exception.ApplicationException;
import com.humanconsulting.humancore_api.domain.exception.EntidadeConflitanteException;
import com.humanconsulting.humancore_api.domain.exception.EntidadeNaoEncontradaException;
import com.humanconsulting.humancore_api.domain.exception.EntidadeSemPermissaoException;
import com.humanconsulting.humancore_api.domain.exception.EntidadeSemRetornoException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    // ====================== DOMAIN ======================
    @ExceptionHandler(EntidadeSemPermissaoException.class)
    public ResponseEntity<ErrorResponse> handleEntidadeSemPermissao(
            EntidadeSemPermissaoException ex,
            HttpServletRequest request) {
        return buildResponse(HttpStatus.FORBIDDEN, "Sem permissão", ex.getMessage(), request.getRequestURI());
    }

    @ExceptionHandler(EntidadeConflitanteException.class)
    public ResponseEntity<ErrorResponse> handleEntidadeConflitante(
            EntidadeConflitanteException ex,
            HttpServletRequest request) {
        return buildResponse(HttpStatus.CONFLICT, "Entidade em conflito", ex.getMessage(), request.getRequestURI());
    }

    @ExceptionHandler(EntidadeNaoEncontradaException.class)
    public ResponseEntity<ErrorResponse> handleEntidadeNaoEncontrada(
            EntidadeNaoEncontradaException ex,
            HttpServletRequest request) {
        return buildResponse(HttpStatus.NOT_FOUND, "Entidade não encontrada", ex.getMessage(), request.getRequestURI());
    }

    @ExceptionHandler(EntidadeSemRetornoException.class)
    public ResponseEntity<ErrorResponse> handleEntidadeSemRetorno(
            EntidadeSemRetornoException ex,
            HttpServletRequest request) {
        return buildResponse(HttpStatus.NO_CONTENT, "Sem retorno", ex.getMessage(), request.getRequestURI());
    }

    // ====================== APPLICATION ======================
    @ExceptionHandler(ApplicationException.class)
    public ResponseEntity<ErrorResponse> handleApplication(
            ApplicationException ex,
            HttpServletRequest request) {
        return buildResponse(HttpStatus.BAD_REQUEST, "Falha na aplicação", ex.getMessage(), request.getRequestURI());
    }

    // ====================== INFRASTRUCTURE ======================
    @ExceptionHandler(InfrastructureException.class)
    public ResponseEntity<ErrorResponse> handleInfrastructure(
            InfrastructureException ex,
            HttpServletRequest request) {
        return buildResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Erro de infraestrutura", ex.getMessage(), request.getRequestURI());
    }

    // ====================== FALLBACK ======================
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneric(
            Exception ex,
            HttpServletRequest request) {
        return buildResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Erro interno do servidor", ex.getMessage(), request.getRequestURI());
    }

    // ====================== UTILITÁRIO ======================
    private ResponseEntity<ErrorResponse> buildResponse(HttpStatus status, String error, String message, String path) {
        ErrorResponse response = new ErrorResponse(
                status.value(),
                error,
                message,
                path
        );
        return new ResponseEntity<>(response, status);
    }
}
