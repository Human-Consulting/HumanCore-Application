package com.humanconsulting.humancore_api.application.usecases.usuario;

import com.humanconsulting.humancore_api.domain.notifiers.EmailNotifier;
import com.humanconsulting.humancore_api.web.dtos.request.UsuarioEnviarCodigoRequestDto;

public class EnviarCodigoUseCase {
    private final EmailNotifier emailNotifier;

    public EnviarCodigoUseCase(EmailNotifier emailNotifier) {
        this.emailNotifier = emailNotifier;
    }

    public void execute(UsuarioEnviarCodigoRequestDto usuarioEnviarCodigoRequestDto) {
        emailNotifier.codigo(usuarioEnviarCodigoRequestDto);
    }
}

