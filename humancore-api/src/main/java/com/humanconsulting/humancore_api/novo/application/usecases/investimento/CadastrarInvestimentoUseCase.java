package com.humanconsulting.humancore_api.novo.application.usecases.investimento;

import com.humanconsulting.humancore_api.novo.domain.entities.Projeto;
import com.humanconsulting.humancore_api.novo.domain.repositories.InvestimentoRepository;
import com.humanconsulting.humancore_api.novo.domain.repositories.ProjetoRepository;
import com.humanconsulting.humancore_api.novo.web.dtos.request.InvestimentoRequestDto;
import com.humanconsulting.humancore_api.novo.web.dtos.response.investimento.InvestimentoResponseDto;
import com.humanconsulting.humancore_api.novo.web.mappers.InvestimentoMapper;

public class CadastrarInvestimentoUseCase {
    private final InvestimentoRepository investimentoRepository;
    private final ProjetoRepository projetoRepository;

    public CadastrarInvestimentoUseCase(InvestimentoRepository investimentoRepository, ProjetoRepository projetoRepository) {
        this.investimentoRepository = investimentoRepository;
        this.projetoRepository = projetoRepository;
    }

    public InvestimentoResponseDto execute(InvestimentoRequestDto financeiroRequestDto) {
        PermissaoValidator.validarPermissao(financeiroRequestDto.getPermissaoEditor(), "ADICIONAR_INVESTIMENTO");
        Projeto projeto = projetoRepository.findById(financeiroRequestDto.getFkProjeto());
        var investimento = investimentoRepository.save(InvestimentoMapper.toEntity(financeiroRequestDto, projeto));
        return InvestimentoMapper.toDto(investimento);
    }
}

