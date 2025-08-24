package com.humanconsulting.humancore_api.velho.observer;

import com.humanconsulting.humancore_api.velho.model.Empresa;
import com.humanconsulting.humancore_api.velho.model.Projeto;
import com.humanconsulting.humancore_api.velho.model.Usuario;

public interface ProjetoObserver {

    void onProjetoCriado(Projeto projeto, Usuario usuario);

    void onEmpresaCriada(Empresa empresa, Usuario usuario);
}
