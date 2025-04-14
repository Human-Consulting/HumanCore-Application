package com.humanconsulting.humancore_api.service;

import com.humanconsulting.humancore_api.controller.dto.atualizar.financeiro.AtualizarFinanceiroRequestDto;
import com.humanconsulting.humancore_api.exception.EntidadeSemRetornoException;
import com.humanconsulting.humancore_api.model.Financeiro;
import com.humanconsulting.humancore_api.repository.FinanceiroRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FinanceiroService {

    @Autowired
    private FinanceiroRepository repository;

    public Financeiro cadastrar(Financeiro financeiro) {
        financeiro.setIdFinanceiro(null);
        return repository.insert(financeiro);
    }

    public Financeiro buscarPorId(Integer id) {
        return repository.selectWhereId(id);
    }

    public List<Financeiro> listar() {
        List<Financeiro> all = repository.selectAll();
        if (all.isEmpty()) throw new EntidadeSemRetornoException("Nenhuma financeiro registrada");
        return all;
    }

    public void deletar(Integer id) {
        repository.deleteWhere(id);
    }

    public Financeiro atualizar(Integer idFinanceiro, @Valid AtualizarFinanceiroRequestDto request) {
        Financeiro financeiroAtualizado = repository.selectWhereId(idFinanceiro);

        if((financeiroAtualizado != null) && (financeiroAtualizado.getIdFinanceiro() == idFinanceiro)) {
            financeiroAtualizado.setIdFinanceiro(idFinanceiro);

            Financeiro f = new Financeiro(request.getValor(), request.getDtInvestimento(), request.getFkProjeto());

            repository.insert(f);

            return f;
        }

        throw new EntidadeSemRetornoException("Financeiro não encontrado");
    }
}
