package com.humanconsulting.humancore_api.observer;

import com.humanconsulting.humancore_api.controller.dto.response.usuario.LoginResponseDto;
import com.humanconsulting.humancore_api.exception.EntidadeNaoEncontradaException;
import com.humanconsulting.humancore_api.model.*;
import com.humanconsulting.humancore_api.repository.SalaRepository;
import com.humanconsulting.humancore_api.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SalaNotifier implements SalaObserver {

    @Autowired private SalaRepository salaRepository;

    @Autowired private UsuarioRepository usuarioRepository;

    @Override
    public void update(Tarefa tarefa, Sprint sprint, Projeto projeto, Usuario tarefaResponsavel, Usuario projetoResponsavel, LoginResponseDto responsavelProjeto, LoginResponseDto responsavelEntrega) {
        if (projeto == null || tarefaResponsavel == null) throw new EntidadeNaoEncontradaException("Projeto e/ou Tarefa não encontrado.");

        Sala sala = salaRepository.findByProjeto(projeto);
        if (sala == null) throw new EntidadeNaoEncontradaException("Sala não encontrada.");

        if (!sala.getUsuarios().contains(tarefaResponsavel)) {
            sala.getUsuarios().add(tarefaResponsavel);
            salaRepository.save(sala);
            System.out.println("Usuário " + tarefaResponsavel.getNome() + " adicionado à sala do projeto " + projeto.getTitulo());
        }
    }
}
