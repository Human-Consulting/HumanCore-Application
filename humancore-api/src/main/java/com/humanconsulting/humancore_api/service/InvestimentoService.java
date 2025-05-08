package com.humanconsulting.humancore_api.service;

import com.humanconsulting.humancore_api.controller.dto.atualizar.investimento.AtualizarInvestimentoRequestDto;
import com.humanconsulting.humancore_api.controller.dto.request.InvestimentoRequestDto;
import com.humanconsulting.humancore_api.controller.dto.response.investimento.InvestimentoResponseDto;
import com.humanconsulting.humancore_api.exception.EntidadeNaoEncontradaException;
import com.humanconsulting.humancore_api.exception.EntidadeSemRetornoException;
import com.humanconsulting.humancore_api.mapper.InvestimentoMapper;
import com.humanconsulting.humancore_api.model.Investimento;
import com.humanconsulting.humancore_api.repository.InvestimentoRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class InvestimentoService {

    @Autowired
    private InvestimentoRepository repository;

    public InvestimentoResponseDto cadastrar(InvestimentoRequestDto financeiroRequestDto) {
        Investimento financeiro = repository.save(InvestimentoMapper.toEntity(financeiroRequestDto));
        return InvestimentoMapper.toDto(financeiro);
    }

    public InvestimentoResponseDto buscarPorId(Integer id) {
        return InvestimentoMapper.toDto(repository.findById(id).get());
    }

    public List<InvestimentoResponseDto> listar() {
        List<Investimento> all = repository.findAll();
        if (all.isEmpty()) throw new EntidadeSemRetornoException("Nenhuma financeiro registrada");
        List<InvestimentoResponseDto> allResponse = new ArrayList<>();
        for (Investimento financeiro : all) {
            allResponse.add(InvestimentoMapper.toDto(financeiro));
        }
        return allResponse;
    }

    public List<InvestimentoResponseDto> listarPorProjeto(Integer idProjeto) {
        List<Investimento> all = repository.findAllByProjeto_IdProjeto(idProjeto);
        if (all.isEmpty()) throw new EntidadeSemRetornoException("Nenhuma financeiro registrada");
        List<InvestimentoResponseDto> allResponse = new ArrayList<>();
        for (Investimento financeiro : all) {
            allResponse.add(InvestimentoMapper.toDto(financeiro));
        }
        return allResponse;
    }

    public void deletar(Integer id) {
        repository.deleteById(id);
    }

    public InvestimentoResponseDto atualizar(Integer id, AtualizarInvestimentoRequestDto dto) {
        Investimento investimento = repository.findById(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Investimento não encontrado"));

        investimento.setValor(dto.getValor());
        investimento.setDtInvestimento(dto.getDtInvestimento());

        return InvestimentoMapper.toDto(repository.save(investimento));
    }

}
