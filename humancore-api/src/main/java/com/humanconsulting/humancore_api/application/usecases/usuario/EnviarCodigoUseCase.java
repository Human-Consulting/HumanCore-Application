package com.humanconsulting.humancore_api.application.usecases.usuario;

import com.humanconsulting.humancore_api.infrastructure.configs.RabbitTemplateConfiguration;
import com.humanconsulting.humancore_api.infrastructure.exception.RabbitPublishException;
import com.humanconsulting.humancore_api.infrastructure.exception.RabbitUnavailableException;
import com.humanconsulting.humancore_api.web.dtos.request.UsuarioEnviarCodigoRequestDto;
import org.springframework.amqp.AmqpConnectException;
import org.springframework.amqp.AmqpException;

public class EnviarCodigoUseCase {
    private final RabbitTemplateConfiguration rabbitMQ;

    public EnviarCodigoUseCase(RabbitTemplateConfiguration rabbitMQ) {
        this.rabbitMQ = rabbitMQ;
    }

    public void execute(UsuarioEnviarCodigoRequestDto usuarioEnviarCodigoRequestDto) {
        try {
            rabbitMQ.rabbitTemplate().convertAndSend(
                    "codigo",
                    usuarioEnviarCodigoRequestDto
            );
        } catch (AmqpConnectException e) {
            throw new RabbitUnavailableException("O email está na fila de envio!");
        } catch (AmqpException e) {
            throw new RabbitPublishException("Falha ao enviar email");
        }
    }
}

