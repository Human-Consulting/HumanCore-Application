package com.humanconsulting.humancore_api.domain.notifiers;

import com.humanconsulting.humancore_api.domain.entities.Projeto;
import com.humanconsulting.humancore_api.domain.entities.Sprint;
import com.humanconsulting.humancore_api.domain.entities.Tarefa;
import com.humanconsulting.humancore_api.domain.entities.Usuario;
import com.humanconsulting.humancore_api.web.dtos.request.UsuarioEnviarCodigoRequestDto;
import com.humanconsulting.humancore_api.web.dtos.response.usuario.LoginResponseDto;

public interface EmailNotifier {

    void update(Tarefa tarefa, Sprint sprintEntrega, Projeto projetoEntrega, Usuario tarefaResponsavel, Usuario projetoResponsavel, LoginResponseDto responsavelProjeto, LoginResponseDto responsavelEntrega);

    void cadastro(Usuario usuario);

    void codigo(UsuarioEnviarCodigoRequestDto usuarioEnviarCodigoRequestDto);
}

