package com.humanconsulting.humancore_api.service;

import com.humanconsulting.humancore_api.controller.dto.atualizar.financeiro.AtualizarFinanceiroRequestDto;
import com.humanconsulting.humancore_api.controller.dto.financeiro.FinanceiroRequestDto;
import com.humanconsulting.humancore_api.controller.dto.response.financeiro.FinanceiroResponseDto;
import com.humanconsulting.humancore_api.exception.EntidadeSemRetornoException;
import com.humanconsulting.humancore_api.mapper.FinanceiroMapper;
import com.humanconsulting.humancore_api.model.Financeiro;
import com.humanconsulting.humancore_api.model.Tarefa;
import com.humanconsulting.humancore_api.repository.FinanceiroRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FinanceiroService {

    @Autowired
    private FinanceiroRepository repository;

    public FinanceiroResponseDto cadastrar(FinanceiroRequestDto financeiroRequestDto) {
        Financeiro financeiro = repository.insert(FinanceiroMapper.toEntity(financeiroRequestDto));
        return FinanceiroMapper.toDto(financeiro);
    }

    public FinanceiroResponseDto buscarPorId(Integer id) {
        return FinanceiroMapper.toDto(repository.selectWhereId(id));
    }

    public List<FinanceiroResponseDto> listar() {
        List<Financeiro> all = repository.selectAll();
        if (all.isEmpty()) throw new EntidadeSemRetornoException("Nenhuma financeiro registrada");
        List<FinanceiroResponseDto> allResponse = new ArrayList<>();
        for (Financeiro financeiro : all) {
            allResponse.add(FinanceiroMapper.toDto(financeiro));
        }
        return allResponse;
    }

    public List<FinanceiroResponseDto> listarPorProjeto(Integer idProjeto) {
        List<Financeiro> all = repository.selectAllWhereIdProjeto(idProjeto);
        if (all.isEmpty()) throw new EntidadeSemRetornoException("Nenhuma financeiro registrada");
        List<FinanceiroResponseDto> allResponse = new ArrayList<>();
        for (Financeiro financeiro : all) {
            allResponse.add(FinanceiroMapper.toDto(financeiro));
        }
        return allResponse;
    }

    public void deletar(Integer id) {
        repository.deleteWhere(id);
    }

    public FinanceiroResponseDto atualizar(Integer idFinanceiro, @Valid AtualizarFinanceiroRequestDto request) {
        Financeiro financeiro = repository.update(idFinanceiro, request);
        return FinanceiroMapper.toDto(financeiro);
    }
}
