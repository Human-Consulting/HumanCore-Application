package com.humanconsulting.humancore_api.novo.infrastructure.repositories.adapters;

import com.humanconsulting.humancore_api.novo.domain.repositories.SalaRepository;
import com.humanconsulting.humancore_api.novo.domain.entities.Sala;
import com.humanconsulting.humancore_api.novo.domain.entities.Empresa;
import com.humanconsulting.humancore_api.novo.domain.entities.Projeto;
import com.humanconsulting.humancore_api.novo.infrastructure.entities.SalaEntity;
import com.humanconsulting.humancore_api.novo.infrastructure.mappers.EmpresaMapper;
import com.humanconsulting.humancore_api.novo.infrastructure.mappers.ProjetoMapper;
import com.humanconsulting.humancore_api.novo.infrastructure.mappers.SalaMapper;
import com.humanconsulting.humancore_api.novo.infrastructure.repositories.jpa.JpaSalaRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class SalaRepositoryAdapter implements SalaRepository {
    private final JpaSalaRepository jpaSalaRepository;

    public SalaRepositoryAdapter(JpaSalaRepository jpaSalaRepository) {
        this.jpaSalaRepository = jpaSalaRepository;
    }

    @Override
    public Sala findByProjeto(Projeto projeto) {
        return SalaMapper.toDomain(jpaSalaRepository.findByProjeto(ProjetoMapper.toEntity(projeto)));
    }

    @Override
    public Sala findByEmpresa(Empresa empresa) {
        return SalaMapper.toDomain(jpaSalaRepository.findByEmpresa(EmpresaMapper.toEntity(empresa)));
    }

    @Override
    public List<Sala> findSalasComUsuariosPorUsuario(Integer idUsuario) {
        return jpaSalaRepository.findSalasComUsuariosPorUsuario(idUsuario)
                .stream()
                .map(SalaMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Optional buscarComUsuarios(Integer idSala) {
        return jpaSalaRepository.buscarComUsuarios(idSala)
                .map(SalaMapper::toDomain);
    }

    @Override
    public Sala save(Sala sala) {
        SalaEntity entity = SalaMapper.toEntity(sala);
        SalaEntity saved = jpaSalaRepository.save(entity);
        return SalaMapper.toDomain(saved);
    }

    @Override
    public Sala findById(Integer id) {
        return jpaSalaRepository.findById(id)
                .map(SalaMapper::toDomain)
                .orElse(null);
    }

    @Override
    public List<Sala> findAll() {
        return jpaSalaRepository.findAll()
                .stream()
                .map(SalaMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(Integer id) {
        jpaSalaRepository.deleteById(id);
    }
}
