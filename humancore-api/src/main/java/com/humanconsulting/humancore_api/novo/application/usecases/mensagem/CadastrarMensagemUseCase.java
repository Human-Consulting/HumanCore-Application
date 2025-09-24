package com.humanconsulting.humancore_api.novo.application.usecases.mensagem;

import com.humanconsulting.humancore_api.novo.domain.repositories.MensagemRepository;
import com.humanconsulting.humancore_api.novo.domain.repositories.SalaRepository;
import com.humanconsulting.humancore_api.novo.domain.repositories.UsuarioRepository;
import com.humanconsulting.humancore_api.novo.web.dtos.request.MensagemRequestDto;
import com.humanconsulting.humancore_api.novo.web.dtos.response.chat.ChatMensagemUnificadaDto;
import com.humanconsulting.humancore_api.novo.web.mappers.MensagemMapper;

public class CadastrarMensagemUseCase {
    private final MensagemRepository mensagemRepository;
    private final UsuarioRepository usuarioRepository;
    private final SalaRepository salaRepository;

    public CadastrarMensagemUseCase(MensagemRepository mensagemRepository, UsuarioRepository usuarioRepository, SalaRepository salaRepository) {
        this.mensagemRepository = mensagemRepository;
        this.usuarioRepository = usuarioRepository;
        this.salaRepository = salaRepository;
    }

    public ChatMensagemUnificadaDto execute(MensagemRequestDto mensagemRequestDto) {
        var mensagemCadastrada = mensagemRepository.save(
            MensagemMapper.toEntity(
                mensagemRequestDto,
                usuarioRepository.findById(mensagemRequestDto.getFkUsuario()).get(),
                salaRepository.findById(mensagemRequestDto.getFkSala()).get()
            )
        );
        return MensagemMapper.toMensagemUnificadaResponse(mensagemCadastrada);
    }
}

