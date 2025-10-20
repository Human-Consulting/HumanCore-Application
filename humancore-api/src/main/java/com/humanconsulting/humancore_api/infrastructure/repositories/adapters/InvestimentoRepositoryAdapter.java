package com.humanconsulting.humancore_api.infrastructure.repositories.adapters;

import com.humanconsulting.humancore_api.domain.repositories.InvestimentoRepository;
import com.humanconsulting.humancore_api.domain.entities.Investimento;
import com.humanconsulting.humancore_api.infrastructure.entities.InvestimentoEntity;
import com.humanconsulting.humancore_api.infrastructure.mappers.InvestimentoMapper;
import com.humanconsulting.humancore_api.infrastructure.repositories.jpa.JpaInvestimentoRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class InvestimentoRepositoryAdapter implements InvestimentoRepository {
    private final JpaInvestimentoRepository jpaInvestimentoRepository;

    public InvestimentoRepositoryAdapter(JpaInvestimentoRepository jpaInvestimentoRepository) {
        this.jpaInvestimentoRepository = jpaInvestimentoRepository;
    }

    @Override
    public List<Investimento> findAllByProjeto_IdProjeto(Integer idProjeto) {
        return jpaInvestimentoRepository.findAllByProjeto_IdProjeto(idProjeto)
                .stream()
                .map(InvestimentoMapper::toDomain)
                .collect(Collectors.toList());
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
