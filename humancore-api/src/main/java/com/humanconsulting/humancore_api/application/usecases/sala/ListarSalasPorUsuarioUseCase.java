package com.humanconsulting.humancore_api.application.usecases.sala;

import com.humanconsulting.humancore_api.domain.entities.Sala;
import com.humanconsulting.humancore_api.domain.repositories.SalaRepository;
import com.humanconsulting.humancore_api.domain.repositories.MensagemRepository;
import com.humanconsulting.humancore_api.domain.repositories.MensagemInfoRepository;
import com.humanconsulting.humancore_api.web.dtos.response.chat.ChatResponseDto;
import com.humanconsulting.humancore_api.web.dtos.response.chat.ChatUsuarioDto;
import com.humanconsulting.humancore_api.web.dtos.response.chat.ChatMensagemUnificadaDto;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ListarSalasPorUsuarioUseCase {
    private final SalaRepository salaRepository;
    private final MensagemRepository mensagemRepository;
    private final MensagemInfoRepository mensagemInfoRepository;

    public ListarSalasPorUsuarioUseCase(
            SalaRepository salaRepository,
            MensagemRepository mensagemRepository,
            MensagemInfoRepository mensagemInfoRepository
    ) {
        this.salaRepository = salaRepository;
        this.mensagemRepository = mensagemRepository;
        this.mensagemInfoRepository = mensagemInfoRepository;
    }

    public List<ChatResponseDto> execute(Integer idUsuario) {
        List<Sala> salas = salaRepository.findSalasComUsuariosPorUsuario(idUsuario);
        return salas.stream().map(sala -> {
            List<ChatUsuarioDto> participantes = sala.getUsuarios().stream()
                    .map(u -> new ChatUsuarioDto(u.getIdUsuario(), u.getNome()))
                    .toList();

            List<ChatMensagemUnificadaDto> mensagens = mensagemRepository.findBySalaOrderByHorarioAsc(sala)
                    .stream()
                    .map(m -> new ChatMensagemUnificadaDto(
                            m.getIdMensagem(),
                            m.getUsuario().getIdUsuario(),
                            m.getConteudo(),
                            m.getHorario(),
                            false
                    )).collect(Collectors.toList());

            List<ChatMensagemUnificadaDto> mensagensInfo = mensagemInfoRepository.findBySalaOrderByHorarioAsc(sala)
                    .stream()
                    .map(m -> new ChatMensagemUnificadaDto(
                            m.getIdMensagemInfo(),
                            null,
                            m.getConteudo(),
                            m.getHorario(),
                            true
                    )).collect(Collectors.toList());

            List<ChatMensagemUnificadaDto> todasMensagens = Stream.concat(mensagens.stream(), mensagensInfo.stream())
                    .sorted(Comparator.comparing(ChatMensagemUnificadaDto::getHorario))
                    .toList();

            Optional<Integer> fkProjeto = sala.getProjeto() == null ? Optional.empty() : Optional.of(sala.getProjeto().getIdProjeto());
            Optional<Integer> fkEmpresa = sala.getEmpresa() == null ? Optional.empty() : Optional.of(sala.getEmpresa().getIdEmpresa());

            return new ChatResponseDto(
                    sala.getIdSala(),
                    sala.getNome(),
                    sala.getUrlImagem(),
                    fkProjeto,
                    fkEmpresa,
                    participantes,
                    todasMensagens
            );
        }).toList();
    }
}

