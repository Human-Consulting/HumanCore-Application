package com.humanconsulting.humancore_api.infrastructure.repositories.adapters;

import com.humanconsulting.humancore_api.domain.entities.Mensagem;
import com.humanconsulting.humancore_api.domain.entities.Sala;
import com.humanconsulting.humancore_api.domain.repositories.MensagemRepository;
import com.humanconsulting.humancore_api.infrastructure.entities.MensagemEntity;
import com.humanconsulting.humancore_api.infrastructure.mappers.MensagemMapper;
import com.humanconsulting.humancore_api.infrastructure.mappers.SalaMapper;
import com.humanconsulting.humancore_api.infrastructure.repositories.jpa.JpaMensagemRepository;

import java.util.List;
import java.util.stream.Collectors;

public class MensagemRepositoryAdapter implements MensagemRepository {
    private final JpaMensagemRepository jpaMensagemRepository;

    public MensagemRepositoryAdapter(JpaMensagemRepository jpaMensagemRepository) {
        this.jpaMensagemRepository = jpaMensagemRepository;
    }

    @Override
    public List<Mensagem> findBySalaOrderByHorarioAsc(Sala sala) {
        return jpaMensagemRepository.findBySalaOrderByHorarioAsc(SalaMapper.toEntity(sala))
                .stream()
                .map(MensagemMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Mensagem save(Mensagem mensagem) {
        MensagemEntity entity = MensagemMapper.toEntity(mensagem);
        MensagemEntity saved = jpaMensagemRepository.save(entity);
        return MensagemMapper.toDomain(saved);
    }

    @Override
    public List<Mensagem> findById(Integer id) {
        return (List<Mensagem>) jpaMensagemRepository.findById(id)
                .map(MensagemMapper::toDomain)
                .orElse(null);
    }

    @Override
    public List<Mensagem> findAll() {
        return jpaMensagemRepository.findAll()
                .stream()
                .map(MensagemMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(Integer id) {
        jpaMensagemRepository.deleteById(id);
    }
}
