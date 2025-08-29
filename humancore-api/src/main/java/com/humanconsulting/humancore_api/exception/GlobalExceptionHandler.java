package com.humanconsulting.humancore_api.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AcessoNegadoException.class)
    public ResponseEntity<ErrorResponse> handleAcessoNegado(
            AcessoNegadoException ex,
            HttpServletRequest request) {
        return buildResponse(HttpStatus.FORBIDDEN, "Acesso negado", ex.getMessage(), request.getRequestURI());
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

    @ExceptionHandler(EntidadeRequisicaoFalhaException.class)
    public ResponseEntity<ErrorResponse> handleEntidadeRequisicaoFalha(
            EntidadeRequisicaoFalhaException ex,
            HttpServletRequest request) {
        return buildResponse(HttpStatus.BAD_REQUEST, "Falha na requisição", ex.getMessage(), request.getRequestURI());
    }

    @ExceptionHandler(EntidadeSemPermissaoException.class)
    public ResponseEntity<ErrorResponse> handleEntidadeSemPermissao(
            EntidadeSemPermissaoException ex,
            HttpServletRequest request) {
        return buildResponse(HttpStatus.FORBIDDEN, "Sem permissão", ex.getMessage(), request.getRequestURI());
    }

    @ExceptionHandler(EntidadeSemRetornoException.class)
    public ResponseEntity<ErrorResponse> handleEntidadeSemRetorno(
            EntidadeSemRetornoException ex,
            HttpServletRequest request) {
        return buildResponse(HttpStatus.NO_CONTENT, "Sem retorno", ex.getMessage(), request.getRequestURI());
    }

    // Fallback genérico para qualquer outra Exception
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneric(
            Exception ex,
            HttpServletRequest request) {
        return buildResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Erro interno do servidor", ex.getMessage(), request.getRequestURI());
    }

    // Método utilitário pra não repetir código
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
