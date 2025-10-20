package com.humanconsulting.humancore_api.infrastructure.repositories.adapters;

import com.humanconsulting.humancore_api.domain.entities.Tarefa;
import com.humanconsulting.humancore_api.domain.repositories.TarefaRepository;
import com.humanconsulting.humancore_api.infrastructure.entities.SalaEntity;
import com.humanconsulting.humancore_api.infrastructure.entities.TarefaEntity;
import com.humanconsulting.humancore_api.infrastructure.mappers.SalaMapper;
import com.humanconsulting.humancore_api.infrastructure.mappers.TarefaMapper;
import com.humanconsulting.humancore_api.infrastructure.repositories.jpa.JpaTarefaRepository;

import java.util.List;
import java.util.Optional;

public class TarefaRepositoryAdapter implements TarefaRepository {
    private final JpaTarefaRepository jpaTarefaRepository;

    public TarefaRepositoryAdapter(JpaTarefaRepository jpaTarefaRepository) {
        this.jpaTarefaRepository = jpaTarefaRepository;
    }

    @Override
    public Tarefa save(Tarefa tarefa) {
        TarefaEntity entity = TarefaMapper.toEntity(tarefa);
        TarefaEntity saved = jpaTarefaRepository.save(entity);
        return TarefaMapper.toDomain(saved);
    }

    @Override
    public Optional<Tarefa> findById(Integer id) {
        return Optional.ofNullable(jpaTarefaRepository.findById(id)
                .map(TarefaMapper::toDomain)
                .orElse(null));
    }

    @Override
    public List<Tarefa> findAll() {
        return jpaTarefaRepository.findAll()
                .stream()
                .map(TarefaMapper::toDomain)
                .toList();
    }

    @Override
    public List<Tarefa> findByProjetoAndSprint(Integer idProjeto, Integer idSprint) {
        return jpaTarefaRepository.findByProjetoAndSprint(idProjeto, idSprint)
                .stream()
                .map(TarefaMapper::toDomain)
                .toList();
    }

    @Override
    public List<Tarefa> findBySprint_IdSprint(Integer idSprint) {
        return jpaTarefaRepository.findBySprint_IdSprint(idSprint)
                .stream()
                .map(TarefaMapper::toDomain)
                .toList();
    }

    @Override
    public boolean existsImpedimentoBySprint(Integer idSprint) {
        return jpaTarefaRepository.existsImpedimentoBySprint(idSprint);
    }

    @Override
    public boolean existsImpedimentoByProjeto(Integer idProjeto) {
        return jpaTarefaRepository.existsImpedimentoByProjeto(idProjeto);
    }

    @Override
    public void toggleImpedimento(Integer idTarefa) {
        jpaTarefaRepository.toggleImpedimento(idTarefa);
    }

    @Override
    public void deleteById(Integer id) {
        jpaTarefaRepository.deleteById(id);
    }
}
