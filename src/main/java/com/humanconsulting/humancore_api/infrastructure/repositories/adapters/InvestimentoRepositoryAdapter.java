package com.humanconsulting.humancore_api.infrastructure.repositories.adapters;

import com.humanconsulting.humancore_api.domain.repositories.InvestimentoRepository;
import com.humanconsulting.humancore_api.domain.entities.Investimento;
import com.humanconsulting.humancore_api.domain.utils.PageResult;
import com.humanconsulting.humancore_api.infrastructure.entities.InvestimentoEntity;
import com.humanconsulting.humancore_api.infrastructure.mappers.InvestimentoMapper;
import com.humanconsulting.humancore_api.infrastructure.repositories.jpa.JpaInvestimentoRepository;
import com.humanconsulting.humancore_api.infrastructure.utils.PageResultImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class InvestimentoRepositoryAdapter implements InvestimentoRepository {
    private final JpaInvestimentoRepository jpaInvestimentoRepository;

    public InvestimentoRepositoryAdapter(JpaInvestimentoRepository jpaInvestimentoRepository) {
        this.jpaInvestimentoRepository = jpaInvestimentoRepository;
    }

    @Override
    public PageResult<Investimento> findAllByProjeto_IdProjeto(Integer idProjeto, int page, int size) {
        Pageable pageable = org.springframework.data.domain.PageRequest.of(page, size);
        Page<InvestimentoEntity> investimentoEntities = jpaInvestimentoRepository.findAllByProjeto_IdProjeto(idProjeto, pageable);

        return new PageResultImpl<>(
                investimentoEntities.getContent().stream().map(InvestimentoMapper::toDomain).toList(),
                investimentoEntities.getNumber(),
                investimentoEntities.getSize(),
                investimentoEntities.getTotalElements(),
                investimentoEntities.getTotalPages()
        );
    }

    @Override
    public Investimento save(Investimento investimento) {
        InvestimentoEntity entity = InvestimentoMapper.toEntity(investimento);
        InvestimentoEntity saved = jpaInvestimentoRepository.save(entity);
        return InvestimentoMapper.toDomain(saved);
    }

    @Override
    public Optional<Investimento> findById(Integer id) {
        return Optional.ofNullable(jpaInvestimentoRepository.findById(id)
                .map(InvestimentoMapper::toDomain)
                .orElse(null));
    }

    @Override
    public List<Investimento> findAll() {
        return jpaInvestimentoRepository.findAll()
                .stream()
                .map(InvestimentoMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(Integer id) {
        jpaInvestimentoRepository.deleteById(id);
    }
}
