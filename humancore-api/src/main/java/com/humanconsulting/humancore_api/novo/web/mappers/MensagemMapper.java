package com.humanconsulting.humancore_api.novo.web.mappers;

import com.humanconsulting.humancore_api.novo.domain.entities.Mensagem;
import com.humanconsulting.humancore_api.novo.domain.entities.MensagemInfo;
import com.humanconsulting.humancore_api.novo.domain.entities.Sala;
import com.humanconsulting.humancore_api.novo.domain.entities.Usuario;
import com.humanconsulting.humancore_api.novo.web.dtos.atualizar.mensagem.MensagemAtualizarRequestDto;
import com.humanconsulting.humancore_api.novo.web.dtos.request.MensagemInfoRequestDto;
import com.humanconsulting.humancore_api.novo.web.dtos.request.MensagemRequestDto;
import com.humanconsulting.humancore_api.novo.web.dtos.response.chat.ChatMensagemUnificadaDto;
import com.humanconsulting.humancore_api.novo.web.dtos.response.mensagem.MensagemResponseDto;

public class MensagemMapper {
    public static Mensagem toEntity(MensagemRequestDto mensagemRequestDto, Usuario usuario, Sala sala) {
        Mensagem mensagem = new Mensagem();
        mensagem.setConteudo(mensagemRequestDto.getConteudo());
        mensagem.setHorario(mensagemRequestDto.getHorario());
        mensagem.setUsuario(usuario);
        mensagem.setSala(sala);
        return mensagem;
    }

    public static MensagemInfo toEntity(MensagemInfoRequestDto mensagemInfoRequestDto, Sala sala) {
        MensagemInfo mensagemInfo = new MensagemInfo();
        mensagemInfo.setConteudo(mensagemInfoRequestDto.getConteudo());
        mensagemInfo.setHorario(mensagemInfoRequestDto.getHorario());
        mensagemInfo.setSala(sala);
        return mensagemInfo;
    }

    public static Mensagem toEntity(MensagemAtualizarRequestDto mensagemAtualizarRequestDto, Integer idMensagem, Usuario usuario, Sala sala) {
        Mensagem mensagem = new Mensagem();
        mensagem.setIdMensagem(idMensagem);
        mensagem.setConteudo(mensagemAtualizarRequestDto.getConteudo());
        mensagem.setHorario(mensagemAtualizarRequestDto.getHorario());
        mensagem.setUsuario(usuario);
        mensagem.setSala(sala);
        return mensagem;
    }

    public static MensagemResponseDto toDto(Mensagem mensagem) {
        return MensagemResponseDto.builder()
                .idMensagem(mensagem.getIdMensagem())
                .conteudo(mensagem.getConteudo())
                .horario(mensagem.getHorario())
                .fkUsuario(mensagem.getUsuario().getIdUsuario())
                .fkSala(mensagem.getSala().getIdSala())
                .build();
    }

    public static ChatMensagemUnificadaDto toMensagemUnificadaResponse(MensagemInfo m) {
        return new ChatMensagemUnificadaDto(
                m.getIdMensagemInfo(),
                null,
                m.getConteudo(),
                m.getHorario(),
                true
        );
    }

    public static ChatMensagemUnificadaDto toMensagemUnificadaResponse(Mensagem m) {
        return new ChatMensagemUnificadaDto(
                m.getIdMensagem(),
                m.getUsuario().getIdUsuario(),
                m.getConteudo(),
                m.getHorario(),
                false
        );
    }
}