package com.humanconsulting.humancore_api.novo.domain.notifiers;

import com.humanconsulting.humancore_api.novo.domain.entities.*;
import com.humanconsulting.humancore_api.novo.web.dtos.request.UsuarioEnviarCodigoRequestDto;
import com.humanconsulting.humancore_api.novo.web.dtos.response.usuario.LoginResponseDto;

public interface EmailNotifier {

    void update(Tarefa tarefa, Sprint sprintEntrega, Projeto projetoEntrega, Usuario tarefaResponsavel, Usuario projetoResponsavel, LoginResponseDto responsavelProjeto, LoginResponseDto responsavelEntrega);

    void cadastro(Usuario usuario);

    void codigo(UsuarioEnviarCodigoRequestDto usuarioEnviarCodigoRequestDto);
}

