package com.humanconsulting.humancore_api.novo.infrastructure.repositories.adapters;

import com.humanconsulting.humancore_api.novo.domain.repositories.ProjetoRepository;
import com.humanconsulting.humancore_api.novo.domain.entities.Projeto;
import com.humanconsulting.humancore_api.novo.infrastructure.entities.ProjetoEntity;
import com.humanconsulting.humancore_api.novo.infrastructure.mappers.ProjetoMapper;
import com.humanconsulting.humancore_api.novo.infrastructure.repositories.jpa.JpaProjetoRepository;

import java.util.List;
import java.util.Optional;

public class ProjetoRepositoryAdapter implements ProjetoRepository {
    private final JpaProjetoRepository jpaProjetoRepository;

    public ProjetoRepositoryAdapter(JpaProjetoRepository jpaProjetoRepository) {
        this.jpaProjetoRepository = jpaProjetoRepository;
    }


    @Override
    public boolean existsByEmpresa_IdEmpresaAndDescricao(Integer idEmpresa, String descricao) {
        return jpaProjetoRepository.existsByEmpresa_IdEmpresaAndDescricao(idEmpresa, descricao);
    }

    @Override
    public List<Projeto> findAllByEmpresa_IdEmpresa(Integer idEmpresa) {
        return jpaProjetoRepository.findAllByEmpresa_IdEmpresa(idEmpresa)
                .stream()
                .map(ProjetoMapper::toDomain)
                .toList();
    }

    @Override
    public String findUrlImagemById(Integer idProjeto) {
        return jpaProjetoRepository.findUrlImagemById(idProjeto);
    }

    @Override
    public Projeto save(Projeto projeto) {
        ProjetoEntity entity = ProjetoMapper.toEntity(projeto);
        ProjetoEntity saved = jpaProjetoRepository.save(entity);
        return ProjetoMapper.toDomain(saved);
    }

    @Override
    public Optional<Projeto> findById(Integer id) {
        return jpaProjetoRepository.findById(id)
                .map(ProjetoMapper::toDomain)
                .orElse(null);
    }

    @Override
    public List<Projeto> findAll() {
        return jpaProjetoRepository.findAll()
                .stream()
                .map(ProjetoMapper::toDomain)
                .toList();
    }

    @Override
    public void deleteById(Integer id) {
        jpaProjetoRepository.deleteById(id);
    }
}
