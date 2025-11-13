package com.humanconsulting.humancore_api.infrastructure.notifiers;

import com.humanconsulting.humancore_api.application.usecases.mensagem.CadastrarMensagemInfoUseCase;
import com.humanconsulting.humancore_api.domain.entities.Empresa;
import com.humanconsulting.humancore_api.domain.entities.Projeto;
import com.humanconsulting.humancore_api.domain.entities.Sala;
import com.humanconsulting.humancore_api.domain.entities.Usuario;
import com.humanconsulting.humancore_api.domain.notifiers.ProjetoNotifier;
import com.humanconsulting.humancore_api.infrastructure.mappers.MensagemInfoMapper;
import com.humanconsulting.humancore_api.infrastructure.mappers.SalaMapper;
import com.humanconsulting.humancore_api.infrastructure.repositories.adapters.MensagemInfoRepositoryAdapter;
import com.humanconsulting.humancore_api.infrastructure.repositories.adapters.SalaRepositoryAdapter;
import com.humanconsulting.humancore_api.web.dtos.request.MensagemInfoRequestDto;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

public class ProjetoNotifierAdapter implements ProjetoNotifier {
    private final SalaRepositoryAdapter salaRepository;
    private final MensagemInfoRepositoryAdapter mensagemInfoRepository;
    private final SalaMapper salaMapper;
    private final MensagemInfoMapper mensagemInfoMapper;
    private final CadastrarMensagemInfoUseCase cadastrarMensagemInfoUseCase;

    public ProjetoNotifierAdapter(
        SalaRepositoryAdapter salaRepository,
        MensagemInfoRepositoryAdapter mensagemInfoRepository,
        SalaMapper salaMapper,
        MensagemInfoMapper mensagemInfoMapper,
        CadastrarMensagemInfoUseCase cadastrarMensagemInfoUseCase
    ) {
        this.salaRepository = salaRepository;
        this.mensagemInfoRepository = mensagemInfoRepository;
        this.salaMapper = salaMapper;
        this.mensagemInfoMapper = mensagemInfoMapper;
        this.cadastrarMensagemInfoUseCase = cadastrarMensagemInfoUseCase;
    }

    @Override
    public void onProjetoCriado(Projeto projeto, Usuario editor) {
        System.out.println(projeto.getResponsavel().getNome());
        System.out.println(editor.getNome());
        Sala novaSala = new Sala();
        Set<Usuario> participantesIniciais = new HashSet<>();
        participantesIniciais.add(projeto.getResponsavel());
        if (!projeto.getResponsavel().getIdUsuario().equals(editor.getIdUsuario())) participantesIniciais.add(editor);

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
}
