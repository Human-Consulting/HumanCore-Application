package com.humanconsulting.humancore_api.velho.observer;

import com.humanconsulting.humancore_api.velho.controller.dto.request.MensagemInfoRequestDto;
import com.humanconsulting.humancore_api.velho.controller.dto.request.MensagemRequestDto;
import com.humanconsulting.humancore_api.velho.controller.dto.response.chat.ChatMensagemUnificadaDto;
import com.humanconsulting.humancore_api.velho.exception.EntidadeNaoEncontradaException;
import com.humanconsulting.humancore_api.model.*;
import com.humanconsulting.humancore_api.velho.model.*;
import com.humanconsulting.humancore_api.velho.repository.SalaRepository;
import com.humanconsulting.humancore_api.velho.repository.UsuarioRepository;
import com.humanconsulting.humancore_api.velho.service.MensagemService;
import com.humanconsulting.humancore_api.velho.service.SalaService;
import com.humanconsulting.humancore_api.velho.service.WebSocketMensagemPublisher;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class SalaNotifier implements SalaObserver {

    @Autowired private SalaRepository salaRepository;
    @Autowired private UsuarioRepository usuarioRepository;
    @Autowired private List<ProjetoObserver> observers;
    @Autowired private MensagemService mensagemService;
    @Autowired private SalaService salaService;
    @Autowired private WebSocketMensagemPublisher publisher;

    @Override
    @Transactional
    public void adicionarUsuarioEmSalaProjeto(Tarefa tarefa, Projeto projeto, Usuario tarefaResponsavel) {
        if (projeto == null || tarefaResponsavel == null) throw new EntidadeNaoEncontradaException("Projeto e/ou Tarefa não encontrado.");

        Sala sala = salaRepository.findByProjeto(projeto);
        if (sala == null) throw new EntidadeNaoEncontradaException("Sala não encontrada.");

        if (!sala.getUsuarios().contains(tarefaResponsavel)) {
            sala.getUsuarios().add(tarefaResponsavel);
            salaRepository.save(sala);
        }
    }

    @Override
    @Transactional
    public void adicionarUsuarioEmSalaEmpresa(Usuario usuario) {
        if (usuario.getEmpresa() == null) throw new EntidadeNaoEncontradaException("Empresa não encontrada.");

        Sala sala = salaRepository.findByEmpresa(usuario.getEmpresa());
        if (sala == null) throw new EntidadeNaoEncontradaException("Sala não encontrada.");

        if (!sala.getUsuarios().contains(usuario)) {
            sala.getUsuarios().add(usuario);
            Sala salaCriada = salaRepository.save(sala);
            enviarMensagemInfo(usuario.getNome() + " entrou na empresa.", salaCriada.getIdSala());
        }
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
    }

    @Override
    public void onEmpresaCriada(Empresa empresa, Usuario editor) {
        Sala novaSala = new Sala();
        Set<Usuario> participantesIniciais = new HashSet<>();
        participantesIniciais.add(editor);
        novaSala.setNome(empresa.getNome());
        novaSala.setUrlImagem(empresa.getUrlImagem());
        novaSala.setUsuarios(participantesIniciais);
        novaSala.setEmpresa(empresa);
        Sala salaCriada = salaRepository.save(novaSala);
        enviarMensagemInfo("Conversa criada.", salaCriada.getIdSala());
        for (Usuario participante : participantesIniciais) {
            if (participante.getIdUsuario() != editor.getIdUsuario()) enviarMensagemInfo(editor.getNome() + " adicionou " + participante.getNome() + " à sala", salaCriada.getIdSala());
        }
    }

    @Transactional
    public List<ChatMensagemUnificadaDto> notificarAtualizacoesSala(Sala antiga, Sala nova, Usuario usuarioEditor) {
        List<ChatMensagemUnificadaDto> listaMensagens = new ArrayList<>();
        Integer idSala = antiga.getIdSala();

        if (!antiga.getNome().equals(nova.getNome())) {
            listaMensagens.add(enviarMensagemInfo(usuarioEditor.getNome() + " alterou o nome da sala para " + nova.getNome() + ".", idSala));
        }

        if (!antiga.getUrlImagem().equals(nova.getUrlImagem())) {
            listaMensagens.add(enviarMensagemInfo(usuarioEditor.getNome() + " alterou a imagem da sala.", idSala));
        }

        Set<Usuario> antigos = antiga.getUsuarios();
        Set<Usuario> novos = nova.getUsuarios();

        for (Usuario antigo : antigos) {
            if (novos.stream().noneMatch(u -> u.getIdUsuario().equals(antigo.getIdUsuario()))) {
                listaMensagens.add(enviarMensagemInfo(usuarioEditor.getNome() + " removeu " + antigo.getNome() + " da sala.", idSala));
            }
        }

        for (Usuario novo : novos) {
            if (antigos.stream().noneMatch(u -> u.getIdUsuario().equals(novo.getIdUsuario()))) {
                listaMensagens.add(enviarMensagemInfo(usuarioEditor.getNome() + " adicionou " + novo.getNome() + " à sala.", idSala));
            }
        }
        return listaMensagens;
    }

    public ChatMensagemUnificadaDto enviarMensagem(MensagemRequestDto mensagemRequestDto) {
        ChatMensagemUnificadaDto msg = mensagemService.cadastrarMensagem(mensagemRequestDto);
        //publisher.enviarParaSala(mensagemRequestDto.getFkSala(), msg);
        return msg;
    }

    public ChatMensagemUnificadaDto enviarMensagemInfo(String conteudo, Integer idSala) {
        ChatMensagemUnificadaDto msg = mensagemService.cadastrarMensagemInfo(new MensagemInfoRequestDto(conteudo, LocalDateTime.now(), idSala));
        //publisher.enviarParaSala(idSala, msg);
        return msg;
    }
}