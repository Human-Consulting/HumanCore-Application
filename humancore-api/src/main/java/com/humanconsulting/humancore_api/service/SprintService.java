package com.humanconsulting.humancore_api.service;

import com.humanconsulting.humancore_api.exception.EntidadeConflitanteException;
import com.humanconsulting.humancore_api.exception.EntidadeSemRetornoException;
import com.humanconsulting.humancore_api.model.Sprint;
import com.humanconsulting.humancore_api.repository.SprintRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SprintService {

    @Autowired
    private SprintRepository repository;

    public Sprint cadastrar(Sprint sprint) {
        if (sprint.getDtInicio().isAfter(sprint.getDtFim()) || sprint.getDtInicio().isEqual(sprint.getDtFim())) throw new EntidadeConflitanteException("Datas de início e fim conflitantes.");
        sprint.setIdSprint(null);
        return repository.insert(sprint);
    }

    public Sprint buscarPorId(Integer id) {
        return repository.selectWhereId(id);
    }

    public List<Sprint> listar() {
        List<Sprint> all = repository.selectAll();
        if (all.isEmpty()) throw new EntidadeSemRetornoException("Nenhuma sprint registrada");
        return all;
    }

    public void deletar(Integer id) {
        repository.deleteWhere(id);
    }
}
