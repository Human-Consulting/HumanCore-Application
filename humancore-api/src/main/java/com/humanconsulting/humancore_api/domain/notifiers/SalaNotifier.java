package com.humanconsulting.humancore_api.domain.notifiers;

import com.humanconsulting.humancore_api.domain.entities.*;
import com.humanconsulting.humancore_api.web.dtos.response.chat.ChatMensagemUnificadaDto;
import java.util.List;

public interface SalaNotifier {
    void adicionarUsuarioEmSalaProjeto(Tarefa tarefa, Projeto projeto, Usuario tarefaResponsavel);

    void adicionarUsuarioEmSalaEmpresa(Usuario usuario);

    void onProjetoCriado(Projeto projeto, Usuario editor);

    void onEmpresaCriada(Empresa empresa, Usuario editor);

    List<ChatMensagemUnificadaDto> notificarAtualizacoesSala(Sala antiga, Sala nova, Usuario usuarioEditor);
}
