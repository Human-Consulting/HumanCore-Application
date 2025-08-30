package com.humanconsulting.humancore_api.novo.application.usecases.investimento;

import com.humanconsulting.humancore_api.novo.domain.entities.Investimento;
import com.humanconsulting.humancore_api.novo.domain.repositories.InvestimentoRepository;
import com.humanconsulting.humancore_api.novo.domain.repositories.UsuarioRepository;
import com.humanconsulting.humancore_api.novo.web.dtos.atualizar.investimento.AtualizarInvestimentoRequestDto;
import com.humanconsulting.humancore_api.novo.web.dtos.response.investimento.InvestimentoResponseDto;
import com.humanconsulting.humancore_api.novo.web.mappers.InvestimentoMapper;

import java.util.Optional;

public class AtualizarInvestimentoUseCase {
    private final InvestimentoRepository investimentoRepository;
    private final UsuarioRepository usuarioRepository;
    private final BuscarInvestimentoPorIdUseCase buscarInvestimentoPorIdUseCase;

    public AtualizarInvestimentoUseCase(InvestimentoRepository investimentoRepository, UsuarioRepository usuarioRepository, BuscarInvestimentoPorIdUseCase buscarInvestimentoPorIdUseCase) {
        this.investimentoRepository = investimentoRepository;
        this.usuarioRepository = usuarioRepository;
        this.buscarInvestimentoPorIdUseCase = buscarInvestimentoPorIdUseCase;
    }

    public InvestimentoResponseDto execute(Integer idInvestimento, AtualizarInvestimentoRequestDto atualizarInvestimentoRequestDto) {
        Investimento investimento = buscarInvestimentoPorIdUseCase.execute(idInvestimento);
        Optional<com.humanconsulting.humancore_api.velho.model.Usuario> optUsuarioEditor = usuarioRepository.findById(atualizarInvestimentoRequestDto.getIdEditor());
        if (optUsuarioEditor.isEmpty()) throw new EntidadeNaoEncontradaException("Usuário não encontrado.");
        PermissaoValidator.validarPermissao(atualizarInvestimentoRequestDto.getPermissaoEditor(), "MODIFICAR_INVESTIMENTO");
        Investimento investimentoAtualizado = investimentoRepository.save(InvestimentoMapper.toEntity(atualizarInvestimentoRequestDto, idInvestimento, investimento.getProjeto()));
        return InvestimentoMapper.toDto(investimentoAtualizado);
    }
}

