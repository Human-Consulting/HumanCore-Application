package com.humanconsulting.humancore_api.infrastructure.repositories.adapters;

import com.humanconsulting.humancore_api.domain.exception.EntidadeNaoEncontradaException;
import com.humanconsulting.humancore_api.domain.repositories.EmpresaRepository;
import com.humanconsulting.humancore_api.domain.entities.Empresa;
import com.humanconsulting.humancore_api.infrastructure.entities.EmpresaEntity;
import com.humanconsulting.humancore_api.infrastructure.mappers.EmpresaMapper;
import com.humanconsulting.humancore_api.infrastructure.repositories.jpa.JpaEmpresaRepository;

import java.util.List;
import java.util.Optional;

public class EmpresaRepositoryAdapter implements EmpresaRepository {
    private final JpaEmpresaRepository jpaEmpresaRepository;

    public EmpresaRepositoryAdapter(JpaEmpresaRepository jpaEmpresaRepository) {
        this.jpaEmpresaRepository = jpaEmpresaRepository;
    }

    @Override
    public boolean existsByCnpj(String cnpj) {
        return jpaEmpresaRepository.existsByCnpj(cnpj);
    }

    @Override
    public String findUrlImagemById(Integer idEmpresa) {
        return jpaEmpresaRepository.findUrlImagemById(idEmpresa);
    }

    @Override
    public Empresa save(Empresa empresa) {
        EmpresaEntity entity = null;
        if (empresa.getIdEmpresa() != null) {
            entity = jpaEmpresaRepository.findById(empresa.getIdEmpresa())
                    .orElseThrow(() -> new EntidadeNaoEncontradaException("Empresa não encontrada"));
        } else entity = new EmpresaEntity();

        entity.setNome(empresa.getNome());
        entity.setCnpj(empresa.getCnpj());
        entity.setUrlImagem(empresa.getUrlImagem());

        EmpresaEntity saved = jpaEmpresaRepository.save(entity);
        return EmpresaMapper.toDomain(saved);
    }

    @Override
    public Optional<Empresa> findById(Integer id) {
        return Optional.ofNullable(jpaEmpresaRepository.findById(id)
                .map(EmpresaMapper::toDomain)
                .orElse(null));
    }

    @Override
    public List<Empresa> findAll() {
        return jpaEmpresaRepository.findAll()
                .stream()
                .map(EmpresaMapper::toDomain)
                .toList();
    }

    @Override
    public void deleteById(Integer id) {
        jpaEmpresaRepository.deleteById(id);
    }
}

