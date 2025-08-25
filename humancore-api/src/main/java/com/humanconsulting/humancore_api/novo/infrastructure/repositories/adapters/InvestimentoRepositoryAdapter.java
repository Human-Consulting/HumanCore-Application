package com.humanconsulting.humancore_api.novo.infrastructure.repositories.adapters;

import com.humanconsulting.humancore_api.novo.domain.repositories.InvestimentoRepository;
import com.humanconsulting.humancore_api.novo.domain.entities.Investimento;
import com.humanconsulting.humancore_api.novo.infrastructure.entities.InvestimentoEntity;
import com.humanconsulting.humancore_api.novo.infrastructure.mappers.InvestimentoMapper;
import com.humanconsulting.humancore_api.novo.infrastructure.repositories.jpa.JpaInvestimentoRepository;

import java.util.List;
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
    public Investimento findById(Integer id) {
        return jpaInvestimentoRepository.findById(id)
                .map(InvestimentoMapper::toDomain)
                .orElse(null);
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
