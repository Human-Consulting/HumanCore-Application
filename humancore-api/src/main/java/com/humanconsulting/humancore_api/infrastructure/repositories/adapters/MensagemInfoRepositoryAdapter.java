package com.humanconsulting.humancore_api.infrastructure.repositories.adapters;

import com.humanconsulting.humancore_api.domain.entities.MensagemInfo;
import com.humanconsulting.humancore_api.domain.entities.Sala;
import com.humanconsulting.humancore_api.domain.repositories.MensagemInfoRepository;
import com.humanconsulting.humancore_api.infrastructure.entities.MensagemInfoEntity;
import com.humanconsulting.humancore_api.infrastructure.mappers.MensagemInfoMapper;
import com.humanconsulting.humancore_api.infrastructure.mappers.SalaMapper;
import com.humanconsulting.humancore_api.infrastructure.repositories.jpa.JpaMensagemInfoRepository;

import java.util.List;
import java.util.stream.Collectors;

public class MensagemInfoRepositoryAdapter implements MensagemInfoRepository {
    private final JpaMensagemInfoRepository jpaMensagemInfoRepository;

    public MensagemInfoRepositoryAdapter(JpaMensagemInfoRepository jpaMensagemInfoRepository) {
        this.jpaMensagemInfoRepository = jpaMensagemInfoRepository;
    }

    @Override
    public List<MensagemInfo> findBySalaOrderByHorarioAsc(Sala sala) {
        return jpaMensagemInfoRepository.findBySalaOrderByHorarioAsc(SalaMapper.toEntity(sala))
                .stream()
                .map(MensagemInfoMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public MensagemInfo save(MensagemInfo mensagemInfo) {
        MensagemInfoEntity entity = MensagemInfoMapper.toEntity(mensagemInfo);
        MensagemInfoEntity saved = jpaMensagemInfoRepository.save(entity);
        return MensagemInfoMapper.toDomain(saved);
    }

    @Override
    public MensagemInfo findById(Integer id) {
        return jpaMensagemInfoRepository.findById(id)
                .map(MensagemInfoMapper::toDomain)
                .orElse(null);
    }

    @Override
    public List<MensagemInfo> findAll() {
        return jpaMensagemInfoRepository.findAll()
                .stream()
                .map(MensagemInfoMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(Integer id) {
        jpaMensagemInfoRepository.deleteById(id);
    }
}
