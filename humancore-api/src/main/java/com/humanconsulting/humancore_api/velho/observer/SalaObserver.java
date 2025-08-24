package com.humanconsulting.humancore_api.velho.observer;

import com.humanconsulting.humancore_api.model.*;
import com.humanconsulting.humancore_api.velho.model.Empresa;
import com.humanconsulting.humancore_api.velho.model.Projeto;
import com.humanconsulting.humancore_api.velho.model.Tarefa;
import com.humanconsulting.humancore_api.velho.model.Usuario;

public interface SalaObserver {
    void adicionarUsuarioEmSalaProjeto(Tarefa tarefa, Projeto projetoEntrega, Usuario tarefaResponsavel);

    void adicionarUsuarioEmSalaEmpresa(Usuario usuario);

    void onProjetoCriado(Projeto projeto, Usuario usuario);

    void onEmpresaCriada(Empresa empresa, Usuario usuario);
}
