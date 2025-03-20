package com.humanconsulting.humancore_api.service;

import com.humanconsulting.humancore_api.exception.EntidadeConflitanteException;
import com.humanconsulting.humancore_api.exception.EntidadeSemRetornoException;
import com.humanconsulting.humancore_api.model.Entrega;
import com.humanconsulting.humancore_api.repository.EntregaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EntregaService {

    @Autowired
    private EntregaRepository repository;

    public Entrega cadastrar(Entrega entrega) {
        if (entrega.getDtInicio().isAfter(entrega.getDtFim()) || entrega.getDtInicio().isEqual(entrega.getDtFim())) throw new EntidadeConflitanteException("Datas de início e fim conflitantes.");
        entrega.setIdSprint(null);
        return repository.insert(entrega);
    }

    public Entrega buscarPorId(Integer id) {
        return repository.selectWhereId(id);
    }

    public List<Entrega> listar() {
        List<Entrega> all = repository.selectAll();
        if (all.isEmpty()) throw new EntidadeSemRetornoException("Nenhuma entrega registrada");
        return all;
    }

    public void deletar(Integer id) {
        repository.deleteWhere(id);
    }
}
