package com.humanconsulting.humancore_api.service;

import com.humanconsulting.humancore_api.controller.dto.atualizar.SprintAtualizarRequestDto;
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

    public Sprint atualizar(Integer idSprint, SprintAtualizarRequestDto sprint) {
        Sprint sprintAtualizada = repository.selectWhereId(idSprint);

        if((sprintAtualizada != null) && (sprintAtualizada.getIdSprint() == idSprint)) {
            sprintAtualizada.setIdSprint(idSprint);

            Sprint s = new Sprint(sprint.getDescricao(), sprint.getDtInicio(), sprint.getDtFim());

            repository.insert(s);

            return s;
        }

        throw new EntidadeSemRetornoException("Sprint não encontrada");
    }

    public Sprint atualizarProgresso(Integer id, Double progresso) {
        Sprint sprintAtualizada = repository.selectWhereId(id);

        if(sprintAtualizada != null && (sprintAtualizada.getIdSprint() == id)) {
            sprintAtualizada.setProgresso(progresso);

            repository.insert(sprintAtualizada);

            return sprintAtualizada;
        }

        throw new EntidadeSemRetornoException("Sprint não encontrada");
    }

    public Sprint atualizarImpedimento(Integer id, Boolean impedimento) {
        Sprint sprintAtualizada = repository.selectWhereId(id);

        if(sprintAtualizada != null && (sprintAtualizada.getIdSprint() == id)) {
            sprintAtualizada.setComImpedimento(impedimento);

            repository.insert(sprintAtualizada);

            return sprintAtualizada;
        }

        throw new EntidadeSemRetornoException("Sprint não encontrada");
    }

    public Sprint atualizarTotalEntregas(Integer id, Integer novoTotal) {
        Sprint sprintAtualizada = repository.selectWhereId(id);

        if(sprintAtualizada != null && (sprintAtualizada.getIdSprint() == id)) {
            sprintAtualizada.setTotalEntregas(novoTotal);

            repository.insert(sprintAtualizada);

            return sprintAtualizada;
        }

        throw new EntidadeSemRetornoException("Sprint não encontrada");
    }

    public Sprint atualizarFinalizada(Integer id, Boolean novoFinalizada) {
        Sprint sprintAtualizada = repository.selectWhereId(id);

        if(sprintAtualizada != null && (sprintAtualizada.getIdSprint() == id)) {
            sprintAtualizada.setFinalizada(novoFinalizada);

            repository.insert(sprintAtualizada);

            return sprintAtualizada;
        }

        throw new EntidadeSemRetornoException("Sprint não encontrada");
    }
}
