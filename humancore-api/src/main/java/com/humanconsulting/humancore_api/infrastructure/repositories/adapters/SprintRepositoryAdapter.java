package com.humanconsulting.humancore_api.infrastructure.repositories.adapters;

import com.humanconsulting.humancore_api.domain.entities.Sprint;
import com.humanconsulting.humancore_api.domain.exception.EntidadeNaoEncontradaException;
import com.humanconsulting.humancore_api.domain.repositories.SprintRepository;
import com.humanconsulting.humancore_api.infrastructure.entities.ProjetoEntity;
import com.humanconsulting.humancore_api.infrastructure.entities.SprintEntity;
import com.humanconsulting.humancore_api.infrastructure.mappers.SprintMapper;
import com.humanconsulting.humancore_api.infrastructure.repositories.jpa.JpaSprintRepository;

import java.util.List;
import java.util.Optional;

public class SprintRepositoryAdapter implements SprintRepository {
    private final JpaSprintRepository jpaSprintRepository;

    public SprintRepositoryAdapter(JpaSprintRepository jpaSprintRepository) {
        this.jpaSprintRepository = jpaSprintRepository;
    }

    @Override
    public Sprint save(Sprint sprint) {
        SprintEntity entity = null;
        if (sprint.getIdSprint() != null) {
            entity = jpaSprintRepository.findById(sprint.getIdSprint())
                    .orElseThrow(() -> new EntidadeNaoEncontradaException("Sprint não encontrada"));
        } else entity = new SprintEntity();

        entity.setTitulo(sprint.getTitulo());
        entity.setDescricao(sprint.getDescricao());
        entity.setDtInicio(sprint.getDtInicio());
        entity.setDtFim(sprint.getDtFim());

        SprintEntity saved = jpaSprintRepository.save(entity);
        return SprintMapper.toDomain(saved);
    }

    @Override
    public Optional<Sprint> findById(Integer id) {
        return Optional.ofNullable(jpaSprintRepository.findById(id)
                .map(SprintMapper::toDomain)
                .orElse(null));
    }

    @Override
    public List<Sprint> findAll() {
        return jpaSprintRepository.findAll()
                .stream()
                .map(SprintMapper::toDomain)
                .toList();
    }

    @Override
    public void deleteById(Integer id) {
        jpaSprintRepository.deleteById(id);
    }

    @Override
    public List<Sprint> findByProjeto_IdProjeto(Integer idProjeto) {
        return jpaSprintRepository.findByProjeto_IdProjeto(idProjeto)
                .stream()
                .map(SprintMapper::toDomain)
                .toList();
    }
}
