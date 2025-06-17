package com.humanconsulting.humancore_api.observer;

import com.humanconsulting.humancore_api.model.Empresa;
import com.humanconsulting.humancore_api.model.Projeto;
import com.humanconsulting.humancore_api.model.Usuario;

public interface ProjetoObserver {

    void onProjetoCriado(Projeto projeto, Usuario usuario);

    void onEmpresaCriada(Empresa empresa, Usuario usuario);
}
