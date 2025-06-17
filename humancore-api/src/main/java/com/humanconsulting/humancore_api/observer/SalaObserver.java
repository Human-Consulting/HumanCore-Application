package com.humanconsulting.humancore_api.observer;

import com.humanconsulting.humancore_api.controller.dto.response.usuario.LoginResponseDto;
import com.humanconsulting.humancore_api.model.*;

public interface SalaObserver {
    void adicionarUsuarioEmSalaProjeto(Tarefa tarefa, Projeto projetoEntrega, Usuario tarefaResponsavel);

    void adicionarUsuarioEmSalaEmpresa(Usuario usuario);

    void onProjetoCriado(Projeto projeto, Usuario usuario);

    void onEmpresaCriada(Empresa empresa, Usuario usuario);
}
