package com.humanconsulting.humancore_api.infrastructure.notifiers;

import com.humanconsulting.humancore_api.domain.entities.*;
import com.humanconsulting.humancore_api.domain.notifiers.SalaNotifier;
import com.humanconsulting.humancore_api.domain.repositories.SalaRepository;
import com.humanconsulting.humancore_api.application.usecases.mensagem.CadastrarMensagemInfoUseCase;
import com.humanconsulting.humancore_api.web.dtos.request.MensagemInfoRequestDto;
import com.humanconsulting.humancore_api.web.dtos.response.chat.ChatMensagemUnificadaDto;
import java.time.LocalDateTime;
import java.util.*;

public class SalaNotifierAdapter implements SalaNotifier {
    private final SalaRepository salaRepository;
    private final CadastrarMensagemInfoUseCase cadastrarMensagemInfoUseCase;

    public SalaNotifierAdapter(
        SalaRepository salaRepository,
        CadastrarMensagemInfoUseCase cadastrarMensagemInfoUseCase
    ) {
        this.salaRepository = salaRepository;
        this.cadastrarMensagemInfoUseCase = cadastrarMensagemInfoUseCase;
    }

    @Override
    public void adicionarUsuarioEmSalaProjeto(Tarefa tarefa, Projeto projeto, Usuario tarefaResponsavel) {
        if (projeto == null || tarefaResponsavel == null) throw new RuntimeException("Projeto e/ou TarefaResponsavel não encontrado.");
        Sala sala = salaRepository.findByProjeto(projeto);
        if (sala == null) System.out.println("Sala não encontrada.");
        if (!sala.getUsuarios().contains(tarefaResponsavel)) {
            sala.getUsuarios().add(tarefaResponsavel);
            salaRepository.save(sala);
        }
    }

    @Override
    public void adicionarUsuarioEmSalaEmpresa(Usuario usuario) {
        if (usuario.getEmpresa() == null) throw new RuntimeException("Empresa não encontrada.");
        Sala sala = salaRepository.findByEmpresa(usuario.getEmpresa());
        if (sala == null) System.out.println("Sala não encontrada.");
        if (!sala.getUsuarios().contains(usuario)) {
            sala.getUsuarios().add(usuario);
            Sala salaCriada = salaRepository.save(sala);
            MensagemInfoRequestDto msg = MensagemInfoRequestDto.builder()
                .conteudo(usuario.getNome() + " entrou na empresa.")
                .horario(LocalDateTime.now())
                .fkSala(salaCriada.getIdSala())
                .build();
            cadastrarMensagemInfoUseCase.execute(msg);
        }
    }

    @Override
    public void onProjetoCriado(Projeto projeto, Usuario editor) {
        Sala novaSala = new Sala();
        Set<Usuario> participantesIniciais = new HashSet<>();
        participantesIniciais.add(editor);
        novaSala.setProjeto(projeto);
        novaSala.setNome(projeto.getTitulo());
        novaSala.setUrlImagem(projeto.getUrlImagem());
        novaSala.setUsuarios(participantesIniciais);
        Sala salaCriada = salaRepository.save(novaSala);
        MensagemInfoRequestDto msgCriada = MensagemInfoRequestDto.builder()
            .conteudo("Conversa criada.")
            .horario(LocalDateTime.now())
            .fkSala(salaCriada.getIdSala())
            .build();
        cadastrarMensagemInfoUseCase.execute(msgCriada);
        for (Usuario participante : participantesIniciais) {
            MensagemInfoRequestDto msg = MensagemInfoRequestDto.builder()
                .conteudo(editor.getNome() + " adicionou " + participante.getNome() + " à sala")
                .horario(LocalDateTime.now())
                .fkSala(salaCriada.getIdSala())
                .build();
            cadastrarMensagemInfoUseCase.execute(msg);
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
        MensagemInfoRequestDto msgCriada = MensagemInfoRequestDto.builder()
            .conteudo("Conversa criada.")
            .horario(LocalDateTime.now())
            .fkSala(salaCriada.getIdSala())
            .build();
        cadastrarMensagemInfoUseCase.execute(msgCriada);
        for (Usuario participante : participantesIniciais) {
            if (!participante.getIdUsuario().equals(editor.getIdUsuario())) {
                MensagemInfoRequestDto msg = MensagemInfoRequestDto.builder()
                    .conteudo(editor.getNome() + " adicionou " + participante.getNome() + " à sala")
                    .horario(LocalDateTime.now())
                    .fkSala(salaCriada.getIdSala())
                    .build();
                cadastrarMensagemInfoUseCase.execute(msg);
            }
        }
    }

    @Override
    public List<ChatMensagemUnificadaDto> notificarAtualizacoesSala(Sala antiga, Sala nova, Usuario usuarioEditor) {
        List<ChatMensagemUnificadaDto> listaMensagens = new ArrayList<>();
        Integer idSala = antiga.getIdSala();

        if (!Objects.equals(antiga.getNome(), nova.getNome())) {
            MensagemInfoRequestDto msg = MensagemInfoRequestDto.builder()
                .conteudo(usuarioEditor.getNome() + " alterou o nome da sala para " + nova.getNome() + ".")
                .horario(LocalDateTime.now())
                .fkSala(idSala)
                .build();
            listaMensagens.add(cadastrarMensagemInfoUseCase.execute(msg));
        }

        if (!Objects.equals(antiga.getUrlImagem(), nova.getUrlImagem())) {
            MensagemInfoRequestDto msg = MensagemInfoRequestDto.builder()
                .conteudo(usuarioEditor.getNome() + " alterou a imagem da sala.")
                .horario(LocalDateTime.now())
                .fkSala(idSala)
                .build();
            listaMensagens.add(cadastrarMensagemInfoUseCase.execute(msg));
        }

        Set<Usuario> antigos = antiga.getUsuarios();
        Set<Usuario> novos = nova.getUsuarios();

        for (Usuario antigo : antigos) {
            if (novos.stream().noneMatch(u -> Objects.equals(u.getIdUsuario(), antigo.getIdUsuario()))) {
                MensagemInfoRequestDto msg = MensagemInfoRequestDto.builder()
                    .conteudo(usuarioEditor.getNome() + " removeu " + antigo.getNome() + " da sala.")
                    .horario(LocalDateTime.now())
                    .fkSala(idSala)
                    .build();
                listaMensagens.add(cadastrarMensagemInfoUseCase.execute(msg));
            }
        }

        for (Usuario novo : novos) {
            if (antigos.stream().noneMatch(u -> Objects.equals(u.getIdUsuario(), novo.getIdUsuario()))) {
                MensagemInfoRequestDto msg = MensagemInfoRequestDto.builder()
                    .conteudo(usuarioEditor.getNome() + " adicionou " + novo.getNome() + " à sala.")
                    .horario(LocalDateTime.now())
                    .fkSala(idSala)
                    .build();
                listaMensagens.add(cadastrarMensagemInfoUseCase.execute(msg));
            }
        }
        return listaMensagens;
    }
}
