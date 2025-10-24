package com.humanconsulting.humancore_api.infrastructure.repositories.adapters;

import com.humanconsulting.humancore_api.domain.exception.EntidadeNaoEncontradaException;
import com.humanconsulting.humancore_api.domain.repositories.EmpresaRepository;
import com.humanconsulting.humancore_api.domain.entities.Empresa;
import com.humanconsulting.humancore_api.domain.utils.PageResult;
import com.humanconsulting.humancore_api.infrastructure.entities.EmpresaEntity;
import com.humanconsulting.humancore_api.infrastructure.mappers.EmpresaMapper;
import com.humanconsulting.humancore_api.infrastructure.repositories.jpa.JpaEmpresaRepository;
import com.humanconsulting.humancore_api.infrastructure.utils.PageResultImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

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

