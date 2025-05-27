package com.humanconsulting.humancore_api.mapper;

import com.humanconsulting.humancore_api.controller.dto.atualizar.mensagem.MensagemAtualizarRequestDto;
import com.humanconsulting.humancore_api.controller.dto.request.MensagemRequestDto;
import com.humanconsulting.humancore_api.controller.dto.response.mensagem.MensagemResponseDto;
import com.humanconsulting.humancore_api.model.*;

public class MensagemMapper {
    public static Mensagem toEntity(MensagemRequestDto mensagemRequestDto, Usuario usuario, Sala sala) {
        return Mensagem.builder()
                .conteudo(mensagemRequestDto.getConteudo())
                .horario(mensagemRequestDto.getHorario())
                .usuario(usuario)
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
}
