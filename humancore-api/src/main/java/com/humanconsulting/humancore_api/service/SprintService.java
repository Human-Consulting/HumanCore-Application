package com.humanconsulting.humancore_api.service;

import com.humanconsulting.humancore_api.controller.dto.atualizar.sprint.*;
import com.humanconsulting.humancore_api.exception.EntidadeConflitanteException;
import com.humanconsulting.humancore_api.exception.EntidadeSemPermissaoException;
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

    public Sprint atualizar(Integer idSprint, SprintAtualizarRequestDto request) {
        Boolean temPermissao = repository.validarPermissao(request.getIdEditor(), request.getPermissaoEditor());
        if (!temPermissao) throw new EntidadeSemPermissaoException("Você não tem permissão para fazer essa edição");

        Sprint sprintAtualizada = repository.selectWhereId(idSprint);

        if((sprintAtualizada != null) && (sprintAtualizada.getIdSprint() == idSprint)) {
            sprintAtualizada.setIdSprint(idSprint);

            Sprint s = new Sprint(request.getDescricao(), request.getDtInicio(), request.getDtFim());

            repository.insert(s);

            return s;
        }

        throw new EntidadeSemRetornoException("Sprint não encontrada");
    }

    public Sprint atualizarProgresso(Integer id, AtualizarProgressoRequestDto request) {
        Boolean temPermissao = repository.validarPermissao(request.getIdEditor(), request.getPermissaoEditor());
        if (!temPermissao) throw new EntidadeSemPermissaoException("Você não tem permissão para fazer essa edição");

        Sprint sprintAtualizada = repository.selectWhereId(id);

        if(sprintAtualizada != null && (sprintAtualizada.getIdSprint() == id)) {
            sprintAtualizada.setProgresso(request.getNovoProgresso());

            repository.insert(sprintAtualizada);

            return sprintAtualizada;
        }

        throw new EntidadeSemRetornoException("Sprint não encontrada");
    }

    public Sprint atualizarImpedimento(Integer id, AtualizarImpedimentoRequestDto request) {
        Boolean temPermissao = repository.validarPermissao(request.getIdEditor(), request.getPermissaoEditor());
        if (!temPermissao) throw new EntidadeSemPermissaoException("Você não tem permissão para fazer essa edição");

        Sprint sprintAtualizada = repository.selectWhereId(id);

        if(sprintAtualizada != null && (sprintAtualizada.getIdSprint() == id)) {
            sprintAtualizada.setComImpedimento(request.getNovoImpedimento());

            repository.insert(sprintAtualizada);

            return sprintAtualizada;
        }

        throw new EntidadeSemRetornoException("Sprint não encontrada");
    }

    public Sprint atualizarTotalEntregas(Integer id, AtualizarTotalEntregasRequestDto request) {
        Boolean temPermissao = repository.validarPermissao(request.getIdEditor(), request.getPermissaoEditor());
        if (!temPermissao) throw new EntidadeSemPermissaoException("Você não tem permissão para fazer essa edição");

        Sprint sprintAtualizada = repository.selectWhereId(id);

        if(sprintAtualizada != null && (sprintAtualizada.getIdSprint() == id)) {
            sprintAtualizada.setTotalEntregas(request.getNovoTotalEntregas());

            repository.insert(sprintAtualizada);

            return sprintAtualizada;
        }

        throw new EntidadeSemRetornoException("Sprint não encontrada");
    }

    public Sprint atualizarFinalizada(Integer id, AtualizarFinalizadaRequestDto request) {
        Boolean temPermissao = repository.validarPermissao(request.getIdEditor(), request.getPermissaoEditor());
        if (!temPermissao) throw new EntidadeSemPermissaoException("Você não tem permissão para fazer essa edição");

        Sprint sprintAtualizada = repository.selectWhereId(id);

        if(sprintAtualizada != null && (sprintAtualizada.getIdSprint() == id)) {
            sprintAtualizada.setFinalizada(request.getNovoFinalizada());

            repository.insert(sprintAtualizada);

            return sprintAtualizada;
        }

        throw new EntidadeSemRetornoException("Sprint não encontrada");
    }
}
