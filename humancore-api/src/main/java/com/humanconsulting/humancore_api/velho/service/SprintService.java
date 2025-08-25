package com.humanconsulting.humancore_api.velho.service;

import com.humanconsulting.humancore_api.controller.dto.atualizar.sprint.*;
import com.humanconsulting.humancore_api.velho.controller.dto.atualizar.sprint.SprintAtualizarRequestDto;
import com.humanconsulting.humancore_api.velho.controller.dto.request.SprintRequestDto;
import com.humanconsulting.humancore_api.velho.controller.dto.request.UsuarioPermissaoDto;
import com.humanconsulting.humancore_api.velho.controller.dto.response.tarefa.TarefaResponseDto;
import com.humanconsulting.humancore_api.velho.controller.dto.response.sprint.SprintResponseDto;
import com.humanconsulting.humancore_api.velho.exception.EntidadeConflitanteException;
import com.humanconsulting.humancore_api.velho.exception.EntidadeNaoEncontradaException;
import com.humanconsulting.humancore_api.velho.exception.EntidadeSemRetornoException;
import com.humanconsulting.humancore_api.velho.mapper.SprintMapper;
import com.humanconsulting.humancore_api.model.*;
import com.humanconsulting.humancore_api.repository.*;
import com.humanconsulting.humancore_api.velho.model.Checkpoint;
import com.humanconsulting.humancore_api.velho.model.Sprint;
import com.humanconsulting.humancore_api.velho.model.Tarefa;
import com.humanconsulting.humancore_api.velho.model.Usuario;
import com.humanconsulting.humancore_api.velho.repository.*;
import com.humanconsulting.humancore_api.velho.security.PermissaoValidator;
import com.humanconsulting.humancore_api.velho.utils.ProgressoCalculator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SprintService {

    @Autowired private ProjetoRepository projetoRepository;

    @Autowired private SprintRepository sprintRepository;

    @Autowired private CheckpointRepository checkpointRepository;

    @Autowired private TarefaRepository tarefaRepository;

    @Autowired private UsuarioRepository usuarioRepository;

    @Autowired private TarefaService tarefaService;

    public SprintResponseDto cadastrar(SprintRequestDto sprintRequestDto) {
        PermissaoValidator.validarPermissao(sprintRequestDto.getPermissaoEditor(), "ADICIONAR_SPRINT");

        if (sprintRequestDto.getDtInicio().isAfter(sprintRequestDto.getDtFim()) || sprintRequestDto.getDtInicio().isEqual(sprintRequestDto.getDtFim())) throw new EntidadeConflitanteException("Datas de início e fim conflitantes.");
        Sprint sprint = sprintRepository.save(SprintMapper.toEntity(sprintRequestDto, projetoRepository.findById(sprintRequestDto.getFkProjeto()).get()));
        return passarParaResponse(sprint, sprint.getIdSprint());
    }

    public Sprint buscarPorId(Integer id) {
        Optional<Sprint> optSprint = sprintRepository.findById(id);
        if (optSprint.isEmpty()) throw new EntidadeNaoEncontradaException("SprintEntity não encontrada.");
        return optSprint.get();
    }

    public List<Sprint> listar() {
        List<Sprint> all = sprintRepository.findAll();
        if (all.isEmpty()) throw new EntidadeSemRetornoException("Nenhuma sprint registrada");
        return all;
    }

    public void deletar(Integer id, UsuarioPermissaoDto usuarioPermissaoDto) {
        PermissaoValidator.validarPermissao(usuarioPermissaoDto.getPermissaoEditor(), "EXCLUIR_SPRINT");

        Optional<Sprint> optSprint = sprintRepository.findById(id);
        if (optSprint.isEmpty()) throw new EntidadeNaoEncontradaException("SprintEntity não encontrada.");
        sprintRepository.deleteById(id);
    }

    public SprintResponseDto atualizar(Integer idSprint, SprintAtualizarRequestDto request) {
        Sprint sprint = buscarPorId(idSprint);

        Optional<Usuario> optUsuarioEditor = usuarioRepository.findById(request.getIdEditor());

        if (optUsuarioEditor.isEmpty()) throw new EntidadeNaoEncontradaException("Usuário não encontrado.");

        PermissaoValidator.validarPermissao(request.getPermissaoEditor(), "MODIFICAR_SPRINT");

        Sprint sprintAtualizada = sprintRepository.save(SprintMapper.toEntity(request, idSprint, sprint.getProjeto()));

        return passarParaResponse(sprintAtualizada, sprintAtualizada.getIdSprint());
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
        boolean comImpedimento = tarefaRepository.existsImpedimentoBySprint(idSprint);
        List<Tarefa> tarefas = tarefaRepository.findByProjetoAndSprint(sprint.getProjeto().getIdProjeto(), sprint.getIdSprint());
        List<TarefaResponseDto> tarefasResponse = new ArrayList<>();
        for (Tarefa tarefa : tarefas) {
            tarefasResponse.add(tarefaService.passarParaResponse(tarefa));
        }
        List<Checkpoint> checkpoints = checkpointRepository.findAllByTarefa_Sprint_IdSprint(idSprint);

        Double progresso = ProgressoCalculator.calularProgresso(checkpoints);

        return SprintMapper.toDto(sprint, progresso, comImpedimento, tarefasResponse);
    }
}
