package com.humanconsulting.humancore_api.observer;

import com.humanconsulting.humancore_api.controller.dto.response.usuario.LoginResponseDto;
import com.humanconsulting.humancore_api.model.Projeto;
import com.humanconsulting.humancore_api.model.Sprint;
import com.humanconsulting.humancore_api.model.Tarefa;
import com.humanconsulting.humancore_api.model.Usuario;

public interface SalaObserver {
    void update(Tarefa tarefa, Projeto projetoEntrega, Usuario tarefaResponsavel);
}
