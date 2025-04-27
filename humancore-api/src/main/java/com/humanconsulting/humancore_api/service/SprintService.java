package com.humanconsulting.humancore_api.service;

import com.humanconsulting.humancore_api.controller.dto.atualizar.sprint.*;
import com.humanconsulting.humancore_api.controller.dto.request.SprintRequestDto;
import com.humanconsulting.humancore_api.controller.dto.response.TarefaResponseDto;
import com.humanconsulting.humancore_api.controller.dto.response.SprintResponseDto;
import com.humanconsulting.humancore_api.exception.EntidadeConflitanteException;
import com.humanconsulting.humancore_api.exception.EntidadeSemRetornoException;
import com.humanconsulting.humancore_api.mapper.SprintMapper;
import com.humanconsulting.humancore_api.model.Tarefa;
import com.humanconsulting.humancore_api.model.Sprint;
import com.humanconsulting.humancore_api.repository.TarefaRepository;
import com.humanconsulting.humancore_api.repository.SprintRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SprintService {

    @Autowired private SprintRepository sprintRepository;

    @Autowired private TarefaRepository tarefaRepository;

    @Autowired private TarefaService tarefaService;

    public SprintResponseDto cadastrar(SprintRequestDto sprintRequestDto) {
        if (sprintRequestDto.getDtInicio().isAfter(sprintRequestDto.getDtFim()) || sprintRequestDto.getDtInicio().isEqual(sprintRequestDto.getDtFim())) throw new EntidadeConflitanteException("Datas de início e fim conflitantes.");
        Sprint sprint = sprintRepository.insert(SprintMapper.toEntity(sprintRequestDto));
        return passarParaResponse(sprint, sprint.getIdSprint());
    }

    public Sprint buscarPorId(Integer id) {
        return sprintRepository.selectWhereId(id);
    }

    public List<Sprint> listar() {
        List<Sprint> all = sprintRepository.selectAll();
        if (all.isEmpty()) throw new EntidadeSemRetornoException("Nenhuma sprint registrada");
        return all;
    }

    public void deletar(Integer id) {
        sprintRepository.deleteWhere(id);
    }

    public SprintResponseDto atualizar(Integer idSprint, SprintAtualizarRequestDto request) {
        sprintRepository.existsById(idSprint);

        Sprint sprint = sprintRepository.update(idSprint, request);
        return passarParaResponse(sprint, sprint.getIdSprint());
    }

    public List<SprintResponseDto> buscarPorIdProjeto(Integer idProjeto) {
        List<Sprint> all = sprintRepository.selectWhereIdProjeto(idProjeto);
        if (all.isEmpty()) throw new EntidadeSemRetornoException("Nenhum projeto encontrado");
        List<SprintResponseDto> allResponse = new ArrayList<>();
        for (Sprint sprint : all) {
            allResponse.add(passarParaResponse(sprint, sprint.getIdSprint()));
        }
        return allResponse;
    }

    public SprintResponseDto passarParaResponse(Sprint sprint, Integer idSprint) {
        double progresso = tarefaRepository.mediaProgressoSprint(idSprint);
        boolean comImpedimento = tarefaRepository.sprintComImpedimento(idSprint);
        List<Tarefa> tarefas = tarefaRepository.selectWhereIdProjeto(sprint.getFkProjeto(), sprint.getIdSprint());
        List<TarefaResponseDto> tarefasResponse = new ArrayList<>();
        for (Tarefa tarefa : tarefas) {
            tarefasResponse.add(tarefaService.passarParaResponse(tarefa, tarefa.getFkResponsavel()));
        }
        return SprintMapper.toDto(sprint, progresso, comImpedimento, tarefasResponse);
    }
}
