package com.humanconsulting.humancore_api.service;

import com.humanconsulting.humancore_api.controller.dto.atualizar.sala.SalaAtualizarRequestDto;
import com.humanconsulting.humancore_api.controller.dto.request.SalaRequestDto;
import com.humanconsulting.humancore_api.controller.dto.request.UsuarioPermissaoDto;
import com.humanconsulting.humancore_api.controller.dto.response.sala.SalaResponseDto;
import com.humanconsulting.humancore_api.exception.EntidadeNaoEncontradaException;
import com.humanconsulting.humancore_api.exception.EntidadeSemRetornoException;
import com.humanconsulting.humancore_api.mapper.SalaMapper;
import com.humanconsulting.humancore_api.model.Sala;
import com.humanconsulting.humancore_api.model.Usuario;
import com.humanconsulting.humancore_api.repository.SalaRepository;
import com.humanconsulting.humancore_api.repository.UsuarioRepository;
import com.humanconsulting.humancore_api.security.PermissaoValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SalaService {
    @Autowired private SalaRepository salaRepository;
    @Autowired private UsuarioRepository usuarioRepository;

    public SalaResponseDto cadastrar(SalaRequestDto salaRequestDto) {
//        PermissaoValidator.validarPermissao(salaRequestDto.getPermissaoEditor(), "ADICIONAR_SALA");

        Sala salaCadastrada = salaRepository.save(SalaMapper.toEntity(salaRequestDto));
        return passarParaResponse(salaCadastrada);
    }

    public Sala buscarPorId(Integer id) {
        return salaRepository.findById(id).get();
    }

    public List<SalaResponseDto> listar() {
        List<Sala> all = salaRepository.findAll();
        if (all.isEmpty()) throw new EntidadeSemRetornoException("Nenhuma sala registrada");
        List<SalaResponseDto> allResponses = new ArrayList<>();
        for (Sala sala : all) {
            allResponses.add(passarParaResponse(sala));
        }
        return allResponses;
    }

    public void deletar(Integer id, UsuarioPermissaoDto usuarioPermissaoDto) {
//        PermissaoValidator.validarPermissao(usuarioPermissaoDto.getPermissaoEditor(), "EXCLUIR_SALA");

        Optional<Sala> optSala = salaRepository.findById(id);
        if (optSala.isEmpty()) throw new EntidadeNaoEncontradaException("Sala não encontrada.");
        salaRepository.deleteById(id);
    }

    public SalaResponseDto atualizar(Integer idSala, SalaAtualizarRequestDto request) {
        Optional<Usuario> optUsuarioEditor = usuarioRepository.findById(request.getIdEditor());

        if (optUsuarioEditor.isEmpty()) throw new EntidadeNaoEncontradaException("Usuário não encontrado.");

//        PermissaoValidator.validarPermissao(request.getPermissaoEditor(), "MODIFICAR_SALA");

        Sala salaAtualizada = salaRepository.save(SalaMapper.toEntity(request, idSala));
        return passarParaResponse(salaAtualizada);
    }

    public SalaResponseDto passarParaResponse(Sala sala) {
        return SalaMapper.toDto(sala);
    }
}
