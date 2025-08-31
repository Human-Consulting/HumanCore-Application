package com.humanconsulting.humancore_api.novo.application.usecases.mensagem;

import com.humanconsulting.humancore_api.novo.domain.entities.MensagemInfo;
import com.humanconsulting.humancore_api.novo.domain.repositories.MensagemInfoRepository;
import com.humanconsulting.humancore_api.novo.domain.repositories.SalaRepository;
import com.humanconsulting.humancore_api.novo.web.dtos.request.MensagemInfoRequestDto;
import com.humanconsulting.humancore_api.novo.web.dtos.response.chat.ChatMensagemUnificadaDto;
import com.humanconsulting.humancore_api.novo.web.mappers.MensagemMapper;

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

