package com.humanconsulting.humancore_api.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    private ResponseEntity<ErrorResponse> buildResponse(HttpStatus status, String error, String message, String path, HttpServletRequest request, String errorCode, java.util.List<String> details, Exception ex) {
        String method = request.getMethod();
        String params = request.getQueryString();
        String stackTrace = (status == HttpStatus.INTERNAL_SERVER_ERROR && ex != null) ? getStackTraceString(ex) : null;
        ErrorResponse response = new ErrorResponse(
                status.value(),
                error,
                message,
                path,
                method,
                params,
                errorCode,
                details,
                stackTrace
        );
        return new ResponseEntity<>(response, status);
    }

    private String getStackTraceString(Exception ex) {
        StringBuilder sb = new StringBuilder();
        for (StackTraceElement elem : ex.getStackTrace()) {
            sb.append(elem.toString()).append("\n");
        }
        return sb.toString();
    }

    @ExceptionHandler(AcessoNegadoException.class)
    public ResponseEntity<ErrorResponse> handleAcessoNegado(
            AcessoNegadoException ex,
            HttpServletRequest request) {
        return buildResponse(HttpStatus.FORBIDDEN, "Usuário não possui permissão para acessar este recurso", ex.getMessage(), request.getRequestURI(), request, "AUTH_403", java.util.Collections.singletonList("."), ex);
    }

    @ExceptionHandler(EntidadeConflitanteException.class)
    public ResponseEntity<ErrorResponse> handleEntidadeConflitante(
            EntidadeConflitanteException ex,
            HttpServletRequest request) {
        return buildResponse(HttpStatus.CONFLICT, "Entidade em conflito", ex.getMessage(), request.getRequestURI(), request, "ENTITY_409", java.util.Collections.singletonList("Já existe uma entidade conflitante."), ex);
    }

    @ExceptionHandler(EntidadeNaoEncontradaException.class)
    public ResponseEntity<ErrorResponse> handleEntidadeNaoEncontrada(
            EntidadeNaoEncontradaException ex,
            HttpServletRequest request) {
        return buildResponse(HttpStatus.NOT_FOUND, "Entidade não encontrada", ex.getMessage(), request.getRequestURI(), request, "ENTITY_404", java.util.Collections.singletonList("A entidade solicitada não foi encontrada."), ex);
    }

    @ExceptionHandler(EntidadeRequisicaoFalhaException.class)
    public ResponseEntity<ErrorResponse> handleEntidadeRequisicaoFalha(
            EntidadeRequisicaoFalhaException ex,
            HttpServletRequest request) {
        return buildResponse(HttpStatus.BAD_REQUEST, "Falha na requisição", ex.getMessage(), request.getRequestURI(), request, "REQ_400", java.util.Collections.singletonList("A requisição está malformada ou possui dados inválidos."), ex);
    }

    @ExceptionHandler(EntidadeSemPermissaoException.class)
    public ResponseEntity<ErrorResponse> handleEntidadeSemPermissao(
            EntidadeSemPermissaoException ex,
            HttpServletRequest request) {
        return buildResponse(HttpStatus.FORBIDDEN, "Sem permissão", ex.getMessage(), request.getRequestURI(), request, "AUTH_403", java.util.Collections.singletonList("Ação não permitida para este usuário."), ex);
    }

    @ExceptionHandler(EntidadeSemRetornoException.class)
    public ResponseEntity<ErrorResponse> handleEntidadeSemRetorno(
            EntidadeSemRetornoException ex,
            HttpServletRequest request) {
        return buildResponse(HttpStatus.NO_CONTENT, "Sem retorno", ex.getMessage(), request.getRequestURI(), request, "NO_CONTENT_204", java.util.Collections.singletonList("A operação foi realizada, mas não há dados para retornar."), ex);
    }

    // Fallback genérico para qualquer outra Exception
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneric(
            Exception ex,
            HttpServletRequest request) {
        return buildResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Erro interno do servidor", ex.getMessage(), request.getRequestURI(), request, "GENERIC_500", java.util.Collections.singletonList("Ocorreu um erro inesperado. Contate o suporte se o problema persistir."), ex);
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
