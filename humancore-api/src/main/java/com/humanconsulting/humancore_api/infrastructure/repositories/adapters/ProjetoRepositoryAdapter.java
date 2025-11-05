package com.humanconsulting.humancore_api.infrastructure.repositories.adapters;

import com.humanconsulting.humancore_api.domain.exception.EntidadeNaoEncontradaException;
import com.humanconsulting.humancore_api.domain.repositories.ProjetoRepository;
import com.humanconsulting.humancore_api.domain.entities.Projeto;
import com.humanconsulting.humancore_api.domain.utils.PageResult;
import com.humanconsulting.humancore_api.infrastructure.entities.ProjetoEntity;
import com.humanconsulting.humancore_api.infrastructure.mappers.EmpresaMapper;
import com.humanconsulting.humancore_api.infrastructure.mappers.ProjetoMapper;
import com.humanconsulting.humancore_api.infrastructure.mappers.UsuarioMapper;
import com.humanconsulting.humancore_api.infrastructure.repositories.jpa.JpaProjetoRepository;
import com.humanconsulting.humancore_api.infrastructure.utils.PageResultImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
        Pageable pageable = PageRequest.of(page, size);
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
    public PageResult<Projeto> findAllByEmpresa_IdEmpresaAndNomeContainingIgnoreCase(Integer idEmpresa, int page, int size, String nome) {
        Pageable pageable = PageRequest.of(page, size);
        Page<ProjetoEntity> projetoEntities = jpaProjetoRepository.findAllByEmpresa_IdEmpresaAndNomeContainingIgnoreCase(idEmpresa, pageable, nome);

        return new PageResultImpl<>(
                projetoEntities.getContent().stream().map(ProjetoMapper::toDomain).toList(),
                projetoEntities.getNumber(),
                projetoEntities.getSize(),
                projetoEntities.getTotalElements(),
                projetoEntities.getTotalPages()
        );
    }

    @Override
    public List<Projeto> findAllByEmpresa_IdEmpresa(Integer idEmpresa) {
        List<ProjetoEntity> projetoEntities = jpaProjetoRepository.findAllByEmpresa_IdEmpresa(idEmpresa);
        return projetoEntities.stream().map(ProjetoMapper::toDomain).toList();
    }

    @Override
    public String findUrlImagemById(Integer idProjeto) {
        return jpaProjetoRepository.findUrlImagemById(idProjeto);
    }

    @Override
    public Projeto save(Projeto projeto) {
        ProjetoEntity entity = null;
        if (projeto.getIdProjeto() != null) {
            entity = jpaProjetoRepository.findById(projeto.getIdProjeto())
                    .orElseThrow(() -> new EntidadeNaoEncontradaException("Projeto não encontrada"));
        } else entity = new ProjetoEntity();

        entity.setTitulo(projeto.getTitulo());
        entity.setDescricao(projeto.getDescricao());
        entity.setOrcamento(projeto.getOrcamento());
        entity.setUrlImagem(projeto.getUrlImagem());
        entity.setResponsavel(UsuarioMapper.toEntity(projeto.getResponsavel()));
        entity.setEmpresa(EmpresaMapper.toEntity(projeto.getEmpresa()));

        ProjetoEntity saved = jpaProjetoRepository.save(entity);
        return ProjetoMapper.toDomain(saved);
    }

    @Override
    public Optional<Projeto> findById(Integer id) {
        return Optional.ofNullable(jpaProjetoRepository.findById(id)
                .map(ProjetoMapper::toDomain)
                .orElse(null));
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
