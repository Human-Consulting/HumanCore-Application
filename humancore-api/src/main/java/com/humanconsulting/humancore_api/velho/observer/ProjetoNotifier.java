package com.humanconsulting.humancore_api.velho.observer;

import com.humanconsulting.humancore_api.velho.controller.dto.request.MensagemInfoRequestDto;
import com.humanconsulting.humancore_api.model.*;
import com.humanconsulting.humancore_api.velho.model.Empresa;
import com.humanconsulting.humancore_api.velho.model.Projeto;
import com.humanconsulting.humancore_api.velho.model.Sala;
import com.humanconsulting.humancore_api.velho.model.Usuario;
import com.humanconsulting.humancore_api.velho.repository.SalaRepository;
import com.humanconsulting.humancore_api.velho.service.MensagemService;
import com.humanconsulting.humancore_api.velho.service.SalaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class ProjetoNotifier implements ProjetoObserver {

    private final List<ProjetoObserver> observers;
    private final SalaRepository salaRepository;
    private final MensagemService mensagemService;
    private final SalaService salaService;

    @Autowired
    public ProjetoNotifier(List<ProjetoObserver> observers, SalaRepository salaRepository, MensagemService mensagemService, SalaService salaService) {
        this.observers = observers;
        this.salaRepository = salaRepository;
        this.mensagemService = mensagemService;
        this.salaService = salaService;
    }

    @Override
    public void onProjetoCriado(Projeto projeto, Usuario editor) {
        Sala novaSala = new Sala();
        Set<Usuario> participantesIniciais = new HashSet<>();
        participantesIniciais.add(projeto.getResponsavel());
        participantesIniciais.add(editor);
        novaSala.setProjeto(projeto);
        novaSala.setNome(projeto.getTitulo());
        novaSala.setUrlImagem(projeto.getUrlImagem());
        novaSala.setUsuarios(participantesIniciais);
        Sala salaCriada = salaRepository.save(novaSala);
        mensagemService.cadastrarMensagemInfo(new MensagemInfoRequestDto("Conversa criada.", LocalDateTime.now(), salaCriada.getIdSala()));
        for (Usuario participante : participantesIniciais) {
            mensagemService.cadastrarMensagemInfo(new MensagemInfoRequestDto(editor.getNome() + " adicionou " + participante.getNome() + " à sala", LocalDateTime.now(), salaCriada.getIdSala()));
        }

        for (ProjetoObserver observer : observers) {
            observer.onProjetoCriado(projeto, editor);
        }
    }

    @Override
    public void onEmpresaCriada(Empresa empresa, Usuario editor) {
        Sala novaSala = new Sala();
        Set<Usuario> participantesIniciais = new HashSet<>();
        participantesIniciais.add(editor);
        novaSala.setNome(empresa.getNome());
        novaSala.setUrlImagem(empresa.getUrlImagem());
        novaSala.setUsuarios(participantesIniciais);
        Sala salaCriada = salaRepository.save(novaSala);
        enviarMensagemInfo("Conversa criada.", salaCriada.getIdSala());
        for (Usuario participante : participantesIniciais) {

            if (participante.getIdUsuario() != editor.getIdUsuario()) enviarMensagemInfo(editor.getNome() + " adicionou " + participante.getNome() + " à sala", salaCriada.getIdSala());
        }

        for (ProjetoObserver observer : observers) {
            observer.onEmpresaCriada(empresa, editor);
        }
    }
//
//    @Override
//    public void onUsuarioCriado(UsuarioEntity usuario) {
//        Sala sala = new
//        SalaRequestDto salaRequestDto = new SalaRequestDto();
//        salaService.atualizar();
//    }

    public void enviarMensagemInfo(String conteudo, Integer idSala) {
        mensagemService.cadastrarMensagemInfo(new MensagemInfoRequestDto(conteudo, LocalDateTime.now(), idSala));
    }
}