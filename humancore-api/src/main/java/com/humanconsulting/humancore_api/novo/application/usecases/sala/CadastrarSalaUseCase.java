package com.humanconsulting.humancore_api.novo.application.usecases.sala;

import com.humanconsulting.humancore_api.novo.application.usecases.sala.mappers.SalaResponseMapper;
import com.humanconsulting.humancore_api.novo.domain.entities.Sala;
import com.humanconsulting.humancore_api.novo.domain.entities.Usuario;
import com.humanconsulting.humancore_api.novo.domain.repositories.SalaRepository;
import com.humanconsulting.humancore_api.novo.domain.repositories.UsuarioRepository;
import com.humanconsulting.humancore_api.novo.web.dtos.request.MensagemInfoRequestDto;
import com.humanconsulting.humancore_api.novo.web.dtos.request.SalaRequestDto;
import com.humanconsulting.humancore_api.novo.web.dtos.response.sala.SalaResponseDto;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

public class CadastrarSalaUseCase {
    private final SalaRepository salaRepository;
    private final UsuarioRepository usuarioRepository;
    private final MensagemService mensagemService;
    private final SalaResponseMapper salaResponseMapper;

    public CadastrarSalaUseCase(SalaRepository salaRepository, UsuarioRepository usuarioRepository, MensagemService mensagemService, SalaResponseMapper salaResponseMapper) {
        this.salaRepository = salaRepository;
        this.usuarioRepository = usuarioRepository;
        this.mensagemService = mensagemService;
        this.salaResponseMapper = salaResponseMapper;
    }

    public SalaResponseDto execute(SalaRequestDto salaRequestDto) {
        Set<Usuario> participantesIniciais = new HashSet<>();
        for (Integer participante : salaRequestDto.getParticipantes()) {
            participantesIniciais.add(usuarioRepository.findById(participante).orElseThrow(() -> new EntidadeNaoEncontradaException("Usuário não encontrado.")));
        }
        participantesIniciais.add(usuarioRepository.findById(salaRequestDto.getIdEditor()).orElseThrow(() -> new EntidadeNaoEncontradaException("Usuário editor não encontrado.")));
        Sala salaCadastrada = salaRepository.save(com.humanconsulting.humancore_api.velho.mapper.SalaMapper.toEntity(salaRequestDto, participantesIniciais));
        MensagemInfoRequestDto mensagemInfoRequest = new MensagemInfoRequestDto("Conversa criada.", LocalDateTime.now(), salaCadastrada.getIdSala());
        mensagemService.cadastrarMensagemInfo(mensagemInfoRequest);
        return salaResponseMapper.toResponse(salaCadastrada);
    }
}

