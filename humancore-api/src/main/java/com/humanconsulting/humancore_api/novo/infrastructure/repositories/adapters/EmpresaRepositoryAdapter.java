package com.humanconsulting.humancore_api.novo.infrastructure.repositories.adapters;

import com.humanconsulting.humancore_api.novo.domain.repositories.EmpresaRepository;
import com.humanconsulting.humancore_api.novo.domain.entities.Empresa;
import com.humanconsulting.humancore_api.novo.domain.repositories.PageResult;
import com.humanconsulting.humancore_api.novo.infrastructure.entities.EmpresaEntity;
import com.humanconsulting.humancore_api.novo.infrastructure.mappers.EmpresaMapper;
import com.humanconsulting.humancore_api.novo.infrastructure.repositories.jpa.JpaEmpresaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

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
        EmpresaEntity entity = EmpresaMapper.toEntity(empresa);
        EmpresaEntity saved = jpaEmpresaRepository.save(entity);
        return EmpresaMapper.toDomain(saved);
    }

    @Override
    public Optional<Empresa> findById(Integer id) {
        return jpaEmpresaRepository.findById(id)
                .map(EmpresaMapper::toDomain)
                .orElse(null);
    }

    @Override
    public PageResult<Empresa> findAll(int page, int size) {
        Pageable pageable = Pageable.ofSize(size).withPage(page);
        Page<EmpresaEntity> empresaEntities = jpaEmpresaRepository.findAll(pageable);

        return new PageResultImpl<>(
                empresaEntities.getContent().stream().map(EmpresaMapper::toDomain).toList(),
                empresaEntities.getNumber(),
                empresaEntities.getSize(),
                empresaEntities.getTotalElements(),
                empresaEntities.getTotalPages()
        );
    }

    @Override
    public void deleteById(Integer id) {
        jpaEmpresaRepository.deleteById(id);
    }
}

