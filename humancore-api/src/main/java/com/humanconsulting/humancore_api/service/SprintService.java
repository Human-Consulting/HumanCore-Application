package com.humanconsulting.humancore_api.service;

import com.humanconsulting.humancore_api.controller.dto.atualizar.sprint.*;
import com.humanconsulting.humancore_api.controller.dto.request.SprintRequestDto;
import com.humanconsulting.humancore_api.controller.dto.response.TarefaResponseDto;
import com.humanconsulting.humancore_api.controller.dto.response.SprintResponseDto;
import com.humanconsulting.humancore_api.exception.EntidadeConflitanteException;
import com.humanconsulting.humancore_api.exception.EntidadeNaoEncontradaException;
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
import java.util.Optional;

@Service
public class SprintService {

    @Autowired private SprintRepository sprintRepository;

    @Autowired private TarefaRepository tarefaRepository;

    @Autowired private TarefaService tarefaService;

    public SprintResponseDto cadastrar(SprintRequestDto sprintRequestDto) {
        if (sprintRequestDto.getDtInicio().isAfter(sprintRequestDto.getDtFim()) || sprintRequestDto.getDtInicio().isEqual(sprintRequestDto.getDtFim())) throw new EntidadeConflitanteException("Datas de início e fim conflitantes.");
        Sprint sprint = sprintRepository.save(SprintMapper.toEntity(sprintRequestDto));
        return passarParaResponse(sprint, sprint.getIdSprint());
    }

    public Sprint buscarPorId(Integer id) {
        Optional<Sprint> optSprint = sprintRepository.findById(id);
        Sprint sprint = optSprint.get();

        if (optSprint.isEmpty()) throw new EntidadeNaoEncontradaException("Sprint não encontrada.");

        return sprint;
    }

    public List<Sprint> listar() {
        List<Sprint> all = sprintRepository.findAll();
        if (all.isEmpty()) throw new EntidadeSemRetornoException("Nenhuma sprint registrada");
        return all;
    }

    public void deletar(Integer id) {
        Optional<Sprint> optSprint = sprintRepository.findById(id);
        if (optSprint.isEmpty()) throw new EntidadeNaoEncontradaException("Sprint não encontrada.");
        sprintRepository.deleteById(id);
    }

    public SprintResponseDto atualizar(Integer idSprint, SprintAtualizarRequestDto request) {
        Optional<Sprint> optSprint = sprintRepository.findById(idSprint);
        if (optSprint.isEmpty()) throw new EntidadeNaoEncontradaException("Sprint não encontrada.");

        Sprint sprint = SprintMapper.toEntity(request);
        sprintRepository.save(sprint);

        return passarParaResponse(sprint, sprint.getIdSprint());
    }

    public List<SprintResponseDto> buscarPorIdProjeto(Integer idProjeto) {
        List<Sprint> all = sprintRepository.findByProjeto_IdProjeto(idProjeto);
        if (all.isEmpty()) throw new EntidadeSemRetornoException("Nenhum projeto encontrado");
        List<SprintResponseDto> allResponse = new ArrayList<>();
        for (Sprint sprint : all) {
            allResponse.add(passarParaResponse(sprint, sprint.getIdSprint()));
        }
        return allResponse;
    }

    public SprintResponseDto passarParaResponse(Sprint sprint, Integer idSprint) {
        double progresso = tarefaRepository.mediaProgressoSprint(idSprint);
        boolean comImpedimento = tarefaRepository.existsImpedimentoBySprint(idSprint);
        List<Tarefa> tarefas = tarefaRepository.findByProjetoAndSprint(sprint.getProjeto().getIdProjeto(), sprint.getIdSprint());
        List<TarefaResponseDto> tarefasResponse = new ArrayList<>();
        for (Tarefa tarefa : tarefas) {
            tarefasResponse.add(tarefaService.passarParaResponse(tarefa));
        }
        return SprintMapper.toDto(sprint, progresso, comImpedimento, tarefasResponse);
    }
}
