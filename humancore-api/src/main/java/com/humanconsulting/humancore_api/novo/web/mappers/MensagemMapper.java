package com.humanconsulting.humancore_api.novo.web.mappers;

import com.humanconsulting.humancore_api.velho.controller.dto.atualizar.mensagem.MensagemAtualizarRequestDto;
import com.humanconsulting.humancore_api.velho.controller.dto.request.MensagemInfoRequestDto;
import com.humanconsulting.humancore_api.velho.controller.dto.request.MensagemRequestDto;
import com.humanconsulting.humancore_api.velho.controller.dto.response.chat.ChatMensagemUnificadaDto;
import com.humanconsulting.humancore_api.velho.controller.dto.response.mensagem.MensagemResponseDto;
import com.humanconsulting.humancore_api.velho.model.Mensagem;
import com.humanconsulting.humancore_api.velho.model.MensagemInfo;
import com.humanconsulting.humancore_api.velho.model.Sala;
import com.humanconsulting.humancore_api.velho.model.Usuario;

public class MensagemMapper {
    public static Mensagem toEntity(MensagemRequestDto mensagemRequestDto, Usuario usuario, Sala sala) {
        return Mensagem.builder()
                .conteudo(mensagemRequestDto.getConteudo())
                .horario(mensagemRequestDto.getHorario())
                .usuario(usuario)
                .sala(sala)
                .build();
    }

    public static MensagemInfo toEntity(MensagemInfoRequestDto mensagemInfoRequestDto, Sala sala) {
        return MensagemInfo.builder()
                .conteudo(mensagemInfoRequestDto.getConteudo())
                .horario(mensagemInfoRequestDto.getHorario())
                .sala(sala)
                .build();
    }

    public static Mensagem toEntity(MensagemAtualizarRequestDto mensagemAtualizarRequestDto, Integer idMensagem, Usuario usuario, Sala sala) {
        return Mensagem.builder()
                .idMensagem(idMensagem)
                .conteudo(mensagemAtualizarRequestDto.getConteudo())
                .horario(mensagemAtualizarRequestDto.getHorario())
                .usuario(usuario)
                .sala(sala)
                .build();
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
