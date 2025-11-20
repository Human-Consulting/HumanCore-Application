package com.humanconsulting.humancore_api.domain.notifiers;

import com.humanconsulting.humancore_api.domain.entities.Empresa;
import com.humanconsulting.humancore_api.domain.entities.Projeto;
import com.humanconsulting.humancore_api.domain.entities.Usuario;

public interface ProjetoNotifier {
    void onProjetoCriado(Projeto projeto, Usuario editor);

    void onEmpresaCriada(Empresa empresa, Usuario editor);
}

