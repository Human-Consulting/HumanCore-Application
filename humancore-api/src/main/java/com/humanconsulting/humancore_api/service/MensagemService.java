package com.humanconsulting.humancore_api.service;

import com.humanconsulting.humancore_api.controller.dto.atualizar.mensagem.MensagemAtualizarRequestDto;
import com.humanconsulting.humancore_api.controller.dto.request.MensagemRequestDto;
import com.humanconsulting.humancore_api.controller.dto.request.UsuarioPermissaoDto;
import com.humanconsulting.humancore_api.controller.dto.response.mensagem.MensagemResponseDto;
import com.humanconsulting.humancore_api.exception.EntidadeNaoEncontradaException;
import com.humanconsulting.humancore_api.exception.EntidadeSemRetornoException;
import com.humanconsulting.humancore_api.mapper.MensagemMapper;
import com.humanconsulting.humancore_api.model.Mensagem;
import com.humanconsulting.humancore_api.model.Usuario;
import com.humanconsulting.humancore_api.repository.MensagemRepository;
import com.humanconsulting.humancore_api.repository.SalaRepository;
import com.humanconsulting.humancore_api.repository.UsuarioRepository;
import com.humanconsulting.humancore_api.security.PermissaoValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MensagemService {
    @Autowired private MensagemRepository mensagemRepository;
    @Autowired private UsuarioRepository usuarioRepository;
    @Autowired private SalaRepository salaRepository;

    public MensagemResponseDto cadastrar(MensagemRequestDto mensagemRequestDto) {
//        PermissaoValidator.validarPermissao(mensagemRequestDto.getPermissaoEditor(), "ADICIONAR_MENSAGEM");

        Mensagem mensagemCadastrada = mensagemRepository.save(MensagemMapper.toEntity(mensagemRequestDto, usuarioRepository.findById(mensagemRequestDto.getFkUsuario()).get(), salaRepository.findById(mensagemRequestDto.getFkSala()).get()));
        return passarParaResponse(mensagemCadastrada);
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
