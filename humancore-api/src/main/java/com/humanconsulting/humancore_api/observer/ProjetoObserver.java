package com.humanconsulting.humancore_api.observer;

import com.humanconsulting.humancore_api.model.Projeto;

public interface ProjetoObserver {

    void onProjetoCriado(Projeto projeto);
}
