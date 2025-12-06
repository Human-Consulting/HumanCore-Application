package com.humanconsulting.humancore_api.application.usecases.usuario.reset;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.humanconsulting.humancore_api.application.exception.ApplicationException;
import com.humanconsulting.humancore_api.domain.notifiers.ResetSenhaNotifier;
import com.humanconsulting.humancore_api.domain.repositories.UsuarioRepository;
import com.humanconsulting.humancore_api.web.dtos.request.SolicitarResetSenhaRequest;
import org.springframework.amqp.AmqpConnectException;
import org.springframework.amqp.AmqpException;

public class SolicitarResetSenhaUseCase {
    private static final Logger log = LoggerFactory.getLogger(SolicitarResetSenhaUseCase.class);
    private final UsuarioRepository usuarioRepository;
    private final ResetSenhaNotifier resetSenhaNotifier;
    private final com.humanconsulting.humancore_api.domain.security.TokenResetSenhaService tokenService;

    public SolicitarResetSenhaUseCase(UsuarioRepository usuarioRepository, ResetSenhaNotifier resetSenhaNotifier,
            com.humanconsulting.humancore_api.domain.security.TokenResetSenhaService tokenService) {
        this.usuarioRepository = usuarioRepository;
        this.resetSenhaNotifier = resetSenhaNotifier;
        this.tokenService = tokenService;
    }

    public void execute(SolicitarResetSenhaRequest request) {
        usuarioRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new ApplicationException("Email não encontrado"));
        String token = tokenService.gerarTokenReset(request.getEmail());
        log.info("[RESET] Token gerado para {}: {}", request.getEmail(), token);
        try {
            // Monta o Map com email, token e url do frontend
            java.util.Map<String, String> resetInfo = new java.util.HashMap<>();
            resetInfo.put("email", request.getEmail());
            resetInfo.put("token", token);
            resetInfo.put("frontendUrl", "http://localhost:5173/"); // URL raiz, modal será aberto se houver token
            log.info("[RESET] Enviando para fila reset: {}", resetInfo);
            resetSenhaNotifier.sendResetInfo(resetInfo);
        } catch (AmqpConnectException e) {
            log.error("[RESET] RabbitMQ indisponível ao enviar token", e);
            throw new com.humanconsulting.humancore_api.infrastructure.exception.RabbitUnavailableException(
                    "O email está na fila de envio!");
        } catch (AmqpException e) {
            log.error("[RESET] Falha ao publicar token de reset", e);
            throw new com.humanconsulting.humancore_api.infrastructure.exception.RabbitPublishException(
                    "Falha ao enviar token de reset");
        }
    }
}
