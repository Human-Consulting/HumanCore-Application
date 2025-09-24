package com.humanconsulting.humancore_api.novo.domain.notifiers;

import com.humanconsulting.humancore_api.novo.domain.entities.*;
import com.humanconsulting.humancore_api.novo.web.dtos.response.chat.ChatMensagemUnificadaDto;
import java.util.List;

public interface SalaNotifier {
    void adicionarUsuarioEmSalaProjeto(Tarefa tarefa, Projeto projeto, Usuario tarefaResponsavel);

    void adicionarUsuarioEmSalaEmpresa(Usuario usuario);

    void onProjetoCriado(Projeto projeto, Usuario editor);

    void onEmpresaCriada(Empresa empresa, Usuario editor);

    List<ChatMensagemUnificadaDto> notificarAtualizacoesSala(Sala antiga, Sala nova, Usuario usuarioEditor);
}
