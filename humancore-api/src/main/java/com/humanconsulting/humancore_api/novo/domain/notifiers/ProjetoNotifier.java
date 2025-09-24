package com.humanconsulting.humancore_api.novo.domain.notifiers;

import com.humanconsulting.humancore_api.novo.domain.entities.Projeto;
import com.humanconsulting.humancore_api.novo.domain.entities.Empresa;
import com.humanconsulting.humancore_api.novo.domain.entities.Usuario;

public interface ProjetoNotifier {
    void onProjetoCriado(Projeto projeto, Usuario editor);

    void onEmpresaCriada(Empresa empresa, Usuario editor);
}

