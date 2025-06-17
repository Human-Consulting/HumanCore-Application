package com.humanconsulting.humancore_api.observer;

import com.humanconsulting.humancore_api.controller.dto.request.UsuarioEnviarCodigoRequestDto;
import com.humanconsulting.humancore_api.controller.dto.response.usuario.LoginResponseDto;
import com.humanconsulting.humancore_api.model.Projeto;
import com.humanconsulting.humancore_api.model.Sprint;
import com.humanconsulting.humancore_api.model.Tarefa;
import com.humanconsulting.humancore_api.model.Usuario;

public interface Observer {
    void update(Tarefa tarefa, Sprint sprintEntrega, Projeto projetoEntrega, Usuario tarefaResponsavel, Usuario projetoResponsavel, LoginResponseDto responsavelProjeto, LoginResponseDto responsavelEntrega);

    void cadastro(Usuario usuario);

    void codigo(UsuarioEnviarCodigoRequestDto usuarioEnviarCodigoRequestDto);
}
