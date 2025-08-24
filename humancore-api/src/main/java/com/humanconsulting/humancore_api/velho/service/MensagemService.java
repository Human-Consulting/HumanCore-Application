package com.humanconsulting.humancore_api.velho.service;

import com.humanconsulting.humancore_api.velho.controller.dto.atualizar.mensagem.MensagemAtualizarRequestDto;
import com.humanconsulting.humancore_api.velho.controller.dto.request.MensagemInfoRequestDto;
import com.humanconsulting.humancore_api.velho.controller.dto.request.MensagemRequestDto;
import com.humanconsulting.humancore_api.velho.controller.dto.request.UsuarioPermissaoDto;
import com.humanconsulting.humancore_api.velho.controller.dto.response.chat.ChatMensagemUnificadaDto;
import com.humanconsulting.humancore_api.velho.controller.dto.response.mensagem.MensagemResponseDto;
import com.humanconsulting.humancore_api.velho.exception.EntidadeNaoEncontradaException;
import com.humanconsulting.humancore_api.velho.exception.EntidadeSemRetornoException;
import com.humanconsulting.humancore_api.velho.mapper.MensagemMapper;
import com.humanconsulting.humancore_api.velho.model.Mensagem;
import com.humanconsulting.humancore_api.velho.model.MensagemInfo;
import com.humanconsulting.humancore_api.velho.model.Usuario;
import com.humanconsulting.humancore_api.velho.repository.MensagemInfoRepository;
import com.humanconsulting.humancore_api.velho.repository.MensagemRepository;
import com.humanconsulting.humancore_api.velho.repository.SalaRepository;
import com.humanconsulting.humancore_api.velho.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MensagemService {
    @Autowired private MensagemRepository mensagemRepository;
    @Autowired private MensagemInfoRepository mensagemInfoRepository;
    @Autowired private UsuarioRepository usuarioRepository;
    @Autowired private SalaRepository salaRepository;

    public ChatMensagemUnificadaDto cadastrarMensagem(MensagemRequestDto mensagemRequestDto) {
        Mensagem mensagemCadastrada = mensagemRepository.save(MensagemMapper.toEntity(mensagemRequestDto, usuarioRepository.findById(mensagemRequestDto.getFkUsuario()).get(), salaRepository.findById(mensagemRequestDto.getFkSala()).get()));
        return MensagemMapper.toMensagemUnificadaResponse(mensagemCadastrada);
    }

    public ChatMensagemUnificadaDto cadastrarMensagemInfo(MensagemInfoRequestDto mensagemInfoRequestDto) {
        MensagemInfo mensagemInfo = mensagemInfoRepository.save(MensagemMapper.toEntity(mensagemInfoRequestDto, salaRepository.findById(mensagemInfoRequestDto.getFkSala()).get()));
        return MensagemMapper.toMensagemUnificadaResponse(mensagemInfo);
    }

    public Mensagem buscarPorId(Integer id) {
        return mensagemRepository.findById(id).get();
    }

    public List<MensagemResponseDto> listar() {
        List<Mensagem> all = mensagemRepository.findAll();
        if (all.isEmpty()) throw new EntidadeSemRetornoException("Nenhuma mensagem registrada");
        List<MensagemResponseDto> allResponses = new ArrayList<>();
        for (Mensagem mensagem : all) {
            allResponses.add(passarParaResponse(mensagem));
        }
        return allResponses;
    }

    public void deletar(Integer id, UsuarioPermissaoDto usuarioPermissaoDto) {
//        PermissaoValidator.validarPermissao(usuarioPermissaoDto.getPermissaoEditor(), "EXCLUIR_MENSAGEM");

        Optional<Mensagem> optMensagem = mensagemRepository.findById(id);
        if (optMensagem.isEmpty()) throw new EntidadeNaoEncontradaException("Mensagem não encontrada.");
        mensagemRepository.deleteById(id);
    }

    public MensagemResponseDto atualizar(Integer idMensagem, MensagemAtualizarRequestDto request) {
        Optional<Usuario> optUsuarioEditor = usuarioRepository.findById(request.getIdEditor());

        if (optUsuarioEditor.isEmpty()) throw new EntidadeNaoEncontradaException("Usuário não encontrado.");

//        PermissaoValidator.validarPermissao(request.getPermissaoEditor(), "MODIFICAR_MENSAGEM");

        Mensagem mensagemAtualizada = mensagemRepository.save(MensagemMapper.toEntity(request, idMensagem, usuarioRepository.findById(request.getFkUsuario()).get(), salaRepository.findById(request.getFkSala()).get()));
        return passarParaResponse(mensagemAtualizada);
    }

    public MensagemResponseDto passarParaResponse(Mensagem mensagem) {
        return MensagemMapper.toDto(mensagem);
    }
}
