package com.humanconsulting.humancore_api.velho.observer;

import com.humanconsulting.humancore_api.velho.controller.dto.request.UsuarioEnviarCodigoRequestDto;
import com.humanconsulting.humancore_api.velho.controller.dto.response.usuario.LoginResponseDto;
import com.humanconsulting.humancore_api.velho.model.Projeto;
import com.humanconsulting.humancore_api.velho.model.Sprint;
import com.humanconsulting.humancore_api.velho.model.Tarefa;
import com.humanconsulting.humancore_api.velho.model.Usuario;

public interface Observer {
    void update(Tarefa tarefa, Sprint sprintEntrega, Projeto projetoEntrega, Usuario tarefaResponsavel, Usuario projetoResponsavel, LoginResponseDto responsavelProjeto, LoginResponseDto responsavelEntrega);

    void cadastro(Usuario usuario);

    void codigo(UsuarioEnviarCodigoRequestDto usuarioEnviarCodigoRequestDto);
}
