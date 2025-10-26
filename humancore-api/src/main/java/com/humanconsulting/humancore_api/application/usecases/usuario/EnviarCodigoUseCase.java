package com.humanconsulting.humancore_api.application.usecases.usuario;

import com.humanconsulting.humancore_api.infrastructure.configs.RabbitTemplateConfiguration;
import com.humanconsulting.humancore_api.web.dtos.request.UsuarioEnviarCodigoRequestDto;

public class EnviarCodigoUseCase {
    private final RabbitTemplateConfiguration rabbitMQ;

    public EnviarCodigoUseCase(RabbitTemplateConfiguration rabbitMQ) {
        this.rabbitMQ = rabbitMQ;
    }

    public void execute(UsuarioEnviarCodigoRequestDto usuarioEnviarCodigoRequestDto) {
        rabbitMQ.rabbitTemplate().convertAndSend(
                "codigo",
                usuarioEnviarCodigoRequestDto
        );
    }
}

