package com.humanconsulting.humancore_api.novo.application.usecases.mensagem;

import com.humanconsulting.humancore_api.novo.domain.entities.Mensagem;
import com.humanconsulting.humancore_api.novo.domain.exception.EntidadeNaoEncontradaException;
import com.humanconsulting.humancore_api.novo.domain.repositories.MensagemRepository;
import com.humanconsulting.humancore_api.novo.domain.repositories.SalaRepository;
import com.humanconsulting.humancore_api.novo.domain.repositories.UsuarioRepository;
import com.humanconsulting.humancore_api.novo.web.dtos.atualizar.mensagem.MensagemAtualizarRequestDto;
import com.humanconsulting.humancore_api.novo.web.dtos.request.MensagemRequestDto;
import com.humanconsulting.humancore_api.novo.web.dtos.response.chat.ChatMensagemUnificadaDto;
import com.humanconsulting.humancore_api.novo.web.mappers.MensagemMapper;

public class AtualizarMensagemUseCase {
    private final MensagemRepository mensagemRepository;
    private final UsuarioRepository usuarioRepository;
    private final SalaRepository salaRepository;
    private final BuscarMensagemPorIdUseCase buscarMensagemPorIdUseCase;

    public AtualizarMensagemUseCase(MensagemRepository mensagemRepository, UsuarioRepository usuarioRepository, SalaRepository salaRepository, BuscarMensagemPorIdUseCase buscarMensagemPorIdUseCase) {
        this.mensagemRepository = mensagemRepository;
        this.usuarioRepository = usuarioRepository;
        this.salaRepository = salaRepository;
        this.buscarMensagemPorIdUseCase = buscarMensagemPorIdUseCase;
    }

    public ChatMensagemUnificadaDto execute(Integer idMensagem, MensagemAtualizarRequestDto mensagemRequestDto) {
        Mensagem mensagemOriginal = buscarMensagemPorIdUseCase.execute(idMensagem);
        var usuarioOpt = usuarioRepository.findById(mensagemRequestDto.getFkUsuario());
        if (usuarioOpt.isEmpty()) throw new EntidadeNaoEncontradaException("Usuário não encontrado.");

        var usuario = usuarioOpt.get();
        var salaOpt = salaRepository.findById(mensagemRequestDto.getFkSala());
        if (salaOpt.isEmpty()) throw new EntidadeNaoEncontradaException("Sala não encontrada.");
        var sala = salaOpt.get();
        Mensagem mensagemAtualizada = mensagemRepository.save(MensagemMapper.toEntity(mensagemRequestDto, idMensagem, usuario, sala));
        return MensagemMapper.toMensagemUnificadaResponse(mensagemAtualizada);
    }
}