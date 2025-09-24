package com.humanconsulting.humancore_api.novo.application.usecases.sala;

import com.humanconsulting.humancore_api.novo.application.usecases.sala.mappers.SalaResponseMapper;
import com.humanconsulting.humancore_api.novo.domain.entities.Empresa;
import com.humanconsulting.humancore_api.novo.domain.entities.Projeto;
import com.humanconsulting.humancore_api.novo.domain.entities.Sala;
import com.humanconsulting.humancore_api.novo.domain.entities.Usuario;
import com.humanconsulting.humancore_api.novo.domain.exception.EntidadeNaoEncontradaException;
import com.humanconsulting.humancore_api.novo.domain.notifiers.SalaNotifier;
import com.humanconsulting.humancore_api.novo.domain.repositories.EmpresaRepository;
import com.humanconsulting.humancore_api.novo.domain.repositories.ProjetoRepository;
import com.humanconsulting.humancore_api.novo.domain.repositories.SalaRepository;
import com.humanconsulting.humancore_api.novo.domain.repositories.UsuarioRepository;
import com.humanconsulting.humancore_api.novo.web.dtos.request.SalaRequestDto;
import com.humanconsulting.humancore_api.novo.web.dtos.response.sala.SalaResponseDto;
import com.humanconsulting.humancore_api.novo.web.mappers.SalaMapper;

import java.util.HashSet;
import java.util.Set;

public class AtualizarSalaUseCase {
    private final SalaRepository salaRepository;
    private final UsuarioRepository usuarioRepository;
    private final ProjetoRepository projetoRepository;
    private final EmpresaRepository empresaRepository;
    private final SalaNotifier salaNotifier;
    private final SalaResponseMapper salaResponseMapper;

    public AtualizarSalaUseCase(SalaRepository salaRepository, UsuarioRepository usuarioRepository, ProjetoRepository projetoRepository, EmpresaRepository empresaRepository, SalaNotifier salaNotifier, SalaResponseMapper salaResponseMapper) {
        this.salaRepository = salaRepository;
        this.usuarioRepository = usuarioRepository;
        this.projetoRepository = projetoRepository;
        this.empresaRepository = empresaRepository;
        this.salaNotifier = salaNotifier;
        this.salaResponseMapper = salaResponseMapper;
    }

    public SalaResponseDto execute(Integer idSala, SalaRequestDto request) {
        Sala salaOriginal = salaRepository.buscarComUsuarios(idSala)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("SalaEntity não encontrada."));

        Set<Usuario> novosParticipantes = new HashSet<>();
        for (Integer participanteId : request.getParticipantes()) {
            novosParticipantes.add(usuarioRepository.findById(participanteId).orElseThrow(() -> new EntidadeNaoEncontradaException("Usuário não encontrado.")));
        }

        if (request.getUrlImagem().isEmpty()) request.setUrlImagem(salaOriginal.getUrlImagem());

        Projeto projeto = request.getFkProjeto() != null
                ? projetoRepository.findById(request.getFkProjeto()).orElse(null)
                : null;

        Empresa empresa = request.getFkEmpresa() != null
                ? empresaRepository.findById(request.getFkEmpresa()).orElse(null)
                : null;

        Sala salaAtualizada = SalaMapper.toEntity(request, idSala, novosParticipantes, empresa, projeto);
        salaRepository.save(salaAtualizada);
        salaNotifier.notificarAtualizacoesSala(salaOriginal, salaAtualizada, usuarioRepository.findById(request.getIdEditor()).orElseThrow(() -> new EntidadeNaoEncontradaException("Usuário editor não encontrado.")));
        return salaResponseMapper.toResponse(salaAtualizada);
    }
}

