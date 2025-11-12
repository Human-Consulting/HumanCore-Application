package com.humanconsulting.humancore_api.application.usecases.sprint;

import com.humanconsulting.humancore_api.application.usecases.sprint.mappers.SprintResponseMapper;
import com.humanconsulting.humancore_api.domain.entities.Sprint;
import com.humanconsulting.humancore_api.domain.entities.Usuario;
import com.humanconsulting.humancore_api.domain.exception.EntidadeNaoEncontradaException;
import com.humanconsulting.humancore_api.domain.repositories.SprintRepository;
import com.humanconsulting.humancore_api.domain.repositories.UsuarioRepository;
import com.humanconsulting.humancore_api.domain.security.ValidarPermissao;
import com.humanconsulting.humancore_api.web.dtos.atualizar.sprint.SprintAtualizarRequestDto;
import com.humanconsulting.humancore_api.web.dtos.response.sprint.SprintResponseDto;
import com.humanconsulting.humancore_api.web.mappers.SprintMapper;

import java.util.Optional;

public class AtualizarSprintUseCase {
    private final SprintRepository sprintRepository;
    private final UsuarioRepository usuarioRepository;
    private final SprintResponseMapper sprintResponseMapper;

    public AtualizarSprintUseCase(SprintRepository sprintRepository, UsuarioRepository usuarioRepository, SprintResponseMapper sprintResponseMapper) {
        this.sprintRepository = sprintRepository;
        this.usuarioRepository = usuarioRepository;
        this.sprintResponseMapper = sprintResponseMapper;
    }

    public SprintResponseDto execute(Integer idSprint, SprintAtualizarRequestDto request) {
        Optional<Sprint> optSprint = sprintRepository.findById(idSprint);
        if (optSprint.isEmpty()) throw new EntidadeNaoEncontradaException("SprintEntity não encontrada.");
        Optional<Usuario> optUsuarioEditor = usuarioRepository.findById(request.getIdEditor());
        if (optUsuarioEditor.isEmpty()) throw new EntidadeNaoEncontradaException("Usuário não encontrado.");
        ValidarPermissao.execute(request.getPermissaoEditor(), "MODIFICAR_SPRINT");
        Sprint sprintAtualizada = sprintRepository.save(SprintMapper.toEntity(request, idSprint, optSprint.get().getProjeto()));
        return sprintResponseMapper.toResponse(sprintAtualizada);
    }
}

