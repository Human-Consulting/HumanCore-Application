package com.humanconsulting.humancore_api.novo.application.usecases.mensagem.mappers;

import com.humanconsulting.humancore_api.novo.domain.entities.Mensagem;
import com.humanconsulting.humancore_api.novo.domain.entities.MensagemInfo;
import com.humanconsulting.humancore_api.novo.web.dtos.response.chat.ChatMensagemUnificadaDto;
import com.humanconsulting.humancore_api.novo.web.mappers.MensagemMapper;

public class MensagemResponseMapper {
    public ChatMensagemUnificadaDto toResponse(Mensagem mensagem) {
        return MensagemMapper.toMensagemUnificadaResponse(mensagem);
    }
    public ChatMensagemUnificadaDto toResponse(MensagemInfo mensagemInfo) {
        return MensagemMapper.toMensagemUnificadaResponse(mensagemInfo);
    }
}

