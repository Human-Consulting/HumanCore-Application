package com.humanconsulting.humancore_api.novo.infrastructure.repositories.adapters;

import com.humanconsulting.humancore_api.novo.domain.repositories.PageResult;
import com.humanconsulting.humancore_api.novo.domain.repositories.ProjetoRepository;
import com.humanconsulting.humancore_api.novo.domain.entities.Projeto;
import com.humanconsulting.humancore_api.novo.infrastructure.entities.ProjetoEntity;
import com.humanconsulting.humancore_api.novo.infrastructure.mappers.ProjetoMapper;
import com.humanconsulting.humancore_api.novo.infrastructure.repositories.jpa.JpaProjetoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

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
    public PageResult<Projeto> findAllByEmpresa_IdEmpresa(Integer idEmpresa, int page, int size) {
        Pageable pageable = org.springframework.data.domain.PageRequest.of(page, size);
        Page<ProjetoEntity> projetoEntities = jpaProjetoRepository.findAllByEmpresa_IdEmpresa(idEmpresa, pageable);

        return new PageResultImpl<>(
                projetoEntities.getContent().stream().map(ProjetoMapper::toDomain).toList(),
                projetoEntities.getNumber(),
                projetoEntities.getSize(),
                projetoEntities.getTotalElements(),
                projetoEntities.getTotalPages()
        );
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
