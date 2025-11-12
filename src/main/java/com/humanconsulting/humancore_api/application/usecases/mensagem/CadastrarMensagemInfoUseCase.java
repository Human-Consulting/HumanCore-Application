package com.humanconsulting.humancore_api.application.usecases.mensagem;

import com.humanconsulting.humancore_api.domain.entities.MensagemInfo;
import com.humanconsulting.humancore_api.domain.repositories.MensagemInfoRepository;
import com.humanconsulting.humancore_api.domain.repositories.SalaRepository;
import com.humanconsulting.humancore_api.web.dtos.request.MensagemInfoRequestDto;
import com.humanconsulting.humancore_api.web.dtos.response.chat.ChatMensagemUnificadaDto;
import com.humanconsulting.humancore_api.web.mappers.MensagemMapper;

public class CadastrarMensagemInfoUseCase {
    private final MensagemInfoRepository mensagemInfoRepository;
    private final SalaRepository salaRepository;

    public CadastrarMensagemInfoUseCase(MensagemInfoRepository mensagemInfoRepository, SalaRepository salaRepository) {
        this.mensagemInfoRepository = mensagemInfoRepository;
        this.salaRepository = salaRepository;
    }

    public ChatMensagemUnificadaDto execute(MensagemInfoRequestDto mensagemInfoRequestDto) {
        MensagemInfo mensagemInfo = mensagemInfoRepository.save(MensagemMapper.toEntity(mensagemInfoRequestDto, salaRepository.findById(mensagemInfoRequestDto.getFkSala()).get()));
        return MensagemMapper.toMensagemUnificadaResponse(mensagemInfo);
    }
}

