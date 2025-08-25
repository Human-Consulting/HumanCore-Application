package com.humanconsulting.humancore_api.velho.service;

import com.humanconsulting.humancore_api.velho.controller.dto.atualizar.investimento.AtualizarInvestimentoRequestDto;
import com.humanconsulting.humancore_api.velho.controller.dto.request.InvestimentoRequestDto;
import com.humanconsulting.humancore_api.velho.controller.dto.request.UsuarioPermissaoDto;
import com.humanconsulting.humancore_api.velho.controller.dto.response.investimento.InvestimentoResponseDto;
import com.humanconsulting.humancore_api.velho.exception.EntidadeNaoEncontradaException;
import com.humanconsulting.humancore_api.velho.exception.EntidadeSemRetornoException;
import com.humanconsulting.humancore_api.velho.mapper.InvestimentoMapper;
import com.humanconsulting.humancore_api.velho.model.Investimento;
import com.humanconsulting.humancore_api.velho.model.Projeto;
import com.humanconsulting.humancore_api.velho.model.Usuario;
import com.humanconsulting.humancore_api.velho.repository.InvestimentoRepository;
import com.humanconsulting.humancore_api.velho.repository.ProjetoRepository;
import com.humanconsulting.humancore_api.velho.repository.UsuarioRepository;
import com.humanconsulting.humancore_api.velho.security.PermissaoValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class InvestimentoService {

    @Autowired InvestimentoRepository investimentoRepository;

    @Autowired ProjetoRepository projetoRepository;

    @Autowired UsuarioRepository usuarioRepository;

    public InvestimentoResponseDto cadastrar(InvestimentoRequestDto financeiroRequestDto) {
        PermissaoValidator.validarPermissao(financeiroRequestDto.getPermissaoEditor(), "ADICIONAR_INVESTIMENTO");

        Projeto projeto = projetoRepository.findById(financeiroRequestDto.getFkProjeto()).get();
        Investimento financeiro = investimentoRepository.save(InvestimentoMapper.toEntity(financeiroRequestDto, projeto));
        return InvestimentoMapper.toDto(financeiro);
    }

    public Investimento buscarPorId(Integer id) {
        Optional<Investimento> optInvestimento = investimentoRepository.findById(id);

        if (optInvestimento.isEmpty()) throw new EntidadeNaoEncontradaException("InvestimentoEntity não encontada");

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
