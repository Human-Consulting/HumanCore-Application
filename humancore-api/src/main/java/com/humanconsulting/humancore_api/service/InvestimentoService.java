package com.humanconsulting.humancore_api.service;

import com.humanconsulting.humancore_api.controller.dto.atualizar.investimento.AtualizarInvestimentoRequestDto;
import com.humanconsulting.humancore_api.controller.dto.request.InvestimentoRequestDto;
import com.humanconsulting.humancore_api.controller.dto.request.UsuarioPermissaoDto;
import com.humanconsulting.humancore_api.controller.dto.response.investimento.InvestimentoResponseDto;
import com.humanconsulting.humancore_api.exception.EntidadeNaoEncontradaException;
import com.humanconsulting.humancore_api.exception.EntidadeSemRetornoException;
import com.humanconsulting.humancore_api.mapper.InvestimentoMapper;
import com.humanconsulting.humancore_api.model.Investimento;
import com.humanconsulting.humancore_api.model.Projeto;
import com.humanconsulting.humancore_api.model.Usuario;
import com.humanconsulting.humancore_api.repository.InvestimentoRepository;
import com.humanconsulting.humancore_api.repository.ProjetoRepository;
import com.humanconsulting.humancore_api.repository.UsuarioRepository;
import com.humanconsulting.humancore_api.security.PermissaoValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class InvestimentoService {

    @Autowired private InvestimentoRepository investimentoRepository;

    @Autowired private ProjetoRepository projetoRepository;

    @Autowired private UsuarioRepository usuarioRepository;

    public InvestimentoResponseDto cadastrar(InvestimentoRequestDto financeiroRequestDto) {
        PermissaoValidator.validarPermissao(financeiroRequestDto.getPermissaoEditor(), "ADICIONAR_INVESTIMENTO");

        Projeto projeto = projetoRepository.findById(financeiroRequestDto.getFkProjeto()).get();
        Investimento financeiro = investimentoRepository.save(InvestimentoMapper.toEntity(financeiroRequestDto, projeto));
        return InvestimentoMapper.toDto(financeiro);
    }

    public Investimento buscarPorId(Integer id) {
        Optional<Investimento> optInvestimento = investimentoRepository.findById(id);

        if (optInvestimento.isEmpty()) throw new EntidadeNaoEncontradaException("Investimento não encontada");

        return optInvestimento.get();
    }

    public List<InvestimentoResponseDto> listar() {
        List<Investimento> all = investimentoRepository.findAll();
        if (all.isEmpty()) throw new EntidadeSemRetornoException("Nenhuma financeiro registrada");
        List<InvestimentoResponseDto> allResponse = new ArrayList<>();
        for (Investimento financeiro : all) {
            allResponse.add(InvestimentoMapper.toDto(financeiro));
        }
        return allResponse;
    }

    public List<InvestimentoResponseDto> listarPorProjeto(Integer idProjeto) {
        List<Investimento> all = investimentoRepository.findAllByProjeto_IdProjeto(idProjeto);
        if (all.isEmpty()) throw new EntidadeSemRetornoException("Nenhuma financeiro registrada");
        List<InvestimentoResponseDto> allResponse = new ArrayList<>();
        for (Investimento financeiro : all) {
            allResponse.add(InvestimentoMapper.toDto(financeiro));
        }
        return allResponse;
    }

    public void deletar(Integer id, UsuarioPermissaoDto usuarioPermissaoDto) {
        PermissaoValidator.validarPermissao(usuarioPermissaoDto.getPermissaoEditor(), "EXCLUIR_INVESTIMENTO");
        investimentoRepository.deleteById(id);
    }

    public InvestimentoResponseDto atualizar(Integer idInvestimento, AtualizarInvestimentoRequestDto atualizarInvestimentoRequestDto) {
        Investimento investimento = buscarPorId(idInvestimento);

        Optional<Usuario> optUsuarioEditor = usuarioRepository.findById(atualizarInvestimentoRequestDto.getIdEditor());

        if (optUsuarioEditor.isEmpty()) throw new EntidadeNaoEncontradaException("Usuário não encontrado.");

        PermissaoValidator.validarPermissao(atualizarInvestimentoRequestDto.getPermissaoEditor(), "MODIFICAR_INVESTIMENTO");

        Investimento investimentoAtualizado = investimentoRepository.save(InvestimentoMapper.toEntity(atualizarInvestimentoRequestDto, idInvestimento, investimento.getProjeto()));

        return InvestimentoMapper.toDto(investimentoRepository.save(investimentoAtualizado));
    }
}
