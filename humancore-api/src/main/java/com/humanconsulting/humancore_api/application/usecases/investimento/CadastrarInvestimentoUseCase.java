package com.humanconsulting.humancore_api.application.usecases.investimento;

import com.humanconsulting.humancore_api.domain.entities.Projeto;
import com.humanconsulting.humancore_api.domain.repositories.InvestimentoRepository;
import com.humanconsulting.humancore_api.domain.repositories.ProjetoRepository;
import com.humanconsulting.humancore_api.domain.security.ValidarPermissao;
import com.humanconsulting.humancore_api.web.dtos.request.InvestimentoRequestDto;
import com.humanconsulting.humancore_api.web.dtos.response.investimento.InvestimentoResponseDto;
import com.humanconsulting.humancore_api.web.mappers.InvestimentoMapper;

import java.util.Optional;

public class CadastrarInvestimentoUseCase {
    private final InvestimentoRepository investimentoRepository;
    private final ProjetoRepository projetoRepository;

    public CadastrarInvestimentoUseCase(InvestimentoRepository investimentoRepository, ProjetoRepository projetoRepository) {
        this.investimentoRepository = investimentoRepository;
        this.projetoRepository = projetoRepository;
    }

    public InvestimentoResponseDto execute(InvestimentoRequestDto financeiroRequestDto) {
        ValidarPermissao.execute(financeiroRequestDto.getPermissaoEditor(), "ADICIONAR_INVESTIMENTO");
        Optional<Projeto> projeto = projetoRepository.findById(financeiroRequestDto.getFkProjeto());
        var investimento = investimentoRepository.save(InvestimentoMapper.toEntity(financeiroRequestDto, projeto.orElse(null)));
        return InvestimentoMapper.toDto(investimento);
    }
}

