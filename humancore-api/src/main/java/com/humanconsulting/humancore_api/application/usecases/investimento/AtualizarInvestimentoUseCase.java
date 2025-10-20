package com.humanconsulting.humancore_api.application.usecases.investimento;

import com.humanconsulting.humancore_api.domain.entities.Investimento;
import com.humanconsulting.humancore_api.domain.entities.Usuario;
import com.humanconsulting.humancore_api.domain.exception.EntidadeNaoEncontradaException;
import com.humanconsulting.humancore_api.domain.repositories.InvestimentoRepository;
import com.humanconsulting.humancore_api.domain.repositories.UsuarioRepository;
import com.humanconsulting.humancore_api.domain.security.ValidarPermissao;
import com.humanconsulting.humancore_api.web.dtos.atualizar.investimento.AtualizarInvestimentoRequestDto;
import com.humanconsulting.humancore_api.web.dtos.response.investimento.InvestimentoResponseDto;
import com.humanconsulting.humancore_api.web.mappers.InvestimentoMapper;

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
        InvestimentoResponseDto investimento = buscarInvestimentoPorIdUseCase.execute(idInvestimento);
        Optional<Usuario> optUsuarioEditor = usuarioRepository.findById(atualizarInvestimentoRequestDto.getIdEditor());
        if (optUsuarioEditor.isEmpty()) throw new EntidadeNaoEncontradaException("Usuário não encontrado.");
        ValidarPermissao.execute(atualizarInvestimentoRequestDto.getPermissaoEditor(), "MODIFICAR_INVESTIMENTO");
        Investimento investimentoAtualizado = investimentoRepository.save(InvestimentoMapper.toEntity(atualizarInvestimentoRequestDto, idInvestimento, investimento.getProjeto()));
        return InvestimentoMapper.toDto(investimentoAtualizado);
    }
}

