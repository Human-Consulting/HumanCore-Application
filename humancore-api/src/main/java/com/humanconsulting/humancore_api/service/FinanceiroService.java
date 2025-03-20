package com.humanconsulting.humancore_api.service;

import com.humanconsulting.humancore_api.exception.EntidadeSemRetornoException;
import com.humanconsulting.humancore_api.model.Financeiro;
import com.humanconsulting.humancore_api.repository.FinanceiroRepository;
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
}
